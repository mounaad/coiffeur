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
	    public Coiffeur getCoiffeurById(int id) {
	    	String sql = "SELECT * FROM coiffeur WHERE id_coiffeur = ?";

	        try (PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, id);

	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                Coiffeur c = new Coiffeur();
	                c.setIdCoiffeur(rs.getInt("id_coiffeur"));
	                c.setNom(rs.getString("nom"));
	                c.setAdresse(rs.getString("adresse"));
	                c.setEmail(rs.getString("email"));
	                c.setTelephone(rs.getString("telephone"));
	                c.setMotDePasse(rs.getString("mot_de_passe"));
	                c.setStatut(rs.getString("statut"));

	                return c;
	            }

	        } catch (Exception e) { e.printStackTrace(); }

	        return null;
	    }

		@Override
		public List<Coiffeur> getAll() {
			
			List<Coiffeur> list = new ArrayList<>();
			
			String sql = "SELECT * FROM coiffeur ORDER BY id_coiffeur";
			
			try(PreparedStatement ps = conn.prepareStatement(sql);
					ResultSet rs = ps.executeQuery()){
				
				while(rs.next()) {
					Coiffeur c= new Coiffeur();
					c.setIdCoiffeur(rs.getInt("id_coiffeur"));
					c.setNom(rs.getString("nom"));
					c.setAdresse(rs.getString("adresse"));
					c.setEmail(rs.getString("email"));
					c.setTelephone(rs.getString("telephone"));
					c.setMotDePasse(rs.getString("mot_de_passe"));
					c.setStatut(rs.getString("statut"));
					
					list.add(c);
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return list;
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
	         
	         
	         while (rs.next()) {
	             Coiffeur c = new Coiffeur();
	             
	             int idCoiffeur = rs.getInt("id_coiffeur");
	             String nom = rs.getString("nom");
	             
	             c.setIdCoiffeur(idCoiffeur);
	             c.setNom(nom);
	             
	             list.add(c);
	             
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
	  * 
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

		@Override
		public boolean add(Coiffeur c) {
			String sql = "INSERT INTO coiffeur (nom,adresse,email,telephone,mot_de_passe,statut) VALUES(?,?,?,?,?,'valide') ";
			
			try(PreparedStatement ps =conn.prepareStatement(sql)){
				ps.setString(1, c.getNom());
				ps.setString(2, c.getAdresse());
				ps.setString(3, c.getEmail());
				ps.setString(4, c.getTelephone());
				ps.setString(5, c.getMotDePasse());
			
				return ps.executeUpdate() > 0;
			}catch(Exception e) {
				e.printStackTrace();
			}
			return false;
		}


		@Override
		public boolean update(Coiffeur c) {
			String sql = "UPDATE coiffeur SET nom=?, adresse=?, email=?, telephone=? WHERE id_coiffeur=?";
			
			try(PreparedStatement ps = conn.prepareStatement(sql)){
				ps.setString(1, c.getNom());
				ps.setString(2, c.getAdresse());
				ps.setString(3, c.getEmail());
				ps.setString(4, c.getTelephone());
				ps.setInt(5, c.getIdCoiffeur());
				
				return ps.executeUpdate() > 0;
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		public boolean delete(int id) {
			String sql = "DELETE FROM coiffeur WHERE id_coiffeur=?";
			try(PreparedStatement ps = conn.prepareStatement(sql)){
				ps.setInt(1, id);
				return ps.executeUpdate() > 0;
			}catch(Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		
		
		public int countCoiffeurs() {
		    String sql = "SELECT COUNT(*) FROM coiffeur";
		    try (PreparedStatement ps = conn.prepareStatement(sql);
		         ResultSet rs = ps.executeQuery()) {
		        if (rs.next()) return rs.getInt(1);
		    } catch (Exception e) { e.printStackTrace(); }
		    return 0;
		}

	 
}
