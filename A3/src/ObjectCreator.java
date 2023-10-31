import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

public class ObjectCreator {
    /*
     * Must be able to create the following objects:
     *      - a simple object with only primitives for instance variables, which must be setable
     *      - an object that contains references to other objects, which must also be created and their fields set. Must deal with circular ref
     *      - an object that contains an array of primitives. Array elements must be setable
     *      - an object that contains an array of object references. other objects must also be created
     *      - an object that uses an instance of one of Java’s collection classes to refer to several other objects
     */

    private Scanner kbReader;
    
    public ObjectCreator(){
        kbReader = new Scanner(System.in);
    }

    public void closeScanner(){
        kbReader.close();
    }
    
    public void objectCreationMenu(){
        System.out.println("Which object would you like to create (Enter the corresponding number):");
        System.out.println("\t1. Simple object wth primitive fields");
        System.out.println("\t2. An object with references to other objects");
        System.out.println("\t3. An object with an array of primitives");
        System.out.println("\t4. An object with an array of object references");
        System.out.println("\t5. An object that uses an instance of Java's collection classes to reference other objects");
        System.out.println("\t6. An object with a circular reference");
    }

    /*
     * Selection 1 - a simple object with just primitive fields
     */
    public Object simpleObject(){
        System.out.println("Creating simple object");
        Object returnObj = createSimpleObject();
        return returnObj;
    }

    /*
     * Selection 2 - an object with a reference to another object
     */
    public Object objectReference(){
        System.out.println("Creating reference object");

        ReferenceObject returnObj = new ReferenceObject();
        returnObj.simpleObj = (SimpleObject)createSimpleObject();

        return returnObj;
    }

    /*
     * Selection 3 - an array containing primitives
     */
    public Object simpleArray(){
        System.out.println("Creating simple array");

        int arrayLength = setArrayLength();

        SimpleArray simpleArrayObject = new SimpleArray();
        simpleArrayObject.intArray = new int[arrayLength];

        for(int i = 0; i < arrayLength; i++){
            simpleArrayObject.intArray[i] = setSimpleInt();
        }
        
        return simpleArrayObject;
    }

    /*
     * Selection 4 - an array containing references to other objects
     */
    public Object arrayReferences(){
        System.out.println("Creating references array");

        int arrayLength = setArrayLength();
        ReferenceArray refArray = new ReferenceArray();
        refArray.simpleObjectArray = new SimpleObject[arrayLength];

        for(int i = 0; i < arrayLength; i++){
            refArray.simpleObjectArray[i] = (SimpleObject)createSimpleObject();
        }

        return refArray;
    }

    /*
     * Selection 5 - an arraylist referencing simple objects
     */
    public Object collectionObj(){

        System.out.println("Creating collection object");
        int collectionLength = setArrayLength();

        CollectionObject collectionObj;
        ArrayList<SimpleObject> localArrayList= new ArrayList<SimpleObject>();

        for(int i = 0; i < collectionLength; i++){
            localArrayList.add((SimpleObject)createSimpleObject());
        }

        collectionObj = new CollectionObject();
        collectionObj.simpleObjectCollection = localArrayList;

        return collectionObj;
    }

    /*
     * Selection 6 - an object with a circular reference to another object
     */
    public Object circularObject(){
        Scanner kb = new Scanner(System.in);
        System.out.println("Creating circular reference object");

        CircularObject obj1 = new CircularObject();
        CircularObject obj2 = new CircularObject();

        System.out.print("Provide an ID for the first object: ");
        obj1.objectID = setSimpleInt();

        System.out.print("Provide an ID for the second object: ");
        obj2.objectID = setSimpleInt();

        obj1.referencedObject = obj2;
        obj2.referencedObject = obj1;

        return obj1;
    }

    /*
     * Sets the fields in a simple object
     */
    public Object createSimpleObject(){

        SimpleObject simpleObj = new SimpleObject();
        Class objClass = simpleObj.getClass();
        Field fields[] = objClass.getFields();

        for (Field f : fields){
            f.setAccessible(true);
            Type fieldType = f.getType();

            try{
                if(fieldType.equals(int.class)){
                    f.setInt(simpleObj, setSimpleInt());
                } else if(fieldType.equals(char.class)){
                    f.setChar(simpleObj, setSimpleChar());
                } else if(fieldType.equals(boolean.class)){
                    f.setBoolean(simpleObj, setSimpleBoolean());
                } else if(fieldType.equals(double.class)){
                    f.setDouble(simpleObj, setSimpleDouble());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return simpleObj;
    }

    private int setArrayLength(){
        String input;
        while (true) {
            System.out.print("Enter a length (greater than 0): ");
            input = kbReader.nextLine();
            try {
                int length = Integer.parseInt(input);
                if(length >= 1) return length;
            } catch (NumberFormatException e) {
                System.out.println("Invalid length provided.");
            }
        }
    }

    private boolean setSimpleBoolean(){
        String input;
        while (true){
            System.out.print("\nEnter \"true\" or \"false\": ");
            input = kbReader.nextLine();
            if(input.equals("true") || input.equals("false")){
                return Boolean.parseBoolean(input);
            }
        }
    }
    private int setSimpleInt() {
        String input;
        while (true) {
            System.out.print("Enter an integer: ");
            input = kbReader.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer provided.");
            }
        }
    }

    private double setSimpleDouble() {
        String input;

        while (true) {
            System.out.print("Enter a double: ");
            input = kbReader.nextLine();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid double provided.");
            }
        }
    }

    private char setSimpleChar() {
        while(true){
            System.out.print("Enter a character: ");
            String input = kbReader.nextLine();
            if (input.length() == 1) {                    
                return input.charAt(0);
            } else {
                System.out.println("Invalid char provided.");
            }
        }
    }
}