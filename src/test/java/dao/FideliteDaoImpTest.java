package dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FideliteDaoImpTest {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private FideliteDaoImpl dao;

    @BeforeEach
    void setup() {
        conn = mock(Connection.class);
        ps = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);
        dao = new FideliteDaoImpl(conn);
    }

    @Test
    void testGetPoints() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("points")).thenReturn(50);

        int points = dao.getPoints(1);
        assertEquals(50, points);
    }

    @Test
    void testHasReduction() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getBoolean("reduction_applicable")).thenReturn(true);

        boolean hasReduction = dao.hasReduction(1);
        assertTrue(hasReduction);
    }

    @Test
    void testAjouterPoints_NewClient() throws Exception {
        PreparedStatement insertPs = mock(PreparedStatement.class);

        // client n'existe pas
        when(conn.prepareStatement(contains("SELECT COUNT(*)"))).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(0); // pas encore de ligne fidélité

        when(conn.prepareStatement(contains("INSERT INTO fidelite"))).thenReturn(insertPs);
        when(insertPs.executeUpdate()).thenReturn(1);

        dao.ajouterPoints(1, 10);
        verify(insertPs, times(1)).executeUpdate();
    }

    @Test
    void testAjouterPoints_ExistingClient() throws Exception {
        PreparedStatement updatePs = mock(PreparedStatement.class);

        // client existe déjà
        when(conn.prepareStatement(contains("SELECT COUNT(*)"))).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(1);

        when(conn.prepareStatement(contains("UPDATE fidelite SET points = points +"))).thenReturn(updatePs);
        when(updatePs.executeUpdate()).thenReturn(1);

        when(conn.prepareStatement(contains("SELECT points"))).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("points")).thenReturn(50);

        dao.ajouterPoints(1, 10);
        verify(updatePs, times(1)).executeUpdate();
    }

    @Test
    void testUtiliserReduction() throws Exception {
        PreparedStatement updatePs = mock(PreparedStatement.class);
        when(conn.prepareStatement(anyString())).thenReturn(updatePs);
        when(updatePs.executeUpdate()).thenReturn(1);

        dao.utiliserReduction(1);
        verify(updatePs, times(1)).executeUpdate();
    }
}