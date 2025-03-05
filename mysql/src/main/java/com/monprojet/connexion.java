import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App.java {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/maBase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public static void main(String[] args) {
        try (Connection connexion = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Connexion réussie !");
        } catch (SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
    }
}