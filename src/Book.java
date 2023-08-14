public abstract class Book implements Comparable {
    protected String author;
    protected String isbn;
    protected String title;
    protected String pages;
    protected int quantity;

    public Book(String isbn, String title, String author, String pages, int quantity) {
        this.author = author;
        this.isbn = isbn;
        this.title = title;
        this.pages = pages;
        this.quantity = quantity;
    }

    public abstract String toString();

    public String getTitle() {
        return title;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }


    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getQuantity() {return quantity;}

}
