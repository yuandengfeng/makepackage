package rest.util.xml;

/**
 * Created by yuandengfeng on 2016/11/3.
 */
public class Book {


    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String genre;
    private String ISBN;
    private String title;
    private String author;
    private String price;

    public Book(String genre,String ISBN,String title,String author,String price)
    {
        this.genre=genre;
        this.ISBN=ISBN;
        this.title=title;
        this.author=author;
        this.price=price;
    }




    public String getBook_id() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBook_name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String id;
    private String name;

    public Book(String id,String name)
    {
        this.id=id;
        this.name=name;
    }



}
