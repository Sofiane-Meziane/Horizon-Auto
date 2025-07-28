/*package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/auto_ecole?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    // Private constructor to enforce Singleton pattern
    private DatabaseConnection() {}

    // Get the connection instance
    public static Connection getInstance() {
        try {
            // Check if connection is null or closed, and create a new one if needed
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    // Close the connection explicitly
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            e.printStackTrace();
        }
    }
}*/

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/auto_ecole?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    // Private constructor to enforce Singleton pattern
    private DatabaseConnection() {}

    // Get the connection instance
    public static Connection getInstance() {
        try {
            // Check if connection is null or closed, and create a new one if needed
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
}

