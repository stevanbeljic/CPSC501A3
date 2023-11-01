/*
 * CPSC 501 - Assignment 3
 * Stevan Beljic - 3006 1812
 */

/*
 * Primary driver class.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Driver {
    public static void main(String[] args){

        if(args.length != 1){
            System.out.println("Usage: java -cp .;jdom-2.0.6.1.jar Driver <port number>");
            return;
        }

        int port = Integer.parseInt(args[0]);

        while(true){
            //create the initial object
            Object toSerializeObject = initialObjectCreation();

            if(toSerializeObject == null){
                break;
            }

            //serialize the object
            Serializer serializer = new Serializer();
            Document toSendDocument = serializer.serialize(toSerializeObject);

            System.out.println("Object serialized");

            //send the document
            new Sender().send(toSendDocument, port);
        }
        new Sender().sendTerminate(port);
        System.out.println("Driver terminated");
    }

    /*
     * The driver call of printing the object creation menu, and getting the user's choice
     */
    private static Object initialObjectCreation(){
        //displays object creation menu
        ObjectCreator objCreator = new ObjectCreator();
        objCreator.objectCreationMenu();

        Scanner kb = new Scanner(System.in);
        int objectSelection;

        
        //runs until proper input has been provided
        while(true){
            System.out.print("\nYour selection (-1 to quit): ");
            objectSelection = kb.nextInt();

            switch(objectSelection){
                case 1: 
                    return objCreator.simpleObject();
                case 2:
                    return objCreator.objectReference();
                case 3:
                    return objCreator.simpleArray();
                case 4:
                    return objCreator.arrayReferences();
                case 5:
                    return objCreator.collectionObj();
                case 6:
                    return objCreator.circularObject();
                case -1:
                    return null;
                
                default:
                    System.out.println("Invalid selection made");
                }
            }
        
    }
}
