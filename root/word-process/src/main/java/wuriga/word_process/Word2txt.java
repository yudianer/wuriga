package wuriga.word_process;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

/**
 * Hello world!
 *
 */
public class Word2txt
{
    public static void main( String[] args ) throws OpenXML4JException, XmlException, IOException, URISyntaxException {
//        getMSWordFileAsString("/Users/jack/work-for-others/wuriga/老乞大原文-校/To 马工 7.30.docx");
//        String destFile =);
        System.out.println(Word2txt.class.getResource(""));
    }
    public static String getMSWordFileAsString(String filePath) throws IOException, OpenXML4JException, XmlException {
        if (filePath.endsWith(".doc")){
            return new WordExtractor(new FileInputStream(filePath)).getText();
        }
        return new XWPFWordExtractor(POIXMLDocument.openPackage(filePath)).getText();
    }
}
