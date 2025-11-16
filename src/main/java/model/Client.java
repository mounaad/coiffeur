package model;

public class Client {
	private int id_client;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String mot_de_passe;
    private int points_fidelite;
    
    public Client() {}
    
    public Client(String nom, String prenom, String email, String telephone, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.mot_de_passe = motDePasse;
        this.points_fidelite = 0;
    }

    // Getters et Setters
    public int getId() {
    	return id_client; 
    }
    public void setId(int id_client) { 
    	this.id_client = id_client; 
    }
    
    public String getNom() { 
    	return nom; 
    }
    public void setNom(String nom) { 
    	this.nom = nom; 
    }
    
    public String getPrenom() { 
    	return prenom; 
    }
    public void setPrenom(String prenom) { 
    	this.prenom = prenom; 
    }
    
    public String getEmail() { 
    	return email; 
    }
    public void setEmail(String email) { 
    	this.email = email; 
    }
    
    public String getTelephone() {
    	return telephone; 
    }
    public void setTelephone(String telephone) { 
    	this.telephone = telephone; 
    }
    
    public String getMotDePasse() { 
    	return mot_de_passe; 
   	}
    public void setMotDePasse(String mot_de_passe) { 
    	this.mot_de_passe = mot_de_passe; 
   	}
    
    public int getPointsFidelite() { 
    	return points_fidelite; 
    }
    public void setPointsFidelite(int points_fidelite) { 
    	this.points_fidelite = points_fidelite; 
    }

}
