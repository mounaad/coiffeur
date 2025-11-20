package dao;

import java.util.List;

import model.Coiffeur;

public interface CoiffeurDao {

	
	 Coiffeur getCoiffeurById(int id);
	 List<Coiffeur> getAll();
	 boolean add(Coiffeur c);
	 boolean update(Coiffeur c);
     boolean delete(int id);
 	public int countCoiffeurs();
	    
	  
}
