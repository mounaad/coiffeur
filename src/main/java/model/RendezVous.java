package model;

import java.sql.Date;
import java.sql.Time;

public class RendezVous {
	
	    private int idRdv;
	    private int idClient;
	    private int idCoiffeur;
	    private Date dateRdv;
	    private Time heureRdv;
	    private String statut;
	    private String nomClient;
	    private String prenomClient;

	    public RendezVous() {}

	    public RendezVous(int idRdv, int idClient, int idCoiffeur, Date dateRdv, Time heureRdv, String statut, String nomClient, String prenomClient) {
	        this.idRdv = idRdv;
	        this.idClient = idClient;
	        this.idCoiffeur = idCoiffeur;
	        this.dateRdv = dateRdv;
	        this.heureRdv = heureRdv;
	        this.statut = statut;
	        this.nomClient = nomClient;
	        this.prenomClient = prenomClient;
	    }

	    // Getters et setters
	    public int getIdRdv() { return idRdv; }
	    public int getIdClient() { return idClient; }
	    public int getIdCoiffeur() { return idCoiffeur; }
	    public Date getDateRdv() { return dateRdv; }
	    public Time getHeureRdv() { return heureRdv; }
	    public String getStatut() { return statut; }
	    public String getNomClient() { return nomClient; }
	    public String getPrenomClient() { return prenomClient; }
 
	    public void setIdRdv(int idRdv) { this.idRdv = idRdv; }
	    public void setIdClient(int idClient) { this.idClient = idClient; }
	    public void setIdCoiffeur(int idCoiffeur) { this.idCoiffeur = idCoiffeur; }
	    public void setDateRdv(Date dateRdv) { this.dateRdv = dateRdv; }
	    public void setHeureRdv(Time heureRdv) { this.heureRdv = heureRdv; }
	    public void setStatut(String statut) { this.statut = statut; }
	    public void setNomClient(String nomClient) { this.nomClient = nomClient; }
	    public void setPrenomClient(String prenomClient) { this.prenomClient = prenomClient; }
	

}
