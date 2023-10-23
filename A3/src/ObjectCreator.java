import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Scanner;

import ObjectCollection.CollectionObject;
import ObjectCollection.ReferenceArray;
import ObjectCollection.ReferenceObject;
import ObjectCollection.SimpleArray;
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
     * Selection 1 - a simple object with just primitive fields
     */
    public Object simpleObject(){
        Scanner kb = new Scanner(System.in);
        System.out.println("Creating simple object");
        Object returnObj = createSimpleObject(kb);
        kb.close();
        return returnObj;
    }

    /*
     * Selection 2 - an object with a reference to another object
     */
    public Object objectReference(){
        Scanner kb = new Scanner(System.in);
        System.out.println("Creating reference object");

        ReferenceObject returnObj = new ReferenceObject();
        returnObj.simpleObj = (SimpleObject)createSimpleObject(kb);

        kb.close();
        return returnObj;
    }

    /*
     * Selection 3 - an array containing primitives
     */
    public Object simpleArray(){
        Scanner kb = new Scanner(System.in);
        System.out.println("Creating simple array");

        SimpleArray simpleArrayObject = new SimpleArray();
        int arrayLength = setArrayLength(kb);
        simpleArrayObject.intArray = new int[arrayLength];

        for(int i = 0; i < arrayLength; i++){
            simpleArrayObject.intArray[i] = setSimpleInt(kb);
        }

        kb.close();
        return simpleArrayObject;
    }

    /*
     * Selection 4 - an array containing references to other objects
     */
    public Object arrayReferences(){
        System.out.println("Creating references array");
        Scanner kb = new Scanner(System.in);

        int arrayLength = setArrayLength(kb);
        ReferenceArray refArray = new ReferenceArray(arrayLength);

        for(int i = 0; i < arrayLength; i++){
            refArray.simpleObjectArray[i] = (SimpleObject)createSimpleObject(kb);
        }

        kb.close();
        return refArray;
    }

    /*
     * Selection 5
     */
    public Object collectionObj(){
        Scanner kb = new Scanner(System.in);

        System.out.println("Creating collection object");
        int collectionLength = setArrayLength(kb);

        CollectionObject collectionObj = new CollectionObject();
        for(int i = 0; i < collectionLength; i++){
            collectionObj.simpleObjectCollection.add((SimpleObject)createSimpleObject(kb));
        }
        return collectionObj;
    }

    /*
     * Sets the fields in a simple object
     */
    public Object createSimpleObject(Scanner kb){

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
        
        return simpleObj;
    }

    private int setArrayLength(Scanner kb){
        String input;
        while (true) {
            System.out.print("Enter a length (greater or equal to 0): ");
            input = kb.nextLine();
            try {
                int length = Integer.parseInt(input);
                if(length >= 0) return length;
            } catch (NumberFormatException e) {
                System.out.println("Invalid length provided.");
            }
        }
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