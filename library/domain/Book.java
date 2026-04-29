package library.domain;

public class Book
{
    private int id;
    private String title;
    private String author;
    private String isbn;
    private int totalAvailable;
    
    public Book(){}

    public Book(int id, String title, String author, String isbn, int totalAvailable)
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
            throw new IllegalStateException("Book unavalilabe to Loan !");
        }
        totalAvailable--;
    }

    public void returnBook()
    {
        totalAvailable++;
    }

    public int getId(){return id;}
    public String getTitle(){return title;}
    public String getAuthor(){return author;}
    public String getIsbn(){return isbn;}
    public int getTotalAvailable(){return totalAvailable;}

    public void setTotalAvailable(int newTotal){ this.totalAvailable = newTotal;}

    @Override
    public String toString(){
        return "Book{"+
                "id=" + id +
                "title=" + title +
                "author=" + author +
                "isbn=" + isbn +
                "totalAvailable=" + totalAvailable +
                "}";

    }
}
