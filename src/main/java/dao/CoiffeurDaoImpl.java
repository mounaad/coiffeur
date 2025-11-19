package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	    
	
	    
}
