package org.example;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ExportData {
    public static void exportDataToExcel() throws SQLException, IOException {
        String query = "SELECT * FROM books WHERE created_at >= DATEADD(DAY, -30, GETDATE())";

        try (Connection connection = ConnectSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            // Tạo file Excel
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Books");

            // Tạo header
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Book Code");
            headerRow.createCell(1).setCellValue("Book Name");
            headerRow.createCell(2).setCellValue("Book Title");
            headerRow.createCell(3).setCellValue("Book Description");
            headerRow.createCell(4).setCellValue("Book Type");
            headerRow.createCell(5).setCellValue("Book Price");
            headerRow.createCell(6).setCellValue("Created At");

            int rowNum = 1;
            while (resultSet.next()) {
                XSSFRow row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(resultSet.getString("book_code"));
                row.createCell(1).setCellValue(resultSet.getString("book_name"));
                row.createCell(2).setCellValue(resultSet.getString("book_title"));
                row.createCell(3).setCellValue(resultSet.getString("book_description"));
                row.createCell(4).setCellValue(resultSet.getString("book_type"));
                row.createCell(5).setCellValue(resultSet.getDouble("book_price"));

                // Chuyển đổi created_at từ Timestamp sang LocalDateTime
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                LocalDateTime localDateTime = createdAt.toLocalDateTime();

                // Chuyển đổi LocalDateTime sang Date
                Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

                row.createCell(6).setCellValue(date);
            }

            // Ghi file Excel
            FileOutputStream outputStream = new FileOutputStream("books.xlsx");
            workbook.write(outputStream);
            outputStream.close();

            System.out.println("Data exported to books.xlsx successfully.");
        }
    }
}
