
public class HashMapTest extends DictionaryTest{
    @Override
    public ProjOneDictionary<String, String> newDictionary() {
        return new HashMapDict<String,String>();
    }
}


