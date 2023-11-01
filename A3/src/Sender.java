import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.jdom2.Document;

public class Sender {
    
    ServerSocket serverSocket;

    public void send(Document toSendDocument, int port){
        try{
            serverSocket = new ServerSocket(port);

            while(true){
                System.out.println("Waiting for a client...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");

                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                outputStream.writeObject(toSendDocument);
                outputStream.close();
                clientSocket.close();
                System.out.println("Serialized object sent");
                serverSocket.close();
                break;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendTerminate(int port){
        try{
            if(serverSocket == null){
                serverSocket = new ServerSocket(port);
            }
            Socket clientSocket = serverSocket.accept();
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            outputStream.writeObject(new TerminateConnectionException("Terminate"));
            System.out.println("Terminated connection");
            clientSocket.close();
            serverSocket.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
