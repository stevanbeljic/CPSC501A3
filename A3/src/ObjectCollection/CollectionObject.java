package ObjectCollection;

import java.util.Collection;
import java.util.HashSet;

public class CollectionObject {
    public Collection<SimpleObject> simpleObjectCollection;

    public CollectionObject(){
        simpleObjectCollection = new HashSet<SimpleObject>();
    }
}
