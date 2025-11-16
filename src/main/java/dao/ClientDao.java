package dao;

import model.Client;

public interface ClientDao {
	public boolean ajouterClient(Client client);
	
	public boolean emailExiste(String email);


}
