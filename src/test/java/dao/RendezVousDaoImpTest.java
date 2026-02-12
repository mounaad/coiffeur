package dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.RendezVous;

class RendezVousDaoImpTest {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private RendezVousDaoImpl rdvDao;

    @BeforeEach
    void setup() {
        conn = mock(Connection.class);
        ps = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);
        rdvDao = new RendezVousDaoImpl(conn);
    }

    @Test
    void testGetRendezVousByCoiffeur() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false); // 1 rendez-vous
        when(rs.getInt("id_rdv")).thenReturn(1);
        when(rs.getInt("id_client")).thenReturn(2);
        when(rs.getInt("id_coiffeur")).thenReturn(3);
        when(rs.getDate("date_rdv")).thenReturn(Date.valueOf("2026-02-11"));
        when(rs.getTime("heure_rdv")).thenReturn(Time.valueOf("10:00:00"));
        when(rs.getString("statut")).thenReturn("confirme");
        when(rs.getString("nom")).thenReturn("Ali");
        when(rs.getString("prenom")).thenReturn("Ben");
        when(rs.getInt("id_service")).thenReturn(5);
        when(rs.getString("nom_service")).thenReturn("Coupe");
        when(rs.getDouble("prix")).thenReturn(50.0);

        List<RendezVous> rdvs = rdvDao.getRendezVousByCoiffeur(3);

        assertNotNull(rdvs);
        assertEquals(1, rdvs.size());
        assertEquals(1, rdvs.get(0).getIdRdv());
        assertEquals("Ali", rdvs.get(0).getNomClient());
    }

    @Test
    void testGetRendezVousByClient() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("id_rdv")).thenReturn(1);
        when(rs.getDate("date_rdv")).thenReturn(Date.valueOf("2026-02-11"));
        when(rs.getTime("heure_rdv")).thenReturn(Time.valueOf("10:00:00"));
        when(rs.getString("statut")).thenReturn("confirme");
        when(rs.getString("service")).thenReturn("Coupe");
        when(rs.getDouble("prix")).thenReturn(50.0);

        List<RendezVous> rdvs = rdvDao.getRendezVousByClient(2);

        assertNotNull(rdvs);
        assertEquals(1, rdvs.size());
        assertEquals("Coupe", rdvs.get(0).getNomService());
    }

    @Test
    void testAddRendezvous() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        RendezVous r = new RendezVous();
        r.setIdClient(2);
        r.setIdService(5);
        r.setDateRdv(Date.valueOf("2026-02-11"));
        r.setHeureRdv(Time.valueOf("10:00:00"));
        r.setStatut("confirme");

        assertTrue(rdvDao.addRendezvous(r));
    }

    @Test
    void testConfirmerRdv() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);

        rdvDao.confirmerRdv(1);

        verify(ps).setInt(1, 1);
        verify(ps).executeUpdate();
    }

    @Test
    void testAnnulerRdv() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);

        rdvDao.annulerRdv(1);

        verify(ps).setInt(1, 1);
        verify(ps).executeUpdate();
    }
}