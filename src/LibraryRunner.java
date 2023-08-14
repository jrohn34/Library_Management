import java.io.*;
import java.util.*;


public class LibraryRunner{
    public static HashMap<String, List<String>> borrowedBooks;
    public static void main(String[] args) throws Exception {
        Map<String, List<String>> borrowedBooks = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        library.inventory("src/inventory v2.txt");
        while (true) {
            System.out.println("Welcome to the Library Management System!");
            System.out.println("Please select an option:");
            System.out.println("1. Register");
            System.out.println("2. Sort Books");
            System.out.println("3. Search Books");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Show Inventory Stats");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Please enter your name:");
                    scanner.nextLine();
                    String name = scanner.nextLine();
                    System.out.println("Please enter your registration number");
                    String number = scanner.nextLine();
                    Student student = new Student(name, number);
                    library.registerStudent(student);
                    System.out.println("Registration successful. Your registration number is: " + student.getRegistrationNumber());
                    break;
                case 2:
                    System.out.println("Now, pick to sort by either ISBN (type 1) or quantity (type2)");
                    scanner.nextLine();
                    int type = scanner.nextInt();
                    if (type == 1){
                        System.out.println("You have chosen ISBN sort");
                    }else if(type == 0){
                        System.out.println("You have chosen quantity sort");
                    }
                    library.sort(type);
                    break;

                case 3:
                    System.out.println("Please enter the ISBN of the book you are looking for:");
                    scanner.nextLine();
                    String isbn = scanner.nextLine();
                    Book book = library.search(isbn);
                    if (book != null) {
                        System.out.println("Book found:");
                        System.out.println(book);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;

                case 4:
                    System.out.println("Please enter your registration number:");
                    scanner.nextLine();
                    String registrationNumber = scanner.nextLine();
                    System.out.println("Please enter the ISBNs of the books you want to borrow, separated by commas:");
                    String borrowISBNsString = scanner.nextLine();
                    String[] borrowISBNs = borrowISBNsString.split(",");
                    boolean allBooksAvailable = true;
                    for (String borrowISBN : borrowISBNs) {
                        if (!library.isBookAvailableForBorrowing(borrowISBN, registrationNumber)) {
                            System.out.println("Book with ISBN " + borrowISBN + " is not available for borrowing.");
                            allBooksAvailable = false;
                        }
                    }
                    if (allBooksAvailable) {
                        List<String> borrowedISBNs = Arrays.asList(borrowISBNs);
                        borrowedBooks.put(registrationNumber, borrowedISBNs);
                        try (PrintWriter writer = new PrintWriter(new FileWriter("src/borrowed_books.txt",true))) {
                            writer.print(registrationNumber + ",");
                            for (String borrowedISBN : borrowedISBNs) {
                                writer.print(borrowedISBN + ",");
                            }
                            writer.println();
                        } catch (IOException e) {
                            System.out.println("Error: borrowed_books.txt file not found.");
                        }
                        System.out.println("Books borrowed successfully.");
                    }
                    break;

                case 5:
                    String ISBN = "";
                    String returnRegistrationNumber = "";
                    System.out.println("Please enter your registration number:");
                    scanner.nextLine();
                    returnRegistrationNumber = scanner.nextLine();
                    if (borrowedBooks.containsKey(returnRegistrationNumber)){
                        System.out.println("Please give the ISBN of the book you are returning");
                        ISBN = scanner.nextLine();
                        borrowedBooks.remove(returnRegistrationNumber, ISBN);
                        library.putBack(ISBN);
                        String inputFile = "src/borrowed_books.txt";
                        String lineToRemove = returnRegistrationNumber + "," + ISBN;
                        File tempFile = File.createTempFile("tempFile", ".txt");
                        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                        PrintWriter writer = new PrintWriter(tempFile);
                        String currentLine;
                        while ((currentLine = reader.readLine()) != null){
                            if (!currentLine.equals(lineToRemove)) {
                                writer.println(currentLine);
                            }
                        }
                        reader.close();
                        writer.close();
                        File editedFile = new File(inputFile);
                        editedFile.delete();
                        tempFile.renameTo(editedFile);
                    }else{
                        System.out.println("You do not have any books to return!");
                    }
                    break;

                case 6:
                    InventoryChart inventoryChart = new InventoryChart("Library Inventory Stats", library.availableBooks());
                    inventoryChart.displayGraph();
                    break;

                case 7:
                    System.out.println("Thank you for using the Library Management System. Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}