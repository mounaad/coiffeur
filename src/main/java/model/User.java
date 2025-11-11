package model;

public class User {
    private int id;
    private String nom;
    private String role;
    
    public User() {}

    public User(int id, String nom, String role) {
        this.id = id;
        this.nom = nom;
        this.role = role;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getRole() { return role; }
    
    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setRole(String role) { this.role = role; }
}
