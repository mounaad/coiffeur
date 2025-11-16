package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Client;

public class ClientDaoImp implements ClientDao {
	
	
	public boolean ajouterClient(Client client) {
        boolean success = false;
        String sql = "INSERT INTO client (prenom, nom, email, telephone, mot_de_passe) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Factory.getInstance().getConnection();

             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, client.getPrenom());
            stmt.setString(2, client.getNom());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getTelephone());
            stmt.setString(5, client.getMotDePasse());

            success = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
        	System.err.println("Erreur dans ajouterClient : " + e.getMessage());
        	e.printStackTrace();        }
        return success;
    }

    public boolean emailExiste(String email) {
        String sql = "SELECT * FROM client WHERE email = ?";
        try (Connection conn = Factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
	

}
