import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LibraryTest {

    private Library library;
    private Student student1, student2;

    @BeforeEach
    public void setUp() throws Exception {
        library = new Library();
        library.inventory("src/inventory v2.txt");
        student1 = new Student("John Doe", "JD123");
        student2 = new Student("Jane Doe", "JD456");
        library.registerStudent(student1);
        library.registerStudent(student2);
    }

    @Test
    public void testAvailableBooks() throws Exception {
        int[] expected = {6, 4};
        int[] actual = library.availableBooks();
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testLend() throws Exception {
        library.lend("123456789");
        Assertions.assertEquals(5, library.getInventory().get("123456789"));
    }

    @Test
    public void testLendNotAvailable() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            library.lend("111111111");
        });
        String expectedMessage = "Book is not available for lending";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testPutBack() throws Exception {
        library.putBack("123456789");
        Assertions.assertEquals(6, library.getInventory().get("123456789"));
    }

    @Test
    public void testPutBackNotFound() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            library.putBack("999999999");
        });
        String expectedMessage = "Book not found in inventory";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testIsBookAvailableForBorrowing() {
        boolean expected = true;
        boolean actual = library.isBookAvailableForBorrowing("123456789", "JD123");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testIsBookAvailableForBorrowingNotAvailable() throws Exception {
        boolean expected = false;
        library.isBookAvailableForBorrowing("123456789", "JD123");
        boolean actual;
        library.lend("123456789");
        actual = library.isBookAvailableForBorrowing("123456789", "JD123");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testInventory() throws Exception {
        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("123456789", 6);
        expected.put("234567890", 4);
        expected.put("345678901", 2);
        expected.put("456789012", 1);
        expected.put("567890123", 3);
        expected.put("678901234", 2);
        library.inventory("src/inventory v2.txt");
        HashMap<String, Integer> actual = library.getInventory();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testInventoryNotFound() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            library.inventory("src/inventory_not_found.txt");
        });
        String expectedMessage = "Inventory file not found";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testRegisterStudent() throws Exception {
        Library lib = new Library();
        Student student = new Student("John Doe", "1234");

        // Test registering a new student
        lib.registerStudent(student);
        List<Student> students = lib.getStudents();
        assertEquals(1, students.size());
        assertEquals(student, students.get(0));

        // Test attempting to register a student with the same registration number as an existing student
        try {
            lib.registerStudent(new Student("Jane Smith", "1234"));
            fail("Expected Exception was not thrown");
        } catch (Exception e) {
            assertEquals("Student already registered", e.getMessage());
        }
    }
}