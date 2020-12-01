/*
 * Class book
 */
public class Book {
  private String isbn13;
  private String authors;
  private String original_publication_year;
  private String title;
  private String language_code;
  private String average_rating;
  private String cover_type;
  private String pages;

  /**
   * Constructor
   * 
   * @param isbn13
   * @param authors
   * @param original_publication_year
   * @param title
   * @param language_code
   * @param average_rating
   * @param cover_type
   * @param pages
   */
  public Book(String isbn13, String authors, String original_publication_year, String title,
      String language_code, String average_rating, String cover_type, String pages) {
    this.isbn13 = isbn13;
    this.title = title;
    this.authors = authors;
    this.original_publication_year = original_publication_year;
    this.language_code = language_code;
    this.average_rating = average_rating;
    this.cover_type = cover_type;
    this.pages = pages;

  }

  /*
   * Default constructor
   */
  public Book() {
    this.isbn13 = null;
    this.title = null;
    this.authors = null;
    this.original_publication_year = null;
    this.language_code = null;
    this.average_rating = null;
    this.cover_type = null;
    this.pages = null;

  }

  /*
   * Getter for the isbn key for the book
   */
  public String getKey() {
    return this.isbn13;
  }



  @Override
  public String toString() {
    return "ISBN13: " + this.isbn13 + "; Book: " + this.title + ", Author: " + this.authors
        + ", Original Publication Year: " + this.original_publication_year + ", Language: "
        + this.language_code + ", Average Rating: " + this.average_rating + ", Cover Type: "
        + this.cover_type + ", Pages: " + this.pages;
  }


}


