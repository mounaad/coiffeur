package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Service;

public class ServiceDaoImp implements ServiceDao {
	
	private Connection conn;

	public ServiceDaoImp(Connection conn) {
        this.conn = conn;
    }

	@Override
	
	public List<Service> getAllServices() {
	    String sql = "SELECT * FROM service";
	    List<Service> services = new ArrayList<>();

	    try (PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Service s = new Service(
	                rs.getInt("id_service"),
	                rs.getString("nom_service"),
	                rs.getString("description"),
	                rs.getInt("duree"),
	                rs.getDouble("prix"),
	                rs.getString("photo")
	            );
	            services.add(s);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return services;
	}



    @Override
    public boolean addService(Service service) {
        String sql = "INSERT INTO service (nom_service, description, duree, prix, photo) VALUES (?, ?, ?, ?,?)";

        try (Connection conn = Factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, service.getNom());
            ps.setString(2, service.getDescription());
            ps.setInt(3, service.getDuree());
            ps.setDouble(4, service.getPrix());
            ps.setString(5, service.getPhoto());


            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Service getServiceById(int id) {
        String sql = "SELECT * FROM service WHERE id_service=?";
        Service service = null;

        try (Connection conn = Factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                service = new Service(
                    rs.getInt("id_service"),
                    rs.getString("nom_service"),
                    rs.getString("description"),
                    rs.getInt("duree"),
                    rs.getDouble("prix"),
                    rs.getString("photo")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return service;
    }
}

