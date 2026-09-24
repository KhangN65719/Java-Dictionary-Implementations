import java.security.Key;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;

public class BinarySearchTreeDict<K extends Comparable<K>,V> implements ProjOneDictionary<K,V> {

    private class Node{
        K key;
        V value;
        Node left;
        Node right;

        Node(K newKey, V newValue){
            this.key = newKey;
            this.value = newValue;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;

    public BinarySearchTreeDict(){
        this.root = null;
        this.size = 0;
    }

    private class BSTIterator implements Iterator<K>{
        public Queue<K> result = new LinkedList<>();

        public BSTIterator(){
            bfs(root);
        }

        public void bfs(Node root){
            if(root == null) return;

            Queue<Node> q = new LinkedList<>();
            q.offer(root);
            while (!q.isEmpty()){
                Node curr = q.poll();
                result.offer(curr.key);
                if(curr.left != null) q.offer(curr.left);
                if(curr.right != null) q.offer(curr.right);
            }
        }

        @Override
        public boolean hasNext(){
            return !result.isEmpty();
        }

        @Override
        public K next() {
            return result.poll();
        }
    }

    private Boolean _insertHelper(Node curr, K key, V value){
        int cmp = key.compareTo(curr.key);

        if(cmp == 0){
            curr.value = value;
            return true;
        }
        else if(cmp < 0){
            if(curr.left == null){
                curr.left = new Node(key, value);
                size++;
                return false;
            }
            return _insertHelper(curr.left, key, value);
        }
        else{
            if(curr.right == null){
                curr.right = new Node(key, value);
                size++;
                return false;
            }
            return _insertHelper(curr.right, key, value);
        }
    }

    @Override
    public boolean insert(K key, V value) throws NullKeyException, NullValueException {
        if(key == null) throw new NullKeyException();
        if(value == null) throw new NullValueException();

        if(root == null){
            root = new Node(key, value);
            size++;
            return false;
        }
        return _insertHelper(root, key, value);
    }

    private V _findHelper(Node curr, K key) {
        if(curr == null) return null;

        int cmp = key.compareTo(curr.key);

        if(cmp == 0){
            return curr.value;
        }
        else if(cmp < 0){
            return _findHelper(curr.left, key);
        }
        else{
            return _findHelper(curr.right, key);
        }
    }

    @Override
    public V find(K key) throws NullKeyException {
        if(key == null) throw new NullKeyException();
        if(root == null) return null;

        return _findHelper(root, key);
    }

    private Node _minNode(Node curr){
        while(curr.left != null){
            curr = curr.left;
        }
        return curr;
    }

    private Node _deleteHelper(Node curr, K key){
        if(curr == null) return null;

        int cmp = key.compareTo(curr.key);

        if(cmp < 0){
            curr.left = _deleteHelper(curr.left, key);
        }
        else if(cmp > 0){
            curr.right = _deleteHelper(curr.right, key);
        }
        else{
            if (curr.left == null) return curr.right;
            if (curr.right == null) return curr.left;

            Node successor = _minNode(curr.right);
            curr.key = successor.key;
            curr.value = successor.value;
            curr.right = _deleteHelper(curr.right, successor.key);
        }
        return curr;
    }

    @Override
    public boolean delete(K key) throws NullKeyException {
        if(key == null) throw new NullKeyException();
        if(find(key) == null) return false;

        root = _deleteHelper(root, key);
        size--;
        return true;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTIterator();
    }
}
