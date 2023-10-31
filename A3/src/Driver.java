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

        //create the initial object
        Object toSerializeObject = initialObjectCreation();

        //serialize the object
        Serializer serializer = new Serializer();
        Document toSendDocument = serializer.serialize(toSerializeObject);

        // XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        // try {
        //     outputter.output(toSendDocument, System.out);
        // } catch (IOException e) {
        //     System.out.println("Error serializing");
        //     e.printStackTrace();
        // }

        System.out.println("Object serialized");

        //send the document
        new Sender().send(toSendDocument);
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

        try{
            //runs until proper input has been provided
            while(true){
                System.out.print("\nYour selection: ");
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
                
                    default:
                        System.out.println("Invalid selection made");
                }
            }
        } finally{
            objCreator.closeScanner();
        }
    }
}
