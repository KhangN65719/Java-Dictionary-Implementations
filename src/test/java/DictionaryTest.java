import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

public abstract class DictionaryTest {
    public abstract ProjOneDictionary<String,String> newDictionary();

    @Test
    void testEmptyFind() throws NullKeyException{
        ProjOneDictionary<String,String> dict = newDictionary();
        assertNull(dict.find("A"),"Incorrect empty find behavior");
    }
    @Test
    void testSingleFind() throws NullKeyException{
        ProjOneDictionary<String,String> dict = newDictionary();
        try{
            dict.insert("3","three");
        } catch (NullValueException e){
            e.printStackTrace();
        }
        assertEquals("three",dict.find("3"),"Incorrect single element find behavior");
    }
    @Test
    void testIteratorSingle() throws NullKeyException {
        ProjOneDictionary<String, String> dict = newDictionary();
        try {
            dict.insert("3", "three");
        } catch (NullValueException e) {
            e.printStackTrace();
        }
        Iterator<String> iter = dict.iterator();
        assertTrue(iter.hasNext(), "Incorrect single element hasNext Iterator behavior");
        assertEquals("3", iter.next(), "Incorrect single element iterator behavior");
        assertFalse(iter.hasNext(), "Incorrect emptied hasNext Iterator behavior");
    }
    @Test
    void testFindWhenMany() throws NullKeyException{
        ProjOneDictionary<String, String> dict = newDictionary();
        try {
            for(int i = 1; i <= 1000; i++) {
                String key = String.valueOf(i);
                String value = String.valueOf(i * 10);
                dict.insert(key, value);
            }
        }
        catch (NullValueException e){
            e.printStackTrace();
        }

        for(int i = 1; i <= 1000; i++) {
            String key = String.valueOf(i);
            String value = String.valueOf(i * 10);
            assertEquals(value, dict.find(key), "Incorrect find behavior");
        }
    }
    @Test
    void testIteratorEmpty() throws NullKeyException{
        ProjOneDictionary<String, String> dict = newDictionary();

        Iterator<String> iter = dict.iterator();

        assertFalse(iter.hasNext(), "Iterator on an empty dictionary has no next");
    }
    @Test
    void testIteratorMany() throws NullKeyException{
        ProjOneDictionary<String, String> dict = newDictionary();
        try{
            dict.insert("a", "1");
            dict.insert("b", "2");
            dict.insert("c", "3");
            dict.insert("d", "4");
            dict.insert("e", "5");
        }
        catch (NullValueException e){
            e.printStackTrace();
        }

        Iterator<String> iter = dict.iterator();
        java.util.Set<String> keys = new java.util.HashSet<>();
        while(iter.hasNext()) keys.add(iter.next());

        assertTrue(keys.contains("a"));
        assertTrue(keys.contains("b"));
        assertTrue(keys.contains("c"));
        assertTrue(keys.contains("d"));
        assertTrue(keys.contains("e"));
        assertEquals(5, keys.size());
    }
    @Test
    void testInsertWhenEmpty() throws NullKeyException {
        ProjOneDictionary<String, String> dict = newDictionary();
        try {
            dict.insert("1", "one");
        }
        catch (NullValueException e){
            e.printStackTrace();
        }

        assertEquals("one", dict.find("1"), "Incorrect insertion behavior for an empty dictionary");
        assertEquals(1, dict.getSize());
    }
    @Test
    void testInsertWhenSingle() throws  NullKeyException{
        ProjOneDictionary<String, String> dict = newDictionary();
        try{
            dict.insert("1", "one");
            dict.insert("2", "two");
            dict.insert("1", "uno");
            assertTrue(dict.insert("2", "dos"));
        }
        catch (NullValueException e){
            e.printStackTrace();
        }

        assertEquals("dos", dict.find("2"), "Incorrect insertion behavior for a dictionary with one key");
        assertEquals("uno", dict.find("1"), "Incorrect insertion behavior for a dictionary with one key");
        assertEquals(2, dict.getSize());
    }
    @Test
    void testInsertWhenMany() throws NullKeyException{
        ProjOneDictionary<String, String> dict = newDictionary();

        try{
            for(int i = 1; i <= 1000; i++) {
                String key = String.valueOf(i);
                String value = String.valueOf(i * 10);
                dict.insert(key, value);
            }
            dict.insert("1001", "10010");
            dict.insert("2", "dos");
            dict.insert("3", "tres");
        }
        catch (NullValueException e){
            e.printStackTrace();
        }

        assertEquals("10010", dict.find("1001"));
        assertEquals("dos", dict.find("2"));
        assertEquals("tres", dict.find("3"));
        assertEquals(1001, dict.getSize());
    }
    @Test
    void testToDeleteEmpty() throws NullKeyException{
        ProjOneDictionary<String, String> dict = newDictionary();

        assertEquals(0, dict.getSize());
        assertFalse(dict.delete("1"), "Deleting a nonexistent key should return false");
        assertEquals(0, dict.getSize(), "Size should stay 0");
    }
    @Test
    void testToDeleteSingle() throws NullKeyException{
        ProjOneDictionary<String, String> dict = newDictionary();
        assertEquals(0, dict.getSize());

        try {
            dict.insert("1", "one");
        }
        catch (NullValueException e){
            e.printStackTrace();
        }

        assertEquals(1, dict.getSize());
        assertTrue(dict.delete("1"), "Delete should return true when key exists");
        assertEquals(0, dict.getSize());
        assertNull(dict.find("1"), "Key should no longer be found");
    }
    @Test
    void testToDeleteMany() throws NullKeyException{
        ProjOneDictionary<String, String> dict = newDictionary();

        try {
            for (int i = 1; i <= 1000; i++) {
                String key = String.valueOf(i);
                String value = String.valueOf(i * 10);
                dict.insert(key, value);
            }
        }
        catch(NullValueException e){
            e.printStackTrace();
        }

        assertEquals(1000, dict.getSize());
        for (int i = 501; i <= 1000; i++) {
            String key = String.valueOf(i);
            assertTrue(dict.delete(key), "Delete should return true");
        }
        assertEquals(500, dict.getSize());
        assertNull(dict.find("501"), "Deleted key should not be found");
        assertNull(dict.find("1000"), "Deleted key should not be found");
        assertEquals("10", dict.find("1"), "Key should still exist");
        assertEquals("4990", dict.find("499"), "Key should still exist");
    }
    @Test
    void testSizeEmpty(){
        ProjOneDictionary<String, String> dict = newDictionary();
        assertEquals(0, dict.getSize(), "Incorrect size");
    }
    @Test
    void testSizeSingle() throws NullKeyException{
        ProjOneDictionary<String, String> dict = newDictionary();
        try {
            dict.insert("1", "one");
        }
        catch (NullValueException e){
            e.printStackTrace();
        }
        assertEquals(1,  dict.getSize(), "Incorrect size");
    }
    @Test
    void testSizeMany() throws NullKeyException{
        ProjOneDictionary<String, String> dict = newDictionary();

        try {
            for (int i = 1; i <= 1000; i++) {
                String key = String.valueOf(i);
                String value = String.valueOf(i * 10);
                dict.insert(key, value);
            }
        }
        catch (NullValueException e){
            e.printStackTrace();
        }
        assertEquals(1000, dict.getSize(), "Incorrect size");
    }

}



