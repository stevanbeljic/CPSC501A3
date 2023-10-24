/*
 * CPSC 501 - Assignment 2 - Java Reflection
 * 
 * Stevan Beljic - 3006 1812
 */


import java.lang.reflect.*;
import java.util.*;

public class Inspector {
    
	private String recursionArrayMessage = "<---Recursion on array %s--->";
	private String recursionClassMessage = "\n\n<---Recursion on %s--->\n\n";
	private String recursionEndMessage =   "<------------------------>";
	private String recursionExistsMessage = "**Recursion already performed on this object**";

	public Set<String> examinedObjects = new HashSet<>();
	private boolean rec;
	
	public Inspector() { //constructor
		
	}
	
	public void inspect(Object obj, boolean recursive) {

		rec = recursive;
		
		Class objClass = obj.getClass();

		if(objClass.isArray()){ //obj is an array
			examineArray(obj);
		} else { //obj is a class
			examineClass(obj, rec);
		}
	}
	
	public void examineArray(Object obj) {
		Class objClass = obj.getClass();
		System.out.println(objClass.getSimpleName()+" Array");
		System.out.println("\tLength: "+Array.getLength(obj) + ", Component type: "+objClass.getComponentType().getName());

		for(int arrayIndex = 0; arrayIndex < Array.getLength(obj); arrayIndex++){
			Object arrayElement = Array.get(obj, arrayIndex);

			if(arrayElement == null){
				System.out.println("Array element "+arrayIndex+": null");
			} else if(arrayElement.getClass().isArray()){
				System.out.printf(recursionArrayMessage, arrayElement.getClass().getSimpleName());
				examineArray(arrayElement);
				System.out.println(recursionEndMessage);

			} else if (objClass.getComponentType().isPrimitive()){
				System.out.println("\tArray element "+arrayIndex+": "+arrayElement);
			} else {
				examineClass(arrayElement, rec);
			}
		}
		
	}
	
	public void examineClass(Object obj, boolean recurse) {
		Class objClass = obj.getClass();
		String declaringClassName = getDeclaringClassName(objClass);
		System.out.println("Class name: "+declaringClassName);
		
		Class superClass = getSuperClass(objClass);

		Class[] superInterfaces = objClass.getInterfaces();
		System.out.println("Interfaces");
		if(superInterfaces.length!=0){
			for (Class interfaceClass : superInterfaces){
				System.out.println("Implemented interface: "+interfaceClass.getName());
				if(!examinedObjects.contains(interfaceClass.getName())){
					examinedObjects.add(interfaceClass.getName());
					System.out.printf(recursionClassMessage, interfaceClass.getName());
					examineClass(interfaceClass, true);
					System.out.println(recursionEndMessage);
				} else{
					System.out.println(recursionExistsMessage);
				}
			}
		}

		examineMethods(obj, objClass);
		examineConstructors(obj, objClass);
		
		
		if(superClass != null && recurse){
			String superClassName = superClass.getName();
			System.out.println("Immediate superclass name: " + superClassName);

			if(!examinedObjects.contains(superClassName)){
				examinedObjects.add(superClassName);
				boolean superIsAbstract = false;

				System.out.printf(recursionClassMessage, superClassName);
				try{
					examineClass(superClass.getDeclaredConstructor().newInstance(), true); //traverse the hierarchy
				} catch(Exception e){
					superIsAbstract = true;
				}
		

				//abstract class, need to check through inheritence
				if (superIsAbstract) {
					System.out.println("Abstract super class, checking methods and fields through inheritance");
					examineMethods(obj, superClass);
					examineFields(obj, superClass);
				}			

				System.out.println(recursionEndMessage);
			} else {
				System.out.println(recursionExistsMessage);
			}
			
		} else{
			System.out.println("No superclass");
		}
		examineFields(obj, objClass);
		
	}

	public String getDeclaringClassName(Class c){
		String declaringClassName = c.getName();
		return declaringClassName;
	}

	public Class getSuperClass(Class c){
		Class superC = c.getSuperclass();
		return superC;
	}

	public void examineFields(Object obj, Class c) {
		System.out.println("\nFields");
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
						
						if (field.getType().isPrimitive()) {
							System.out.println("\n\tField: " + field.getName() + ", Type: " + field.getType().getName()+ ", Modifiers: " + Modifier.toString(field.getModifiers()));
							try {
								field.setAccessible(true);
								Object value = field.get(obj);
								System.out.println("\tValue: " + value);
							} catch (Exception e) {
								System.out.println("\tValue inaccessible");
							}
						} else if (field.getType().isArray()) {
							//examineArray(obj);
						} else if (rec == false){
								System.out.println("\n\tField: " + field.getName() + ", Type: " + field.getType().getName()+ ", Modifiers: " + Modifier.toString(field.getModifiers()));
								String hashValue = field.getType().getName()+"-"+field.getType().hashCode();
								System.out.println("\tField value: "+hashValue);
						} else {
							System.out.println("\n\tField: " + field.getName() + ", Type: " + field.getType().getName()+ ", Modifiers: " + Modifier.toString(field.getModifiers()));
							try{
								field.setAccessible(true);
								Object fieldValue = field.get(obj);
								System.out.printf(recursionClassMessage, fieldValue.getClass().getName());
								examineClass(field.get(fieldValue), rec);
								System.out.println(recursionEndMessage);
							} catch(Exception e){
								System.out.println("\tField value inaccesible");
							}
							
						}
					}
	}

	public void examineMethods(Object obj, Class c){

		Method methods[] = c.getDeclaredMethods();
		System.out.println("Declared Methods");
		for(Method m : methods){
			System.out.println("\t"+m.getName()+", Returns: "+m.getReturnType()+", Modifiers: "+Modifier.toString(m.getModifiers()));
			Class paraTypes[] = m.getParameterTypes();
			if(paraTypes.length > 0){
				System.out.print("\t\tParameter Types: ");
				for (Class p : paraTypes){
					System.out.print(p.getSimpleName()+", ");
				}
				System.out.println();
			}

			Class exceptionTypes[] = m.getExceptionTypes();

			if(exceptionTypes.length > 0){
				System.out.print("\t\tThrows Exceptions: ");
				for(Class e : exceptionTypes){
					System.out.print(e.getName()+", ");
				}
				System.out.println();
			}
		}

	}

	public void examineConstructors(Object obj, Class c){

		Constructor consts[] = c.getDeclaredConstructors();
		System.out.println("Declared Constructors");

		for(Constructor cs : consts){
			System.out.println("\t"+cs.getName());
			System.out.println("\tModifiers: "+Modifier.toString(cs.getModifiers()));
			Class paraTypes[] = cs.getParameterTypes();
			if(paraTypes.length > 0){
				System.out.print("\t\tParameter Types: ");
				for (Class p : paraTypes){
					System.out.print(p.getSimpleName()+", ");
				}
				System.out.println();
			}

			Class exceptionTypes[] = cs.getExceptionTypes();
			if(exceptionTypes.length > 0){
				System.out.print("\t\tThrows Exceptions: ");
				for(Class e : exceptionTypes){
					System.out.print(e.getName()+", ");
				}
				System.out.println();
			}
		}
	}
	
  }