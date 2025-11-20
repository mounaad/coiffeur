package dao;

import model.RendezVous;
import java.sql.SQLException;
import java.util.List;

public interface RendezVousDao {


    List<RendezVous> getRendezVousByCoiffeur(int idCoiffeur) throws SQLException;

    
    void confirmerRdv(int idRdv) throws SQLException;
    void annulerRdv(int idRdv) throws SQLException;
    public boolean addRendezvous(RendezVous rdv);
    List<RendezVous> getRendezVousByClient(int idClient) throws SQLException;

}
