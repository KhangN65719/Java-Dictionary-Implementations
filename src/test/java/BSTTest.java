

public class BSTTest extends DictionaryTest {
    @Override
    public ProjOneDictionary<String, String> newDictionary() {
        return new BinarySearchTreeDict<String,String>();
    }
}


