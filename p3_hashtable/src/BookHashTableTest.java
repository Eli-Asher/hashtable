/**
 * Filename: BookHashTable.java Project: p3 Authors: Elijah Asher (asher3@wisc.edu)
 * 
 * Semester: Fall 2018 Course: CS400
 * 
 * Due Date: before 10pm on 10/29 Version: 1.0
 * 
 * Credits: None so far
 * 
 * Bugs: none!
 */

import org.junit.After;
import java.io.FileNotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test HashTable class implementation to ensure that required functionality works for all cases.
 */
public class BookHashTableTest {

  // Default name of books data file
  public static final String BOOKS = "Newbooks.csv";

  // Empty hash tables that can be used by tests
  static BookHashTable bookObject;
  static ArrayList<Book> bookTable;

  static final int INIT_CAPACITY = 2;
  static final double LOAD_FACTOR_THRESHOLD = 0.49;

  static Random RNG = new Random(0); // seeded to make results repeatable (deterministic)

  /** Create a large array of keys and matching values for use in any test */
  @BeforeAll
  public static void beforeClass() throws Exception {
    bookTable = BookParser.parse(BOOKS);
  }

  /** Initialize empty hash table to be used in each test */
  @BeforeEach
  public void setUp() throws Exception {
    // TODO: change HashTable for final solution
    bookObject = new BookHashTable(INIT_CAPACITY, LOAD_FACTOR_THRESHOLD);
  }

  /** Not much to do, just make sure that variables are reset */
  @AfterEach
  public void tearDown() throws Exception {
    bookObject = null;
  }

