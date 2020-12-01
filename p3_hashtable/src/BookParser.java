import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

// learn how Scanner instances that are connected to the keyboard work.
public class BookParser {

  // @param booksfilename - a csv file with book database information

  // Parse the csv file into a list of book object
  public static ArrayList<Book> parse(String booksfilename) throws FileNotFoundException {
    ArrayList<Book> bookList = new ArrayList<Book>();
    try {
      Scanner scanner = new Scanner(new File(booksfilename), "UTF-8");

      Scanner valueScanner = null;
      int idx = 0;

      // try different methods of the Scanner STDIN
      while (scanner.hasNext()) {
        valueScanner = new Scanner(scanner.nextLine());
        valueScanner.useDelimiter(",");
        ArrayList<String> book = new ArrayList<String>();
        while (valueScanner.hasNext()) {
          String data = valueScanner.next();
          book.add(data);
        }

        Book bookobj = new Book(book.get(0), book.get(1), book.get(2), book.get(3), book.get(4),
            book.get(5), book.get(6), book.get(7));
        // System.out.println(bookobj.toString());
        bookList.add(bookobj);


      }
      bookList.remove(0);
      scanner.close();

    } catch (FileNotFoundException e) {
    }
    return bookList;

  }

}

