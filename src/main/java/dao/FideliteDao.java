package dao;

public interface FideliteDao {

	 void ajouterPoints(int idClient, int points) throws Exception;
	 int getPoints(int idClient) throws Exception;
	 boolean hasReduction(int idClient) throws Exception;
	 void utiliserReduction(int idClient) throws Exception;
}
