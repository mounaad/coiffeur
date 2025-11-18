package model;

public class Service {

    private int id_service;
    private String nom_service;
    private String description;
    private int duree;
    private double prix;
    private String photo;

    public Service() {}

    public Service(int id_service, String nom_service, String description, int duree, double prix, String photo) {
        this.id_service = id_service;
        this.nom_service = nom_service;
        this.description = description;
        this.duree = duree;
        this.prix = prix;
        this.photo = photo;
    }

    public int getId() { return id_service; }
    public String getNom() { return nom_service; }
    public String getDescription() { return description; }
    public int getDuree() { return duree; }
    public double getPrix() { return prix; }
    public String getPhoto() { return photo; }

    public void setId(int id_service) { this.id_service = id_service; }
    public void setNom(String nom_service) { this.nom_service = nom_service; }
    public void setDescription(String description) { this.description = description; }
    public void setDuree(int duree) { this.duree = duree; }
    public void setPrix(double prix) { this.prix = prix; }
    public void setPhoto(String photo) { this.photo = photo; }
}
