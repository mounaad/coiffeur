package dao;


import java.util.List;
import model.Service;

public interface ServiceDao {
	    List<Service> getAllServices();
	    boolean addService(Service service);
	    Service getServiceById(int id);
	}
	


