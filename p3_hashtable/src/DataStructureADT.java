/**
 * A data structure that can store at as many key,value pairs as needed.
 * 
 * CAUTION: The methods in this interface are similar, 
 * but are not be exactly the same as required in p1 and p2.
 *
 * DO NOT ADD OR EDIT members to this ADT
 * DO NOT ADD OR EDIT the signatures for these methods in your implementation.
 * 
 * @author deppeler
 *
 * @param <K> The key must not be null and must be Comparable.
 * @param <V> The data value associated with a given key.
 */
public interface DataStructureADT<K extends Comparable<K>, V> {

    // Add the key,value pair to the data structure and increase the number of keys.
    // If key is null, throw IllegalNullKeyException;
    // If key is already in data structure, throw DuplicateKeyException();
    void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException;

    // If key is found, 
    //    remove the key,value pair from the data structure
    //    decrease number of keys.
    //    return true
    // If key is null, throw IllegalNullKeyException
    // If key is not found, return false
    boolean remove(K key) throws IllegalNullKeyException;

    // Returns the value associated with the specified key
    // Does not remove key or decrease number of keys
    //
    // If key is null, throw IllegalNullKeyException 
    // If key is not found, throw KeyNotFoundException().
    V get(K key) throws IllegalNullKeyException, KeyNotFoundException;

    // Returns the number of key,value pairs in the data structure
    int numKeys();

}
