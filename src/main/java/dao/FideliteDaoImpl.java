package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FideliteDaoImpl implements FideliteDao {

    private Connection conn;

    public FideliteDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void ajouterPoints(int idClient, int points) throws Exception {

        //Vérifier si le client a déjà une ligne dans fidélité
        String checkSql = "SELECT COUNT(*) FROM fidelite WHERE id_client = ?";
        PreparedStatement check = conn.prepareStatement(checkSql);
        check.setInt(1, idClient);
        ResultSet rs = check.executeQuery();
        rs.next();
        boolean exists = rs.getInt(1) > 0;

        if (!exists) {
            // Si pas d'entrée → créer la ligne
            String insertSql = "INSERT INTO fidelite (id_client, points, reduction_applicable, source, date_operation, description) " +
                               "VALUES (?, ?, FALSE, 'rendezvous', NOW(), 'Création du compte fidélité')";
            PreparedStatement insert = conn.prepareStatement(insertSql);
            insert.setInt(1, idClient);
            insert.setInt(2, points);
            insert.executeUpdate();
            return;
        }

        //Sinon : mise à jour
        String sql = "UPDATE fidelite SET points = points + ?, date_operation = NOW(), " +
                     "source='rendezvous', description='Points ajoutés après confirmation' " +
                     "WHERE id_client = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, points);
        ps.setInt(2, idClient);
        ps.executeUpdate();

        //Vérifier si le client a atteint 100 points
        int totalPoints = getPoints(idClient);
        if (totalPoints >= 100) {
            String sql2 = "UPDATE fidelite SET reduction_applicable = TRUE WHERE id_client = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, idClient);
            ps2.executeUpdate();
        }
    }

    @Override
    public int getPoints(int idClient) throws Exception {
        String sql = "SELECT points FROM fidelite WHERE id_client=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idClient);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) return rs.getInt("points");
        return 0;
    }

    @Override
    public boolean hasReduction(int idClient) throws Exception {
        String sql = "SELECT reduction_applicable FROM fidelite WHERE id_client=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idClient);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getBoolean("reduction_applicable");
        return false;
    }

    @Override
    public void utiliserReduction(int idClient) throws Exception {
        String sql = "UPDATE fidelite SET points = points - 100, reduction_applicable = FALSE WHERE id_client = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idClient);
        ps.executeUpdate();
    }

}
