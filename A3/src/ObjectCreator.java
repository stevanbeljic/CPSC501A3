import java.util.Scanner;

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
        return null;
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
}