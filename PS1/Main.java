import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        // Database connection settings
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "a19082004";
        String databaseName = "akhil";

        // Load the JDBC driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading JDBC driver: " + e.getMessage());
            return;
        }

        // Create a connection to the database
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // Create the database if it doesn't exist
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + databaseName);

            // Switch to the created database
            stmt.executeUpdate("USE " + databaseName);

            // Execute the SQL statements to create tables
            String[] sqlStatements = {
                    "DROP TABLE IF EXISTS OrderItems CASCADE;",
                    "DROP TABLE IF EXISTS Orders CASCADE;",
                    "DROP TABLE IF EXISTS UserProfiles CASCADE;",
                    "DROP TABLE IF EXISTS Users CASCADE;",
                    "DROP TABLE IF EXISTS Categories CASCADE;",
                    "DROP TABLE IF EXISTS Customers CASCADE;",

                    "CREATE TABLE Customers (" +
                            "customer_id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "first_name VARCHAR(255) NOT NULL, " +
                            "last_name VARCHAR(255) NOT NULL, " +
                            "email VARCHAR(255) NOT NULL UNIQUE, " +
                            "phone VARCHAR(20)" +
                            ");",

                    "CREATE TABLE Categories (" +
                            "category_id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "name VARCHAR(255) NOT NULL, " +
                            "description TEXT" +
                            ");",

                    "CREATE TABLE Users (" +
                            "user_id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "username VARCHAR(255) NOT NULL UNIQUE, " +
                            "password VARCHAR(255) NOT NULL, " +
                            "email VARCHAR(255) NOT NULL UNIQUE" +
                            ");",

                    "CREATE TABLE UserProfiles (" +
                            "profile_id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "user_id INT NOT NULL UNIQUE, " +
                            "address VARCHAR(255), " +
                            "date_of_birth DATE, " +
                            "phone_number VARCHAR(20), " +
                            "FOREIGN KEY (user_id) REFERENCES Users(user_id)" +
                            ");",

                    "CREATE TABLE Orders (" +
                            "order_id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "customer_id INT NOT NULL, " +
                            "order_date DATETIME NOT NULL, " +
                            "status VARCHAR(50), " +
                            "FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)" +
                            ");",

                    "CREATE TABLE OrderItems (" +
                            "order_item_id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "order_id INT NOT NULL, " +
                            "product_id INT NOT NULL, " +
                            "quantity INT NOT NULL, " +
                            "price DECIMAL(10, 2) NOT NULL, " +
                            "FOREIGN KEY (order_id) REFERENCES Orders(order_id)" +
                            ");"
            };

            for (String sql : sqlStatements) {
                stmt.executeUpdate(sql);
                System.out.println("Executed: " + sql);
            }

            System.out.println("Database created successfully!");
        } catch (SQLException e) {
            System.out.println("Error creating database: " + e.getMessage());
        }
    }
}