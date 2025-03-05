package com.monprojet;

public class Utilisateur {
    // Attribut pour stocker le nom de l'utilisateur
    private String nom;

    // Constructeur par défaut
    public Utilisateur() {}

    // Constructeur avec paramètre
    public Utilisateur(String nom) {
        this.setNom(nom);
    }

    // Getter pour l'attribut nom
    public String getNom() {
        return nom;
    }

    // Setter pour l'attribut nom
    public void setNom(String nom) {
        if (nom != null && !nom.isEmpty()) {
            this.nom = nom;
        } else {
            throw new IllegalArgumentException("Le nom ne peut pas être vide ou null");
        }
    }

    // Méthode toString pour afficher les informations de l'utilisateur
    @Override
    public String toString() {
        return "Utilisateur{" +
                "nom='" + nom + '\'' +
                '}';
    }

    // Méthode equals pour comparer deux utilisateurs
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Utilisateur utilisateur = (Utilisateur) obj;
        return nom.equals(utilisateur.nom);
    }

    // Méthode hashCode pour générer un code unique pour chaque utilisateur
    @Override
    public int hashCode() {
        return nom.hashCode();
    }
}