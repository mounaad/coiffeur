package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
