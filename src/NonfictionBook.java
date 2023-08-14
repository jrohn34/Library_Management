public class NonfictionBook extends Book {

    public NonfictionBook(String isbn, String title, String author, String pages, int quantity) {
        super(isbn, title, author, pages, quantity);
    }

    @Override
    public String toString() {
        return "NonFictionBook{" +
                "isbn='" + getIsbn() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", pages=" + getPages() +
                ", quantity=" + getQuantity() +
                '}';
    }

    public int compareTo(Object obj) {
        if (!(obj instanceof Book book)){
            throw new IllegalArgumentException("Object is not a book");
        }
        return this.pages.compareTo(book.getPages());
    }
}