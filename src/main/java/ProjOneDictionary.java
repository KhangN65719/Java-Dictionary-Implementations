public interface ProjOneDictionary<K ,V> extends Iterable<K>{
    /**
     * Inserts a key, value pair into the dictionary.
     * If the key is already in the dictionary it should overwrite the value currently in the dictionary.
     * @param key the key to be inserted
     * @param value the value to be inserted paired with the key
     * @throws NullKeyException if key is null
     * @throws NullValueException if value is null
     * @return Return true if the key is already in the list and false otherwise
     *
     */
    public boolean insert(K key, V value) throws NullKeyException,NullValueException;
    /**
     * Returns the value associated with a given key. Should return null if the key is not found
     * @param key the key to be searched for
     * @throws NullKeyException if the search key is Null
     * @return the associated value or null if the key is not found
     */
    public V find(K key) throws NullKeyException;

    /**
     * Removes the first instance of a given key K found in the dictionary and its corresponding value
     * @param key the key to be removed
     * @throws NullKeyException if the key to delete is Null
     * @return true if the deletion is successful, false if the key is not found.
     */
    public boolean delete(K key) throws NullKeyException;

    /*
     * @return the number of elements in the dictionary
     */
    public int getSize();
}


