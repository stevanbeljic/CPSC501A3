import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.jdom2.Document;

public class Sender {
    
    public void send(Document toSendDocument, int port){
        try{
            ServerSocket serverSocket = new ServerSocket(port);

            while(true){
                System.out.println("Waiting for a client...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");

                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                outputStream.writeObject(toSendDocument);
                outputStream.close();
                clientSocket.close();
                System.out.println("Serialized object sent");
                break;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
         

    }

}
