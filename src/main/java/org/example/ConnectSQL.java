package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectSQL {
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;"
            + "databaseName=BookManagement;"
            + "encrypt=true;trustServerCertificate=true";
    private static final String USERNAME = "htoan";
    private static final String PASSWORD = "1";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

    public static class TestConnection {
        public void main(String[] args) {
            try {
                Connection connection = ConnectSQL.getConnection();
                System.out.println("Connection to SQL Server successful!");
                // Nếu bạn muốn kiểm tra thêm các thao tác truy vấn hoặc thao tác khác với cơ sở dữ liệu, bạn có thể thực hiện ở đây.
                connection.close(); // Đóng kết nối sau khi sử dụng
            } catch (SQLException e) {
                System.out.println("Connection to SQL Server failed!");
                e.printStackTrace();
            }
        }
    }
}