  private void insertMany(ArrayList<Book> bookTable)
      throws IllegalNullKeyException, DuplicateKeyException {
    for (int i = 0; i < bookTable.size(); i++) {
      bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));
    }
  }

  /**
   * IMPLEMENTED AS EXAMPLE FOR YOU Tests that a HashTable is empty upon initialization
   */
  @Test
  public void test000_collision_scheme() {
    if (bookObject == null)
      fail("Gg");
    int scheme = bookObject.getCollisionResolutionScheme();
    if (scheme < 1 || scheme > 9)
      fail("collision resolution must be indicated with 1-9");
  }


  /**
   * IMPLEMENTED AS EXAMPLE FOR YOU Tests that a HashTable is empty upon initialization
   */
  @Test
  public void test000_IsEmpty() {
    // "size with 0 entries:"
    assertEquals(0, bookObject.numKeys());
  }

  /**
   * IMPLEMENTED AS EXAMPLE FOR YOU Tests that a HashTable is not empty after adding one (key,book)
   * pair
   * 
   * @throws DuplicateKeyException
   * @throws IllegalNullKeyException
   */
  @Test
  public void test001_IsNotEmpty() throws IllegalNullKeyException, DuplicateKeyException {
    // System.out.println(bookObject.numKeys());
    bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
    String expected = "" + 1;
    // "size with one entry:"
    assertEquals(expected, "" + bookObject.numKeys());
  }

  /**
   * IMPLEMENTED AS EXAMPLE FOR YOU Test if the hash table will be resized after adding two
   * (key,book) pairs given the load factor is 0.49 and initial capacity to be 2.
   */

  @Test
  public void test002_Resize() throws IllegalNullKeyException, DuplicateKeyException {
    bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
    int cap1 = bookObject.getCapacity();
    bookObject.insert(bookTable.get(1).getKey(), bookTable.get(1));
    int cap2 = bookObject.getCapacity();

    // "size with one entry:"
    assertTrue(cap2 > cap1 & cap1 == 2);
  }

  /**
   * Tests the remove method of BookHashTable
   * 
   * @throws IllegalNullKeyException
   * @throws DuplicateKeyException
   */
  @Test
  public void test003_Test_Remove() throws IllegalNullKeyException, DuplicateKeyException {
    ArrayList<Book> bookList = new ArrayList<Book>();
    for (int i = 0; i < 100; i++) {
      bookList.add(bookTable.get(i));
    }
    insertMany(bookList);

    int numKeys = bookObject.numKeys();
    bookObject.remove(bookTable.get(3).getKey());
    int numKeysAfter = bookObject.numKeys();
    // check that there are less keys after remove, and that there are only 9 elements in hashTable
    assertTrue(numKeys > numKeysAfter & numKeysAfter == 9);
  }

  /**
   * Tests that a duplicate key exception is thrown when 2 of the same keys are added
   */
  @Test
  public void test004_Test_DuplicateKey() {
    try {
      bookObject.insert(bookTable.get(76).getKey(), bookTable.get(76));
      bookObject.insert(bookTable.get(76).getKey(), bookTable.get(76));
    } catch (DuplicateKeyException e) {
      /* expected */
    } catch (Exception e) {
      fail("DuplicateKeyException was not thrown");
    }
  }

  /**
   * Pass if a nullKey exception is thrown when .get(key) is called when key == null
   */
  @Test
  public void test005_Test_Get_NullKey() {
    try {
      bookObject.insert(bookTable.get(6).getKey(), bookTable.get(6));
      bookObject.insert(bookTable.get(76).getKey(), bookTable.get(76));
      bookObject.insert(bookTable.get(10).getKey(), bookTable.get(10));
      bookObject.get(null);
    } catch (IllegalNullKeyException e) {
      /* expected */
    } catch (Exception e) {
      fail("IllegalNullKeyException was not thrown");
    }
  }

  /**
   * @throws DuplicateKeyException
   * @throws IllegalNullKeyException
   * 
   */
  @Test
  public void test006_Test_RemoveAll() throws IllegalNullKeyException, DuplicateKeyException {
    for (int i = 0; i < bookTable.size(); i++) {
      bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));
    }
    int numKeys = bookObject.numKeys();
    for (int i = 0; i < bookTable.size(); i++) {
      bookObject.remove(bookTable.get(i).getKey());
    }
    assertTrue(numKeys - bookObject.numKeys() == numKeys);
  }

  /**
   * Adds many elements to bookHashTable and the attempts to get each of the elements using their
   * corresponding keys. Pass if no exceptions are thrown
   */
  @Test
  public void test007_Test_addMany_GetMany() {
    try {
      for (int i = 0; i < bookTable.size(); i++) {
        bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));
      }
      for (int i = 0; i < bookTable.size(); i++) {
        bookObject.get(bookTable.get(i).getKey());
      }
    } catch (Exception e) {
      fail("Unexpected exception thrown");
    }
  }

  /**
   * Adds many elements to BookHashTable, then checks that the capacity of the DS was properly
   * changed.
   */
  @Test
  public void test008_Test_capacityChangeAfterResize() {
    try {
      for (int i = 0; i < 6; i++) {
        bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));
      }
      int capacity = bookObject.getCapacity();
      for (int i = 6; i < bookTable.size(); i++) {
        bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));
      }
      assertTrue(capacity != bookObject.getCapacity());
    } catch (Exception e) {
      fail("Unexpected exception thrown");
    }
  }

  /**
   * Attempts to get a key that was not added to the DS Pass if keynotfoundexcption is thrown
   * 
   * @throws DuplicateKeyException
   * @throws IllegalNullKeyException
   */
  @Test
  public void test009_Test_get_KeyNotFound() throws IllegalNullKeyException, DuplicateKeyException {
    for (int i = 0; i < 6; i++) {
      bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));
    }
    try {
      bookObject.get(bookTable.get(6).getKey());
    } catch (KeyNotFoundException e) {

    } catch (Exception e) {
      fail("unexpected error");
    }
  }

  /**
   * Pass if a null key exception is thrown when trying to insert a null key
   * 
   * @throws DuplicateKeyException
   */
  @Test
  public void test010_Test_InsertNullKey() throws DuplicateKeyException {
    try {
      bookObject.insert(null, bookTable.get(0));
    } catch (IllegalNullKeyException e) {
      /* expected */
    } catch (Exception e) {
      fail("Unexpected error thrown");
    }
  }
}
