public class FictionBook extends Book {

    public FictionBook(String isbn, String title, String author, String pages, int quantity) {
        super(isbn, title, author, pages, quantity);
    }

    @Override
    public String toString() {
        return "FictionBook{" +
                "isbn='" + getIsbn() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", pages=" + getPages() +
                ", quantity=" + getQuantity() +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}

