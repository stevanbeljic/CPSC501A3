import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import java.io.File;
import java.io.IOException;

public class Serializer {
    public final static String fileName = "src/Course.xml";
    public Document serialize(Object obj){

    try{
        SAXBuilder sax = new SAXBuilder();
        Document doc = sax.build(new File(fileName));
    } catch (Exception e){

    }

        return null;
    }
}