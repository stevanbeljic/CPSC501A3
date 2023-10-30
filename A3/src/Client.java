import java.io.IOException;

import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Client {
    public static void main(String[] args){

        Reciever reciever = new Reciever();
        Document rDocument;
        try {
            rDocument = reciever.recieve();
        } catch (Exception e){
            e.printStackTrace();
            rDocument = null;
        }
        Deserializer deserializer = new Deserializer();
        Object deserializedObject;
        
        try{
            deserializedObject = deserializer.deserialize(rDocument);
            // XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            // outputter.output(rDocument, System.out);
            // System.out.println("\n\n\n");

            Inspector inspector = new Inspector();
            System.out.println("======================================================\n======================================================");
            inspector.inspect(deserializedObject, true);
            System.out.println("======================================================\n======================================================");
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
