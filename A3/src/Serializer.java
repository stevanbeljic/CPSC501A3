import org.jdom2.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
 * Serializer from Java Reflection in Action - Forman & Forman
 */
public class Serializer {
    public final static String fileName = "src/Course.xml";

    public Document serialize(Object obj) {

        Document seralizedDocument;

        try {
            seralizedDocument = serializeHelper(obj, new Document(new Element("serialized")), new IdentityHashMap<>());
            return seralizedDocument;
        } catch (Exception e){
            System.out.println("ERROR: in serialization");
            e.printStackTrace(System.out);
            return null;
        }
    }

    public static Document serializeHelper(Object source, Document target, IdentityHashMap table) throws Exception {
        String id = Integer.toString(table.size());
        table.put(source, id);
        Class sourceClass = source.getClass();

        Element oElt = new Element("object");
        oElt.setAttribute("class", sourceClass.getName());
        oElt.setAttribute("id", id);
        target.getRootElement().addContent(oElt);

        if(sourceClass.isArray()){
            System.out.println("Source class IS array");
            Class componentType = sourceClass.getComponentType();

            int length = Array.getLength(source);
            oElt.setAttribute("length", Integer.toString(length));
            for(int i = 0; i < length; i++){
                oElt.addContent(serializeVariable(componentType, Array.get(source, i), target, table));
            }
        } else {
            System.out.println("Source class NOT array");
            Field fields[] = sourceClass.getDeclaredFields();
            for(Field f : fields){
                if(!Modifier.isPublic(f.getModifiers())){
                    f.setAccessible(true);
                }
                Element fElt = new Element("field");
                fElt.setAttribute("name", f.getName());
                Class decClass = f.getDeclaringClass();
                fElt.setAttribute("declaringclass", decClass.getName());

                Class fieldtype = f.getType();
                Object child = f.get(source);

                if(Modifier.isTransient(f.getModifiers())){
                    child = null;
                }

                fElt.addContent(serializeVariable(fieldtype, child, target, table));
                oElt.addContent(fElt);
            }
        }
        return target;
    }

    private static Element serializeVariable (Class fieldtype, Object child, Document target, IdentityHashMap table) throws Exception{
        if(child == null){
            return new Element("null");
        } else if(!fieldtype.isPrimitive()) {
            Element reference = new Element("reference");
            if(table.containsKey(child)){
                reference.setText(table.get(child).toString());
            } else {
                reference.setText(Integer.toString(table.size()));
                serializeHelper(child, target, table);
            }
            
            return reference;
        } else {
            Element value = new Element("value");
            value.setText(child.toString());
            return value;
        }
    }
}
