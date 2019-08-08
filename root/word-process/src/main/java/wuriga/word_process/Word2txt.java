package wuriga.word_process;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.apache.poi.hwpf.extractor.WordExtractor;
/**
 * Hello world!
 *
 */
public class Word2txt
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

    public static String readMSWordFile(String filePath) throws FileNotFoundException {
        if (filePath.endsWith(".doc")){
            return new WordExtractor(new FileInputStream(filePath));
        }
    }
}
