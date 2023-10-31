import java.io.IOException;

import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Client {

    private static String messageSplit= "======================================================\n======================================================";
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

            Inspector inspector = new Inspector();
            System.out.println(messageSplit);
            inspector.inspect(deserializedObject, true);
            System.out.println(messageSplit);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
