package wuriga.word_process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.trees.Tree;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;
import org.apache.commons.io.FileUtils;
/**
 * Hello world!
 *
 */
public class Word2txt
{

    public static void main( String[] args ) throws OpenXML4JException, XmlException, IOException, URISyntaxException {
        String sourceDir = "/home/maujia/docs/wuriga/";
        String sourceFile = sourceDir + "laoqita-sentences.txt";
        String targetFile = sourceDir + "laoqita-tagged.txt";
        taggingSentences(sourceFile, new File(targetFile));
    }

    public static String getMSWordFileAsString(String filePath) throws IOException, OpenXML4JException, XmlException {
        if (filePath.endsWith(".doc")){
            return new WordExtractor(new FileInputStream(filePath)).getText();
        }
        return new XWPFWordExtractor(POIXMLDocument.openPackage(filePath)).getText();
    }

    public static List<String> filterTextFileToSentences(String filePath) throws IOException {
        List<String> orgLines = FileUtils.readLines(new File(filePath));
        StringBuilder pureText = new StringBuilder();
        String flawRegex = "[δ\\]\\[」②a-zA-Z【]";
        Pattern flawPattern = Pattern.compile("^.?[0-9]{1,2}.?[左右].?[:]?.?[O0-9]{1,2}");
        for (String line : orgLines){
            if (line.startsWith("------ image") || line.matches("^[0-9]+$") || StringUtils.isBlank(line)){
                continue;
            }
            line = line.replaceAll(flawRegex,"");
            Matcher matcher = flawPattern.matcher(line);
            if (matcher.find())
                line = matcher.replaceAll("");
            pureText.append(line);
        }
        List<String> pureSentences = new LinkedList<>();
        for (String sentence: getSentences(pureText.toString())){
            sentence = sentence.replaceAll("[~`、!@#$%^&*()\\-+={}\\[\\]:;\"'?/>.<,|]"," ");
            pureSentences.add(sentence);
        }
        return pureSentences;
    }

    public static String[] getSentences(String text){
        String sentenceEndingRegex = "[?。!;.]";
        return text.split(sentenceEndingRegex);
    }

    public static List<String> taggingSentences(String filePath, File targetFile) throws IOException {
        return taggingSentences(FileUtils.readLines(new File(filePath)), null);
    }

    public static List<String> taggingSentences(List<String> sentences, File targetFile) throws IOException {
        String modelPath = "/home/maujia/softs/jars/edu/stanford/nlp/models/lexparser/xinhuaFactoredSegmenting.ser.gz";
        LexicalizedParser lp = LexicalizedParser.loadModel(modelPath);
        List<String> taggedSentences = new LinkedList<>();
        for (String sentence: sentences) {
            List<TaggedWord> taggedWord = lp.parse(sentence).taggedYield();
            StringJoiner taggedSentenceCon = new StringJoiner(" ");
            for (TaggedWord word: taggedWord){
                taggedSentenceCon.add(word.word()+"/"+word.tag());
            }
            System.out.println(sentence);
            System.out.println(taggedSentenceCon.toString());
            System.out.println();
            taggedSentences.add(taggedSentenceCon.toString());
        }
        if (targetFile != null)
            FileUtils.writeLines(targetFile, taggedSentences);
        return taggedSentences;
    }
}
