import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Scanner;

import ObjectCollection.SimpleObject;

public class ObjectCreator{
    /*
     * Must be able to create the following objects:
     *      - a simple object with only primitives for instance variables, which must be setable
     *      - an object that contains references to other objects, which must also be created and their fields set. Must deal with circular ref
     *      - an object that contains an array of primitives. Array elements must be setable
     *      - an object that contains an array of object references. other objects must also be created
     *      - an object that uses an instance of one of Java’s collection classes to refer to several other objects
     */

    public void objectCreationMenu(){
        System.out.println("Which object would you like to create (Enter the corresponding number):");
        System.out.println("\t1. Simple object wth primitive fields");
        System.out.println("\t2. An object with references to other objects");
        System.out.println("\t3. An object with an array of primitives");
        System.out.println("\t4. An object with an array of object references");
        System.out.println("\t5. An object that uses an instance of Java's collection classes to reference other objects");
    }

    /*
     * Selection 1
     */
    public Object simpleObject(){
        System.out.println("Creating simple object");
        return createSimpleObject();
    }

    /*
     * Selection 2
     */
    public Object objectReference(){
        System.out.println("Creating reference object");
        return null;
    }

    /*
     * Selection 3
     */
    public Object simpleArray(){
        System.out.println("Creating simple array");
        return null;
    }

    /*
     * Selection 4
     */
    public Object arrayReferences(){
        System.out.println("Creating references array");
        return null;
    }

    /*
     * Selection 5
     */
    public Object collectionObj(){
        System.out.println("Creating collection object");
        return null;
    }

    /*
     * Sets the fields in a simple object
     */
    private Object createSimpleObject(){
        Scanner kb = new Scanner(System.in);

        SimpleObject simpleObj = new SimpleObject();
        Class objClass = simpleObj.getClass();
        Field fields[] = objClass.getFields();

        for (Field f : fields){
            f.setAccessible(true);
            Type fieldType = f.getType();

            try{
                if(fieldType.equals(int.class)){
                    f.setInt(simpleObj, setSimpleInt(kb));
                } else if(fieldType.equals(char.class)){
                    f.setChar(simpleObj, setSimpleChar(kb));
                } else if(fieldType.equals(boolean.class)){
                    f.setBoolean(simpleObj, setSimpleBoolean(kb));
                } else if(fieldType.equals(double.class)){
                    f.setDouble(simpleObj, setSimpleDouble(kb));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        kb.close();
        return simpleObj;
    }

    private boolean setSimpleBoolean(Scanner kb){
        String input;
        while (true){
            System.out.print("\nEnter \"true\" or \"false\": ");
            input = kb.nextLine();
            if(input.equals("true") || input.equals("false")){
                return Boolean.parseBoolean(input);
            }
        }
    }
    private int setSimpleInt(Scanner kb) {
        String input;
        while (true) {
            System.out.print("Enter an integer: ");
            input = kb.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer provided.");
            }
        }
    }

    private double setSimpleDouble(Scanner kb) {
        String input;

        while (true) {
            System.out.print("Enter a double: ");
            input = kb.nextLine();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid double provided.");
            }
        }
    }

    private char setSimpleChar(Scanner kb) {
        while(true){
            System.out.print("Enter a character: ");
            String input = kb.nextLine();
            if (input.length() == 1) {                    
                return input.charAt(0);
            } else {
                System.out.println("Invalid char provided.");
            }
        }
    }
}