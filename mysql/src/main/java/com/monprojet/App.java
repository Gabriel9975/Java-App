package com.monprojet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    private static Connection connexion;

    public static void main(String[] args) {
        // Informations de connexion
        String url = "jdbc:mysql://localhost:3306/maBase"; // Remplacer "maBase" par le nom de votre base
        String utilisateur = "root";
        String motDePasse = "";

        try {
            // Établir la connexion
            connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            System.out.println("Connexion réussie !");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Choisissez une option :");
                System.out.println("1. Lister les utilisateurs");
                System.out.println("2. Supprimer un utilisateur");
                System.out.println("3. Éditer un utilisateur");
                System.out.println("4. Rechercher un utilisateur par email");
                System.out.println("5. Quitter");
                int choix = scanner.nextInt();
                scanner.nextLine(); // Pour consommer la nouvelle ligne

                switch (choix) {
                    case 1:
                        listerUtilisateurs();
                        break;
                    case 2:
                        System.out.println("Entrez l'ID de l'utilisateur à supprimer :");
                        int idSupprimer = scanner.nextInt();
                        supprimerUtilisateur(idSupprimer);
                        break;
                    case 3:
                        System.out.println("Entrez l'ID de l'utilisateur à éditer :");
                        int idEditer = scanner.nextInt();
                        scanner.nextLine(); // Pour consommer la nouvelle ligne
                        System.out.println("Entrez le nouveau nom :");
                        String nouveauNom = scanner.nextLine();
                        System.out.println("Entrez le nouvel email :");
                        String nouvelEmail = scanner.nextLine();
                        editerUtilisateur(idEditer, nouveauNom, nouvelEmail);
                        break;
                    case 4:
                        System.out.println("Entrez l'email de l'utilisateur à rechercher :");
                        String emailRechercher = scanner.nextLine();
                        rechercherUtilisateurParEmail(emailRechercher);
                        break;
                    case 5:
                        System.out.println("Au revoir !");
                        return;
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        } finally {
            // Toujours fermer la connexion pour éviter les fuites de ressources
            if (connexion != null) {
                try {
                    connexion.close();
                    System.out.println("Connexion fermée avec succès.");
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
                }
            }
        }
    }

    private static void listerUtilisateurs() throws SQLException {
        String sql = "SELECT * FROM utilisateurs";
        try (PreparedStatement statement = connexion.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String email = resultSet.getString("email");
                System.out.println("ID: " + id + ", Nom: " + nom + ", Email: " + email);
            }
        }
    }

    private static void supprimerUtilisateur(int id) throws SQLException {
        String sql = "DELETE FROM utilisateurs WHERE id = ?";
        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Utilisateur supprimé avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet ID.");
            }
        }
    }

    private static void editerUtilisateur(int id, String nouveauNom, String nouvelEmail) throws SQLException {
        String sql = "UPDATE utilisateurs SET nom = ?, email = ? WHERE id = ?";
        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setString(1, nouveauNom);
            statement.setString(2, nouvelEmail);
            statement.setInt(3, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Utilisateur mis à jour avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet ID.");
            }
        }
    }

    private static void rechercherUtilisateurParEmail(String email) throws SQLException {
        String sql = "SELECT * FROM utilisateurs WHERE email = ?";
        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nom = resultSet.getString("nom");
                    System.out.println("ID: " + id + ", Nom: " + nom + ", Email: " + email);
                } else {
                    System.out.println("Aucun utilisateur trouvé avec cet email.");
                }
            }
        }
    }
}

