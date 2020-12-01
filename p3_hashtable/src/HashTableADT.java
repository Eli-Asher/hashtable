/**
 * Filename:   HashTableADT.java
 * Project:    p3-201901
 * Course:     cs400
 * Authors:    Debra Deppeler
 * 
 * May use any of these Java's built-in Java collection types:
 * Arrays, List, ArrayList, LinkedList, Stack, Queue (interface), PriorityQueue, Deque 
 * 
 * May not use Java's HashTable, TreeMap, HashMap, etc.
 * May not add or edit any public members of this ADT
 * May not add or edit any public members to for your implementation.
 *
 * DO NOT EDIT OR SUBMIT THIS INTERFACE           
 */
public interface HashTableADT<K extends Comparable<K>, V> extends DataStructureADT<K,V> {

    // Notice:
    // THIS INTERFACE EXTENDS AND INHERITS ALL METHODS FROM DataStructureADT
    // and adds the following operations:

    // Returns the load factor for this hash table
    // that determines when to increase the capacity 
    // of this hash table
    public double getLoadFactorThreshold() ;

     // Capacity is the size of the hash table array
     // This method returns the current capacity.
     //
     // The initial capacity must be a positive integer, 1 or greater
     // and is specified in the constructor.
     // 
     // REQUIRED: When the load factor is reached, 
     // the capacity must increase to: 2 * capacity + 1
     //
     // Once increased, the capacity never decreases
     public int getCapacity() ;
    

     // Returns the collision resolution scheme used for this hash table.
     // Implement this ADT with one of the following collision resolution strategies
     // and implement this method to return an integer to indicate which strategy.
     //
      // 1 OPEN ADDRESSING: linear probe
      // 2 OPEN ADDRESSING: quadratic probe
      // 3 OPEN ADDRESSING: double hashing
      // 4 CHAINED BUCKET: array list of array lists
      // 5 CHAINED BUCKET: array list of linked lists
      // 6 CHAINED BUCKET: array list of binary search trees
      // 7 CHAINED BUCKET: linked list of array lists
      // 8 CHAINED BUCKET: linked list of linked lists
      // 9 CHAINED BUCKET: linked list of of binary search trees
     public int getCollisionResolutionScheme() ;

}
