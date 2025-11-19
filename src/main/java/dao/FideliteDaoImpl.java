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
        String sql = "UPDATE fidelite SET points = points + ?, date_operation = NOW(), " +
                     "source='rdv', description='Points ajoutés après confirmation' " +
                     "WHERE id_client = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, points);
        ps.setInt(2, idClient);
        ps.executeUpdate();

        // Vérifier si le client atteint 100 points
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
        // Retirer 100 points et désactiver la réduction
        String sql = "UPDATE fidelite SET points = points - 100, reduction_applicable = FALSE WHERE id_client = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idClient);
        ps.executeUpdate();
    }

}
