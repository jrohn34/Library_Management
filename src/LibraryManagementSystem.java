import java.util.*;

public interface LibraryManagementSystem {

    void lend(String isbn) throws Exception;

    void putBack(String isbn) throws Exception;

    void inventory(String filePath) throws Exception;

    void registerStudent(Student student) throws Exception;

    Book search(String isbn);

    List<Book> sort(int mode);

}
