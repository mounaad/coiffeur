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
        String sql = "SELECT r.id_rdv, r.id_client, r.id_coiffeur, \r\n"
        		+ "                     r.date_rdv, r.heure_rdv, r.statut,\r\n"
        		+ "                     c.nom, c.prenom,\r\n"
        		+ "                     s.id_service, s.nom_service, s.prix\r\n"
        		+ "              FROM rendezvous r\r\n"
        		+ "              JOIN client c ON r.id_client = c.id_client\r\n"
        		+ "              JOIN service s ON r.id_service = s.id_service\r\n"
        		+ "              WHERE r.id_coiffeur = ?\r\n"
        		+ "              ORDER BY r.date_rdv, r.heure_rdv";
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
                rs.getString("prenom"),
                rs.getInt("id_service"),
                rs.getString("nom_service"),
                rs.getDouble("prix")
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
            ps.setDate(3, rdv.getDateRdv());
            ps.setTime(4, rdv.getHeureRdv());
            ps.setString(5, rdv.getStatut());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<RendezVous> getRendezVousByClient(int idClient) throws SQLException {
        List<RendezVous> list = new ArrayList<>();

        String sql = "SELECT r.id_rdv, r.date_rdv, r.heure_rdv, r.statut, " +
                "s.nom_service AS service, s.prix " + // ← Ajout du prix
                "FROM rendezvous r " +
                "INNER JOIN service s ON r.id_service = s.id_service " +
                "WHERE r.id_client = ? " +
                "ORDER BY r.date_rdv DESC, r.heure_rdv DESC";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idClient);

        try (ResultSet rs = ps.executeQuery()){

        while (rs.next()) {
            RendezVous r = new RendezVous();
            r.setIdRdv(rs.getInt("id_rdv"));
            r.setDateRdv(rs.getDate("date_rdv"));
            r.setHeureRdv(rs.getTime("heure_rdv"));
            r.setPrix(rs.getDouble("prix")); 
            r.setNomService(rs.getString("service"));
            r.setStatut(rs.getString("statut"));

            list.add(r);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}



