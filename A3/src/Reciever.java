import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ConnectException;
import java.net.Socket;

import org.jdom2.Document;

public class Reciever {

    public Document recieve(String ipAddress, int port) throws Exception{
        try{
            boolean connected = false;
            Socket socket = null;
            while(!connected){
                try{
                    socket = new Socket(ipAddress, port);
                    connected = true;
                } catch (ConnectException cE){
                }
            }
            Document rDocument;
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            Object rObject = inputStream.readObject();
            if(rObject instanceof TerminateConnectionException){
                socket.close();
                throw new TerminateConnectionException("Terminated by Server");
            } else if(rObject instanceof Document){
                rDocument = (Document) rObject;
            } else {
                socket.close();
                throw new IOException("Recieved unexpected object type");
            }
            socket.close();
            return rDocument;
            
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }

    }
}
