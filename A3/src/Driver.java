/*
 * CPSC 501 - Assignment 3
 * Stevan Beljic - 3006 1812
 */

/*
 * Primary driver class.
 */

import java.lang.reflect.Field;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args){

        //create the initial object
        Object toSerializeObject = initialObjectCreation();

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
                    
                default:
                    System.out.println("Invalid selection made");
            }
        }
    }
}
