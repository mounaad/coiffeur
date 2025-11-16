package model;

public class Service {
    private int id;
    private String nom;
    private String description;
    private int duree;
    private double prix;
    private String photo;

    public Service() {}

    public Service(int id, String nom, String description, int duree, double prix, String photo) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.duree = duree;
        this.prix = prix;
        this.photo = photo; 
    }

    public Service(String nom, String description, int duree, double prix) {
        this.nom = nom;
        this.description = description;
        this.duree = duree;
        this.prix = prix;
        this.photo = photo; 

    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public int getDuree() { return duree; }
    public double getPrix() { return prix; }
    public String getPhoto() { return photo;}

    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setDescription(String description) { this.description = description; }
    public void setDuree(int duree) { this.duree = duree; }
    public void setPrix(double prix) { this.prix = prix; }
    public void setPhoto(String photo) { this.photo = photo; }

}



