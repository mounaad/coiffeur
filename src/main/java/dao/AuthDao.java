package dao;

import java.sql.*;
import model.User;

public class AuthDao {
	private Connection conn;

	 public AuthDao(Factory factory) {
	        try {
				this.conn = Factory.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
    public User login(String usernameOrEmail, String password) throws SQLException {
    	 User user = null;
        
        String sqlAdmin = "SELECT * FROM admin WHERE username=? AND mot_de_passe=?";
        try (PreparedStatement pst = conn.prepareStatement(sqlAdmin)) {
            pst.setString(1, usernameOrEmail);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id_admin"), rs.getString("username"), "admin");
                }
            }
        }

        
        String sqlCoiffeur = "SELECT * FROM coiffeur WHERE email=? AND mot_de_passe=? AND statut='valide'";
        try (PreparedStatement pst = conn.prepareStatement(sqlCoiffeur)) {
            pst.setString(1, usernameOrEmail);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id_coiffeur"), rs.getString("nom"), "coiffeur");
                }
            }
        }

        
        String sqlClient = "SELECT * FROM client WHERE email=? AND mot_de_passe=?";
        try (PreparedStatement pst = conn.prepareStatement(sqlClient)) {
            pst.setString(1, usernameOrEmail);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id_client"), rs.getString("nom"), "client");
                }
            }
        }

        return user;
    }
}
