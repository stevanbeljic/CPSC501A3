import java.io.ObjectInputStream;
import java.net.Socket;

import org.jdom2.Document;

public class Reciever {

    public Document recieve(String ipAddress, int port) throws Exception{
        try{
            Socket socket = new Socket(ipAddress, port);

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            Document rDocument = (Document) inputStream.readObject();

            socket.close();
            return rDocument;
            
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }

    }
}
