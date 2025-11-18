package dao;


import model.RendezVous;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RendezVousDaoImpl implements RendezVousDao {

    private Connection conn;

    public RendezVousDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<RendezVous> getRendezVousByCoiffeur(int idCoiffeur) throws SQLException {
        List<RendezVous> rdvs = new ArrayList<>();
        String sql = "SELECT r.id_rdv, r.id_client, r.id_coiffeur, r.date_rdv, r.heure_rdv, r.statut, c.nom, c.prenom " +
                     "FROM rendezvous r JOIN client c ON r.id_client = c.id_client " +
                     "WHERE r.id_coiffeur = ? ORDER BY r.date_rdv, r.heure_rdv";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idCoiffeur);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            rdvs.add(new RendezVous(
                rs.getInt("id_rdv"),
                rs.getInt("id_client"),
                rs.getInt("id_coiffeur"),
                rs.getDate("date_rdv"),
                rs.getTime("heure_rdv"),
                rs.getString("statut"),
                rs.getString("nom"),
                rs.getString("prenom")
            ));
        } 
        return rdvs;
    }

    @Override
    public void confirmerRdv(int idRdv) throws SQLException {
        String sql = "UPDATE rendezvous SET statut='confirme' WHERE id_rdv=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idRdv);
        ps.executeUpdate();
    }

    @Override
    public void annulerRdv(int idRdv) throws SQLException {
        String sql = "UPDATE rendezvous SET statut='annule' WHERE id_rdv=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idRdv);
        ps.executeUpdate();
    }
    
    public boolean addRendezvous(RendezVous rdv) {
        String sql = "INSERT INTO rendezvous (id_client, id_service, date_rdv, heure_rdv, statut) VALUES (?,?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rdv.getIdClient());
            ps.setInt(2, rdv.getIdService());
            ps.setDate(3, rdv.getDate());
            ps.setTime(4, rdv.getHeure());
            ps.setString(5, rdv.getStatut());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
