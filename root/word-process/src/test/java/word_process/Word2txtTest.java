package word_process;

import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author malujia
 * @create 08-09-2019 下午2:28
 **/

public class Word2txtTest {
    @Test
    public void classTest(){
        String modelPath = "/home/maujia/softs/jars/edu/stanford/nlp/models/lexparser/xinhuaFactoredSegmenting.ser.gz";
        LexicalizedParser lp = LexicalizedParser.loadModel(modelPath);
        lp.parse("俄国希望伊朗没有制造核武器计划").pennPrint();
    }
}
