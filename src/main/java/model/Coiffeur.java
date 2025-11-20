package model;

public class Coiffeur {

	private int idCoiffeur;
    private String nom;
    private String adresse;
    private String email;
    private String motDePasse;
    private String telephone;
    private String statut;

    public Coiffeur() {}

    public Coiffeur(int idCoiffeur, String nom, String adresse, String email,
                    String motDePasse, String telephone, String statut) {
        this.idCoiffeur = idCoiffeur;
        this.nom = nom;
        this.adresse = adresse;
        this.email = email;
        this.motDePasse = motDePasse;
        this.telephone = telephone;
        this.statut = statut;
    }

    public int getIdCoiffeur() {
        return idCoiffeur;
    }
    
    public int getId() {
        return idCoiffeur;
    } 

    public void setId(int id) {
        this.idCoiffeur = id; 
    }

    public void setIdCoiffeur(int idCoiffeur) {
        this.idCoiffeur = idCoiffeur;
    }

    public String getNom() {
        return nom;
    } 

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
