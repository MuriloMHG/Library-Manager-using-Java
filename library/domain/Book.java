package library.domain;

public class Book
{
    private long id;
    private String title;
    private String author;
    private String isbn;
    private int totalAvailable;
    
    public Book(){}

    public Book(long id, String title, String author, String isbn, int totalAvailable)
    {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.totalAvailable = totalAvailable;
    }

    public void doLoan()
    {
        if (totalAvailable <= 0)
        {
            throw new IllegalStateException("Book unavailable for loan.");
        }
        totalAvailable--;
    }

    public void returnBook()
    {
        totalAvailable++;
    }

    public long getId(){return id;}
    public String getTitle(){return title;}
    public String getAuthor(){return author;}
    public String getIsbn(){return isbn;}
    public int getTotalAvailable(){return totalAvailable;}

    public void setTotalAvailable(int newTotal){ this.totalAvailable = newTotal;}

    @Override
    public String toString(){
        return "Book{" +
                "id=" + id +
                ", title=" + title +
                ", author=" + author +
                ", isbn=" + isbn +
                ", totalAvailable=" + totalAvailable +
                "}";

    }
}
