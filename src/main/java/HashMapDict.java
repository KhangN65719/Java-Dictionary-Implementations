import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashMapDict<K,V> implements ProjOneDictionary<K,V> {
    private class Node{
        K key;
        V value;
        Node(K newkey, V newvalue){
            key = newkey;
            value = newvalue;
        }
    }

    private Node[] buckets;
    private int size;
    private static final int INITIAL_SIZE = 20;
    private final Node placeholder = new Node(null, null);

    private Node[] getArray(int size){
        Node[] array = (Node[]) Array.newInstance(Node.class, size);
        return array;
    }

    public HashMapDict(){
        buckets = getArray(INITIAL_SIZE);
        size = 0;
    }

    private class hashMapIterator implements Iterator<K>{
        private int i = 0;

        hashMapIterator(){
            moveForward();
        }

        private void moveForward(){
            while (i < buckets.length && (buckets[i] == null || buckets[i] == placeholder)){
                i++;
            }
        }

        @Override
        public boolean hasNext(){
            return i < buckets.length;
        }

        @Override
        public K next(){
            if(!hasNext()) throw new NoSuchElementException();
            K key = buckets[i].key;
            i++;
            moveForward();
            return key;
        }
    }

    private void resize() {
        Node[] oldArray = buckets;
        buckets = getArray(oldArray.length * 2);
        size = 0;
        for (Node node : oldArray) {
            if (node != null && node != placeholder) {
                int pos = Math.abs(node.key.hashCode()) % buckets.length;

                while (buckets[pos] != null && !buckets[pos].key.equals(node.key)) {
                    pos = (pos + 1) % buckets.length;
                }
                buckets[pos] = node;
                size++;
            }
        }
    }

    @Override
    public boolean insert(K key, V value) throws NullKeyException, NullValueException {
        if(key == null) throw new NullKeyException();
        if(value == null) throw new NullValueException();

        if((double) size / buckets.length >= 0.5){
            resize();
        }

        int pos = Math.abs(key.hashCode()) % buckets.length;
        int placeholderPos = -1;

        while (buckets[pos] != null) {
            if (buckets[pos] == placeholder) {
                if (placeholderPos == -1){
                    placeholderPos = pos;
                }
            }
            else if (buckets[pos].key.equals(key)) {
                buckets[pos].value = value;
                return true;
            }
            pos = (pos + 1) % buckets.length;
        }

        int insertPos = (placeholderPos != -1) ? placeholderPos : pos;
        buckets[insertPos] = new Node(key, value);
        size++;
        return false;
    }

    @Override
    public V find(K key) throws NullKeyException {
        if(key == null) throw new NullKeyException();

        int pos = Math.abs(key.hashCode()) % buckets.length;

        while(buckets[pos] != null){
            if(buckets[pos] != placeholder && buckets[pos].key.equals(key)){
                return buckets[pos].value;
            }
            pos = (pos + 1) % buckets.length;
        }
        return null;
    }

    @Override
    public boolean delete(K key) throws NullKeyException {
        if(key == null) throw new NullKeyException();

        int pos = Math.abs(key.hashCode()) % buckets.length;

        while(buckets[pos] != null){

            if(buckets[pos] != placeholder && buckets[pos].key.equals(key)){
                buckets[pos] = placeholder;
                size--;
                return true;
            }
            pos = (pos + 1) % buckets.length;
        }

        return false;
    }

    @Override
    public int getSize() {
       return size;
    }

    @Override
    public Iterator<K> iterator() {
        return new hashMapIterator();
    }
}
