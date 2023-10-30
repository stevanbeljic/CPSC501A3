import static org.junit.Assert.fail;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

import org.jdom2.Document;
import org.junit.*;

public class TestSandD {
    
    @Test
    public void testSandDSimpleObject(){
        SimpleObject first = new SimpleObject();
        first.aBoolean = true;
        first.aChar = 'a';
        first.aDouble = 2.2;
        first.anInt = 454;

        Document d = new Serializer().serialize(first);
        SimpleObject second = null;
        try{
            second = (SimpleObject)new Deserializer().deserialize(d);
        } catch (Exception e){
            fail();
        }

        assertEquals(first.aBoolean, second.aBoolean);
        assertEquals(first.aChar, second.aChar);
        assertEquals(first.aDouble, second.aDouble, 0.1);
        assertEquals(first.anInt, second.anInt);
    }

    @Test
    public void testSandDRefObject(){
        SimpleObject first = new SimpleObject();
        first.aBoolean = true;
        first.aChar = 'a';
        first.aDouble = 2.2;
        first.anInt = 454;

        ReferenceObject obj1 = new ReferenceObject();
        obj1.simpleObj = first;

        Document d = new Serializer().serialize(obj1);
        ReferenceObject obj2 = null;
        try{
            obj2 = (ReferenceObject)new Deserializer().deserialize(d);
        } catch (Exception e){
            fail();
        }

        assertEquals(obj1.simpleObj.aBoolean, obj2.simpleObj.aBoolean);
        assertEquals(obj1.simpleObj.aChar, obj2.simpleObj.aChar);
        assertEquals(obj1.simpleObj.aDouble, obj2.simpleObj.aDouble, 0.1);
        assertEquals(obj1.simpleObj.anInt, obj2.simpleObj.anInt);
        assertEquals(obj1.getClass().getName(), obj2.getClass().getName());
    }

    @Test
    public void testSandDSimpleArray(){
        SimpleArray firstArray = new SimpleArray();
        firstArray.intArray = new int[]{1,2,454};

        Document d = new Serializer().serialize(firstArray);
        SimpleArray secondArray = null;
        try{
            secondArray = (SimpleArray)new Deserializer().deserialize(d);
        } catch(Exception e){
            fail();
        }

        assertEquals(firstArray.intArray.length, secondArray.intArray.length);
        assertEquals(firstArray.intArray[0], secondArray.intArray[0]);
        assertEquals(firstArray.intArray[1], secondArray.intArray[1]);
        assertEquals(firstArray.intArray[2], secondArray.intArray[2]);
        assertEquals(firstArray.getClass().getName(), secondArray.getClass().getName());
    }

    @Test
    public void testSandDRefArray(){

        SimpleObject first = new SimpleObject();
        first.aBoolean = true;
        first.aChar = 'a';
        first.aDouble = 2.2;
        first.anInt = 454;

        SimpleObject second = new SimpleObject();
        second.aBoolean = false;
        second.aChar = 'h';
        second.aDouble = 1.12;
        second.anInt = 2222;

        ReferenceArray refArray1 = new ReferenceArray();
        refArray1.simpleObjectArray = new SimpleObject[]{first, second};

        Document d = new Serializer().serialize(refArray1);
        ReferenceArray refArray2 = null;
        try{
            refArray2 = (ReferenceArray) new Deserializer().deserialize(d);
        } catch (Exception e){
            fail();
        }

        assertEquals(refArray1.simpleObjectArray.length, refArray2.simpleObjectArray.length);
        assertEquals(refArray1.simpleObjectArray[0].aBoolean, refArray2.simpleObjectArray[0].aBoolean);
        assertEquals(refArray1.simpleObjectArray[0].aChar, refArray2.simpleObjectArray[0].aChar);
        assertEquals(refArray1.simpleObjectArray[0].aDouble, refArray2.simpleObjectArray[0].aDouble, 0.1);
        assertEquals(refArray1.simpleObjectArray[0].anInt, refArray2.simpleObjectArray[0].anInt);

        assertEquals(refArray1.simpleObjectArray[1].aBoolean, refArray2.simpleObjectArray[1].aBoolean);
        assertEquals(refArray1.simpleObjectArray[1].aChar, refArray2.simpleObjectArray[1].aChar);
        assertEquals(refArray1.simpleObjectArray[1].aDouble, refArray2.simpleObjectArray[1].aDouble, 0.1);
        assertEquals(refArray1.simpleObjectArray[1].anInt, refArray2.simpleObjectArray[1].anInt);
    }

    @Test
    public void testSandDArrayList(){

        SimpleObject first = new SimpleObject();
        first.aBoolean = true;
        first.aChar = 'a';
        first.aDouble = 2.2;
        first.anInt = 454;

        SimpleObject second = new SimpleObject();
        second.aBoolean = false;
        second.aChar = 'h';
        second.aDouble = 1.12;
        second.anInt = 2222;

        CollectionObject cObj1 = new CollectionObject();
        cObj1.simpleObjectCollection = new ArrayList<>();
        cObj1.simpleObjectCollection.add(first);
        cObj1.simpleObjectCollection.add(second);

        Document d = new Serializer().serialize(cObj1);
        CollectionObject rCollectionObject = null;

        try{
            rCollectionObject = (CollectionObject) new Deserializer().deserialize(d);
        } catch (Exception e){
            fail();
        }

        assertEquals(cObj1.simpleObjectCollection.size(), rCollectionObject.simpleObjectCollection.size());
        assertEquals(cObj1.simpleObjectCollection.get(0).aBoolean, rCollectionObject.simpleObjectCollection.get(0).aBoolean);
        assertEquals(cObj1.simpleObjectCollection.get(0).aChar, rCollectionObject.simpleObjectCollection.get(0).aChar);
        assertEquals(cObj1.simpleObjectCollection.get(1).aDouble, rCollectionObject.simpleObjectCollection.get(1).aDouble, 0.1);
        assertEquals(cObj1.simpleObjectCollection.get(1).anInt, rCollectionObject.simpleObjectCollection.get(1).anInt);

    }

    @Test
    public void testSandDCircular(){
        CircularObject obj1 = new CircularObject();
        obj1.objectID = 1;

        CircularObject obj2 = new CircularObject();
        obj1.objectID = 222;

        obj1.referencedObject = obj2;
        obj2.referencedObject = obj1;

        Document d = new Serializer().serialize(obj1);
        CircularObject returnedCircularObject = null;

        try{
            returnedCircularObject = (CircularObject) new Deserializer().deserialize(d);
        } catch (Exception e){
            fail();
        }

        assertEquals(obj1.objectID, returnedCircularObject.referencedObject.referencedObject.objectID);
        assertEquals(obj1.objectID, returnedCircularObject.objectID);
        assertEquals(obj2.objectID, returnedCircularObject.referencedObject.objectID);
    }

}
