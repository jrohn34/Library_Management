import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Library implements LibraryManagementSystem {

    private List<FictionBook> fictionBooks;
    private List<NonfictionBook> nonfictionBooks;
    private List<Student> students;
    private HashMap<String, Integer> inventory;

    public Library() throws FileNotFoundException {
        this.fictionBooks = new ArrayList<>();
        this.nonfictionBooks = new ArrayList<>();
        this.students = new ArrayList<>();
        this.inventory = new HashMap<>();
    }

    public List<FictionBook> getFictionBooks() {
        return fictionBooks;
    }

    public List<NonfictionBook> getNonfictionBooks() {
        return nonfictionBooks;
    }

    public List<Student> getStudents() {
        return students;
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(HashMap<String, Integer> inventory) {
        this.inventory = inventory;
    }


    public int[] availableBooks() throws Exception {
        int[] available = new int[2];
        Library lib = new Library();
        lib.inventory("src/inventory v2.txt");
        for (FictionBook book : fictionBooks) {
            available[0] += 1;
        }
        for (NonfictionBook book : nonfictionBooks) {
            available[1] += 1;
        }
        return available;
    }

    @Override
    public void lend(String isbn) throws Exception {
        if (!inventory.containsKey(isbn) || inventory.get(isbn) == 0) {
            throw new Exception("Book is not available for lending");
        } else {
            inventory.put(isbn, inventory.get(isbn) - 1);
        }
    }

    @Override
    public void putBack(String isbn) throws Exception {
        if (!inventory.containsKey(isbn)) {
            throw new Exception("Book not found in inventory");
        } else {
            inventory.put(isbn, inventory.get(isbn) + 1);
        }
    }

    public boolean isBookAvailableForBorrowing(String isbn, String registrationNumber) {
        Integer book = inventory.get(isbn);
        if (book == null) {
            return false; // book not found in inventory
        }
        return book > 0;
    }

    @Override
    public void inventory(String filePath) throws Exception {
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (!parts[0].equals("isbn")) {
                    String isbn = parts[0];
                    String title = parts[1];
                    String author = parts[2];
                    String pages = parts[3];
                    int quantity = Integer.parseInt(parts[4]);
                    String type = parts[5];
                    if (type.startsWith("f")) {
                        FictionBook book = new FictionBook(isbn, title, author, pages, quantity);
                        fictionBooks.add(book);
                    } else if (type.startsWith("n")) {
                        NonfictionBook book = new NonfictionBook(isbn, title, author, pages, quantity);
                        nonfictionBooks.add(book);
                    }
                    inventory.put(isbn, quantity);
                }
            }
        } catch (FileNotFoundException e) {
            throw new Exception("Inventory file not found");
        }
    }

    @Override
    public void registerStudent(Student student) throws Exception {
        for (Student s : students) {
            if (s.getRegistrationNumber().equals(student.getRegistrationNumber())) {
                throw new Exception("Student already registered");
            }
        }
        students.add(student);
    }


    @Override
    public Book search(String isbn) {
        for (FictionBook book : fictionBooks) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        for (NonfictionBook book : nonfictionBooks) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null; // book not found
    }

    @Override
    public ArrayList<Book> sort(int sortType) {
        ArrayList<Book> allBooks = new ArrayList<Book>();
        allBooks.addAll(fictionBooks);
        allBooks.addAll(nonfictionBooks);

        if (sortType == 1) {
            Collections.sort(allBooks, new Comparator<Book>() {
                @Override
                public int compare(Book b1, Book b2) {
                    return b1.getIsbn().compareTo(b2.getIsbn());
                }
            });
        } else if (sortType == 2) {
            Collections.sort(allBooks, new Comparator<Book>() {
                @Override
                public int compare(Book b1, Book b2) {
                    return b2.getQuantity() - b1.getQuantity();
                }
            });
        }

        ArrayList<Book> top10 = new ArrayList<Book>();
        for (int i = 0; i < 10 && i < allBooks.size(); i++) {
            top10.add(allBooks.get(i));
        }

        return top10;
    }
}