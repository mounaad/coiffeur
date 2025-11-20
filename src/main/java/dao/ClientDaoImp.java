package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Client;

public class ClientDaoImp implements ClientDao {

	 private Connection conn;
	    public ClientDaoImp(Connection conn) {
	        this.conn = conn; 
	    }
	
	public ClientDaoImp() {
		}

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

    public Client getClienttById(int id) {
        Client c = null;
        String sql = "SELECT * FROM client WHERE id_client = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                c = new Client();
                c.setId(rs.getInt("id_client"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setEmail(rs.getString("email"));
                c.setTelephone(rs.getString("telephone"));
                c.setMotDePasse(rs.getString("mot_de_passe"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public Client getClientById(int id) {
        Client client = null;
        String sql = "SELECT * FROM client WHERE id_client = ?";

        try (Connection conn = Factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                client = new Client(
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("telephone"),
                    rs.getString("mot_de_passe")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
    
    
    @Override
	public List<Client> getAll() {
		List<Client> list = new ArrayList<>();
        String sql = "SELECT * FROM client ORDER BY id_client";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Client c = new Client();
                c.setId(rs.getInt("id_client"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setEmail(rs.getString("email"));
                c.setTelephone(rs.getString("telephone"));
                list.add(c);
            }

        } catch (Exception e) { e.printStackTrace(); }

        return list;
	}

	@Override
	public boolean add(Client c) {
		String sql = "INSERT INTO client (nom, prenom, email, telephone, mot_de_passe) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNom());
            ps.setString(2, c.getPrenom());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getTelephone());
            ps.setString(5, c.getMotDePasse());
            
            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }

        return false;

	}

	@Override
	public boolean update(Client c) {
		String sql = "UPDATE client SET nom=?, prenom=?, email=?, telephone=? WHERE id_client=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNom());
            ps.setString(2, c.getPrenom());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getTelephone());
            ps.setInt(5, c.getId());
            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }

        return false;
	}

	@Override
	public boolean delete(int id) {
		 String sql = "DELETE FROM client WHERE id_client=?";

	        try (PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, id);
	            return ps.executeUpdate() > 0;

	        } catch (Exception e) { e.printStackTrace(); }

	        return false;
	}
	
	public int countClients() {
	    String sql = "SELECT COUNT(*) FROM client";
	    try (PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	        if (rs.next()) return rs.getInt(1);
	    } catch (Exception e) { e.printStackTrace(); }
	    return 0;
	}

}
