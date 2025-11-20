package dao;

import java.util.List;

import model.Client;


public interface ClientDao {
	
	public boolean ajouterClient(Client client);
	public boolean emailExiste(String email);
	public Client getClientById(int id);
	public Client getClienttById(int id);
	List<Client> getAll();
    boolean add(Client c);
    boolean update(Client c);
    boolean delete(int id);
	

}
