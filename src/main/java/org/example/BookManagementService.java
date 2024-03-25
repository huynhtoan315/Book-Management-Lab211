package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class BookManagementService {

    public void insertBook(Book book) throws SQLException {
        String query = "INSERT INTO books (book_code, book_name, book_title, book_description, book_type, book_price) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getBookCode());
            preparedStatement.setString(2, book.getBookName());
            preparedStatement.setString(3, book.getBookTitle());
            preparedStatement.setString(4, book.getBookDescription());
            preparedStatement.setString(5, book.getBookType());
            preparedStatement.setDouble(6, book.getBookPrice());

            preparedStatement.executeUpdate();
        }
    }
    public void updateBook(String bookCode, Book updatedBook) throws SQLException {
        String query = "UPDATE books SET book_name = ?, book_title = ?, book_description = ?, book_type = ?, book_price = ? WHERE book_code = ?";

        try (Connection connection = ConnectSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, updatedBook.getBookName());
            preparedStatement.setString(2, updatedBook.getBookTitle());
            preparedStatement.setString(3, updatedBook.getBookDescription());
            preparedStatement.setString(4, updatedBook.getBookType());
            preparedStatement.setDouble(5, updatedBook.getBookPrice());
            preparedStatement.setString(6, bookCode);

            preparedStatement.executeUpdate();
        }
    }

    public void findBook(String keyword) throws SQLException {
        String query = "SELECT * FROM books WHERE book_code = ? OR book_name LIKE ? OR book_title LIKE ?";

        try (Connection connection = ConnectSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Lấy thông tin của sách từ kết quả truy vấn và hiển thị
                String bookCode = resultSet.getString("book_code");
                String bookName = resultSet.getString("book_name");
                String bookTitle = resultSet.getString("book_title");
                String bookDescription = resultSet.getString("book_description");
                String bookType = resultSet.getString("book_type");
                double bookPrice = resultSet.getDouble("book_price");
                System.out.println("Book Code: " + bookCode + ", Book Name: " + bookName + ", Book Title: " + bookTitle +
                        ", Description: " + bookDescription + ", Type: " + bookType + ", Price: " + bookPrice);
            }
        }
    }
}


