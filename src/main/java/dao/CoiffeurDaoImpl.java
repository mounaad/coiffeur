package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Coiffeur;

public class CoiffeurDaoImpl implements CoiffeurDao {

	 private Connection conn;

	    public CoiffeurDaoImpl(Connection conn) {
	        this.conn = conn;
	    }
	   

	    @Override
	    public Coiffeur getCoiffeurByEmailAndPassword(String email, String password) {
	        Coiffeur c = null;

	        String sql = "SELECT * FROM coiffeur WHERE email = ? AND mot_de_passe = ? AND statut = 'valide'";

	        try (PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setString(1, email);
	            ps.setString(2, password); 

	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                c = mapRow(rs);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return c;
	    }

	    @Override
	    public Coiffeur getCoiffeurById(int id) {
	        Coiffeur c = null;

	        String sql = "SELECT * FROM coiffeur WHERE id_coiffeur = ?";

	        try (PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, id);

	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                c = mapRow(rs);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return c;
	    }

	    private Coiffeur mapRow(ResultSet rs) throws Exception {
	        Coiffeur c = new Coiffeur();

	        c.setIdCoiffeur(rs.getInt("id_coiffeur"));
	        c.setNom(rs.getString("nom"));
	        c.setAdresse(rs.getString("adresse"));
	        c.setEmail(rs.getString("email"));
	        c.setMotDePasse(rs.getString("mot_de_passe"));
	        c.setTelephone(rs.getString("telephone"));
	        c.setStatut(rs.getString("statut"));

	        return c;
	    }


	 

	 public List<Coiffeur> getCoiffeursDisponibles(int id_service, String date, String heure) {
	     List<Coiffeur> list = new ArrayList<>();
	     
	  	     String sql =
	         "SELECT c.id_coiffeur, c.nom " +
	         "FROM coiffeur c " +
	         "JOIN coiffeur_service cs ON c.id_coiffeur = cs.id_coiffeur " +
	         "WHERE cs.id_service = ?";
	     
	     
	     PreparedStatement ps = null;
	     ResultSet rs = null;
	     
	     try {
	         ps = conn.prepareStatement(sql);
	         ps.setInt(1, id_service);
	         
	         rs = ps.executeQuery();
	         
	         int count = 0;
	         while (rs.next()) {
	             Coiffeur c = new Coiffeur();
	             
	             int idCoiffeur = rs.getInt("id_coiffeur");
	             String nom = rs.getString("nom");
	             
	             c.setIdCoiffeur(idCoiffeur);
	             c.setNom(nom);
	             
	             list.add(c);
	             count++;
	             
	         }
	         
	         
	     } catch (SQLException e) {
	         
	         e.printStackTrace();
	     } finally {
	         try {
	             if (rs != null) rs.close();
	             if (ps != null) ps.close();
	         } catch (SQLException e) {
	             e.printStackTrace();
	         }
	     }
	     
	     return list;
	 }

	 /**
	  * VERSION AVEC VÉRIFICATION DES DISPONIBILITÉS
	  * À utiliser après avoir vérifié que la version simple fonctionne
	  */
	 public List<Coiffeur> getCoiffeursDisponiblesAvecVerif(int id_service, String date, String heure) {
	     List<Coiffeur> list = new ArrayList<>();
	     
	     String sql =
	         "SELECT c.id_coiffeur, c.nom " +
	         "FROM coiffeur c " +
	         "JOIN coiffeur_service cs ON c.id_coiffeur = cs.id_coiffeur " +
	         "WHERE cs.id_service = ? " +
	         "AND c.id_coiffeur NOT IN (" +
	             "SELECT COALESCE(id_coiffeur, 0) " +
	             "FROM rendezvous " +
	             "WHERE date_rdv = ? " +
	             "AND heure_rdv = ?" +
	         ")";
	     
	     PreparedStatement ps = null;
	     ResultSet rs = null;
	     
	     try {
	         ps = conn.prepareStatement(sql);
	         ps.setInt(1, id_service);
	         ps.setString(2, date);
	         ps.setString(3, heure);
	         
	         rs = ps.executeQuery();
	         
	         while (rs.next()) {
	             Coiffeur c = new Coiffeur();
	             c.setIdCoiffeur(rs.getInt("id_coiffeur"));
	             c.setNom(rs.getString("nom"));
	             
	             list.add(c);
	         }
	         
	     } catch (SQLException e) {
	         System.out.println("❌ ERREUR SQL: " + e.getMessage());
	         e.printStackTrace();
	     } finally {
	         try {
	             if (rs != null) rs.close();
	             if (ps != null) ps.close();
	         } catch (SQLException e) {
	             e.printStackTrace();
	         }
	     }
	     
	     return list;
	 }
}
