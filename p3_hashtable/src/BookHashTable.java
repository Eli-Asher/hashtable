/**
 * Filename: TestHashTableDeb.java Project: p3 Authors: Elijah Asher (asher3@wisc.edu)
 * 
 * Semester: Fall 2018 Course: CS400
 * 
 * Due Date: before 10pm on 10/29 Version: 1.0
 * 
 * Credits: None so far
 * 
 * Bugs: none!
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


/**
 * HashTable implementation that uses Chain Bucket Collision resolution This class uses a LinkedList
 * array of type pair to store Books and their keys ------------------------------------------------
 * Pair: inner class that stores the book and it's assigned key The Buckets are linked lists that
 * stem from the array at hashed index ------------------------------------------------------------
 * The buckets are traversed when searching for a Key that has caused a collision
 * 
 * Hashing: Java's default hashcode method modded with table size == hashIndex
 * 
 * @param <K> unique comparable identifier for each <K,V> pair, may not be null
 * @param <V> associated value with a key, value may be null
 */
public class BookHashTable implements HashTableADT<String, Book> {
  /**
   * Inner Class that holds key and book
   * 
   * @author eliasher
   *
   */
  private class Pair {
    private String key;
    private Book book;

    /**
     * Constructor
     * 
     * @param k - key assigned to the book
     * @param v - book
     */
    private Pair(String k, Book v) {
      key = k;
      book = v;
    }

    /**
     * Getter for the Key stored in pair
     * 
     * @return key
     */
    private String getKey() {
      return key;
    }

    /**
     * Getter for the Book stored in pair
     * 
     * @return book
     */
    private Book getBook() {
      return book;
    }

  }

  /** The initial capacity that is used if none is specified user */
  static final int DEFAULT_CAPACITY = 101;

  /** The load factor that is used if none is specified by user */
  static final double DEFAULT_LOAD_FACTOR_THRESHOLD = 0.70;


  static Integer capacity;// capacity assigned by user
  static double loadFactorThreshold;
  private LinkedList<Pair>[] linkedList;
  private int numKeys;

  /**
   * REQUIRED default no-arg constructor Uses default capacity and sets load factor threshold for
   * the newly created hash table.
   */
  public BookHashTable() {
    this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR_THRESHOLD);

  }

  /**
   * Creates an empty hash table with the specified capacity and load factor.
   * 
   * @param initialCapacity     number of elements table should hold at start.
   * @param loadFactorThreshold the ratio of items/capacity that causes table to resize and rehash
   */
  @SuppressWarnings("unchecked")
  public BookHashTable(int initialCapacity, double loadFactor) {
    numKeys = 0;
    capacity = initialCapacity;
    loadFactorThreshold = loadFactor;
    linkedList = new LinkedList[capacity];
  }

  @Override
  public void insert(String key, Book value) throws IllegalNullKeyException, DuplicateKeyException {
    if (key == null)
      throw new IllegalNullKeyException();

    // check if key is already in hashTable
    for (int i = 0; i < linkedList.length; i++) {
      if (linkedList[i] == null) {
        // do nothing
      } else {//////// check for duplicate value
        for (int k = 0; k < linkedList[i].size(); k++) {
          if (linkedList[i].get(k).getKey().equals(key))
            throw new DuplicateKeyException();
        }
      }
    }

    int index = hashFunction(key);
    // create instance of LinkedList at this index if null
    if (linkedList[index] == null) {
      linkedList[index] = new LinkedList<Pair>();
    }
    // bucket does not contain value, add to bucket and increment num keys
    Pair toAdd = new Pair(key, value);
    linkedList[index].add(toAdd);
    numKeys++;


    if (loadFactorThreshold <= (numKeys / capacity)) {// checks if table needs to resize
      resize();
    }
  }

  /**
   * Resizes and Rehashes BookHashTable PRECONDITION: only called when loadFactorThreshold <=
   * numkeys/capacity
   * 
   * @throws IllegalNullKeyException
   * @throws DuplicateKeyException
   */
  @SuppressWarnings("unchecked")
  private void resize() throws IllegalNullKeyException, DuplicateKeyException {
    LinkedList<Pair> temp = new LinkedList<Pair>();
    for (int i = 0; i < linkedList.length; i++) {
      // for non-null elements, add to temp
      if (linkedList[i] == null) {
        // do nothing
      } else if (!linkedList[i].isEmpty()) {// if linkedList[i] is non-null and not empty, add
                                            // books
        // traverse through each bucket and add books to temp
        for (int k = 0; k < linkedList[i].size(); k++) {
          if (linkedList[i].get(k) != null)// for non null elements in bucket
            temp.add(linkedList[i].get(k));// add to temp
        }
      }
    } // now temp holds all instances of books
    capacity = capacity * 2 + 1;
    linkedList = new LinkedList[capacity];// resize instance variable
    // add to newly resized list of bookLists
    while (!temp.isEmpty()) {
      Pair tempBook = temp.peek();
      int index = hashFunction(tempBook.getKey());// gets hash index of book
      // add removed instance of book to linkedList
      if (linkedList[index] == null)
        linkedList[index] = new LinkedList<Pair>();// initialize so there's a non null instance at
                                                   // idx
      linkedList[index].add(tempBook);
      temp.remove();
    }
  }

  @Override
  public boolean remove(String key) throws IllegalNullKeyException {
    if (key == null)
      throw new IllegalNullKeyException();
    int index = hashFunction(key);
    if (linkedList[index] == null || linkedList[index].isEmpty())
      return false;
    for (int i = 0; i < linkedList[index].size(); i++) {
      // check if keys match
      if (linkedList[index].get(i).getKey().equals(key)) {
        linkedList[index].remove(i);
        numKeys--;
        return true;
      }
    }
    return false;
  }

  @Override
  public Book get(String key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null)
      throw new IllegalNullKeyException();
    // finds books in hashIndex of bookList
    LinkedList<Pair> bucket = linkedList[hashFunction(key)];
    if (bucket == null || bucket.isEmpty()) {// if bucket doesn't exist, throw exception
      throw new KeyNotFoundException();
    }
    // if key hashed to an index with a null element
    else {// look for book in chain bucket
      Book toReturn = null;
      ArrayList<String> keyList = new ArrayList<String>();
      for (int i = 0; i < bucket.size(); i++) {
        // if the keys match
        keyList.add(bucket.get(i).getKey());
        if (bucket.get(i).getKey().equals(key)) {
          toReturn = bucket.get(i).getBook();
        }
      } // key was not found in chain bucketing, throw exception
      if (!keyList.contains(key) || toReturn == null) {
        throw new KeyNotFoundException();
      }
      return toReturn;
    }
  }

  @Override
  public int numKeys() {
    return numKeys;
  }

  @Override
  public double getLoadFactorThreshold() {
    return loadFactorThreshold;
  }

  @Override
  public int getCapacity() {
    return capacity;
  }

  @Override
  public int getCollisionResolutionScheme() {
    // Chain Bucket resolution scheme
    return 5;
  }

  /**
   * Defines a unique for book given it's key
   * 
   * @param key
   * @return hashed index of key
   */
  private int hashFunction(String key) throws IllegalNullKeyException {
    return Math.abs(key.hashCode()) % capacity;
  }
}
