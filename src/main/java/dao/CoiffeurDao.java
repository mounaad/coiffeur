package dao;

import model.Coiffeur;

public interface CoiffeurDao {

	 Coiffeur getCoiffeurByEmailAndPassword(String email, String password);

	    Coiffeur getCoiffeurById(int id);
}
