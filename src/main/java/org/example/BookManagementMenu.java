package org.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookManagementMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static BookManagementService bookService = new BookManagementService();

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("Book Management System Menu:");
            System.out.println("1. Create book");
            System.out.println("2. Update book");
            System.out.println("3. Find book");
            System.out.println("4. View all books");
            System.out.println("5. Export data to Excel (last 30 days)");
            System.out.println("6. Close service");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            try {
                switch (choice) {
                    case 1:
                        createBook();
                        break;
                    case 2:
                        updateBook();
                        break;
                    case 3:
                        System.out.print("Enter the keyword to search (book code, name, or title): ");
                        String keyword = scanner.nextLine();
                        findBook(keyword);
                        break;
                    case 4:
                        viewAllBooks();
                        break;
                    case 5:
                        exportDataToExcel();
                        break;
                    case 6:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }
            } catch (SQLException e) {
                System.out.println("An error occurred while performing the operation: " + e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Service closed.");
    }

    private static void createBook() throws SQLException {
        // Nhập thông tin cho cuốn sách mới
        System.out.println("Enter book information:");
        System.out.print("Book code: ");
        String bookCode = scanner.nextLine();
        System.out.print("Book name: ");
        String bookName = scanner.nextLine();
        System.out.print("Book title: ");
        String bookTitle = scanner.nextLine();
        System.out.print("Book description: ");
        String bookDescription = scanner.nextLine();
        System.out.print("Book type: ");
        String bookType = scanner.nextLine();
        System.out.print("Book price: ");
        double bookPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character

        Book book = new Book(bookCode, bookName, bookTitle, bookDescription, bookType, bookPrice);
        // Sử dụng bookService để thêm sách vào cơ sở dữ liệu
        bookService.insertBook(book);

        System.out.println("Book created successfully.");
    }

    private static void updateBook() throws SQLException {
        System.out.print("Enter the book code to update: ");
        String bookCode = scanner.nextLine();

        System.out.println("Enter updated book information:");
        System.out.print("Book name: ");
        String updatedBookName = scanner.nextLine();
        System.out.print("Book title: ");
        String updatedBookTitle = scanner.nextLine();
        System.out.print("Book description: ");
        String updatedBookDescription = scanner.nextLine();
        System.out.print("Book type: ");
        String updatedBookType = scanner.nextLine();
        System.out.print("Book price: ");
        double updatedBookPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character

        Book updatedBook = new Book(null, updatedBookName, updatedBookTitle,
                updatedBookDescription, updatedBookType, updatedBookPrice);

        // Sử dụng bookService để cập nhật thông tin sách trong cơ sở dữ liệu
        bookService.updateBook(bookCode, updatedBook);

        System.out.println("Book updated successfully.");
    }

    public static void findBook(String keyword) throws SQLException {
        bookService.findBook(keyword);
    }

    public static void viewAllBooks() throws SQLException {
        String query = "SELECT * FROM books";

        try (Connection connection = ConnectSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No books found.");
                return;
            }
            System.out.printf("%-12s %-30s %-30s %-30s %-12s %-10s%n",
                    "Book Code", "Book Name", "Book Title", "Description", "Type", "Price");
            while (resultSet.next()) {
                // Lấy thông tin của từng cuốn sách từ kết quả truy vấn và hiển thị
                String bookCode = resultSet.getString("book_code");
                String bookName = resultSet.getString("book_name");
                String bookTitle = resultSet.getString("book_title");
                String bookDescription = resultSet.getString("book_description");
                String bookType = resultSet.getString("book_type");
                double bookPrice = resultSet.getDouble("book_price");
                // Hiển thị thông tin của từng cuốn sách theo kiểu cột
                System.out.printf("%-12s %-30s %-30s %-30s %-12s %-10.2f%n",
                        bookCode, bookName, bookTitle, bookDescription, bookType, bookPrice);
            }
        }
    }

    private static void exportDataToExcel() throws SQLException, IOException {
        ExportData.exportDataToExcel();
    }
}
