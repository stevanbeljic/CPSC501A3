import org.jdom2.*;

public class Client {
    public static void main(String[] args){

        Reciever reciever = new Reciever();
        Document rDocument;
        try{
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

            System.out.println("======================================================\n======================================================");
            inspector.inspect(deserializedObject, true);
            System.out.println("======================================================\n======================================================");
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
