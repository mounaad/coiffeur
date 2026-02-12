package dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Coiffeur;

class CoiffeurDaoImpTest {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private CoiffeurDaoImpl dao;
    @BeforeEach
    void setup(){
        conn = mock(Connection.class);
        ps = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);

        dao = new CoiffeurDaoImpl(conn);
    }
    @Test
    void testGetCoiffeurById() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);

        when(rs.getInt("id_coiffeur")).thenReturn(1);
        when(rs.getString("nom")).thenReturn("Ali");

        Coiffeur c = dao.getCoiffeurById(1);

        assertNotNull(c);
        assertEquals(1, c.getIdCoiffeur());
        assertEquals("Ali", c.getNom());
    }

    @Test
    void testAddCoiffeur() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        Coiffeur c = new Coiffeur();
        c.setNom("Sara");
        c.setAdresse("Casa");
        c.setEmail("s@mail.com");
        c.setTelephone("0600");
        c.setMotDePasse("pwd");

        assertTrue(dao.add(c));
    }

    @Test
    void testDeleteCoiffeur() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        assertTrue(dao.delete(1));
    }

    @Test
    void testCountCoiffeurs() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(5);

        assertEquals(5, dao.countCoiffeurs());
    }

    @Test
    void testGetAll() throws Exception {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, true, false); // 2 coiffeurs
        when(rs.getInt("id_coiffeur")).thenReturn(1, 2);
    when(rs.getString("nom")).thenReturn("Ali","Sara");

    assertEquals(2, dao.getAll().size());
}

@Test
void testUpdateCoiffeur() throws Exception {
    when(conn.prepareStatement(anyString())).thenReturn(ps);
    when(ps.executeUpdate()).thenReturn(1);

    Coiffeur c = new Coiffeur();
    c.setIdCoiffeur(1);
    c.setNom("Updated");
    c.setAdresse("New Address");
    c.setEmail("new@mail.com");
    c.setTelephone("060000");

    assertTrue(dao.update(c));
}

@Test
void testGetAllEmpty() throws Exception {
    when(conn.prepareStatement(anyString())).thenReturn(ps);
    when(ps.executeQuery()).thenReturn(rs);
    when(rs.next()).thenReturn(false); // aucune ligne dans ResultSet

    assertEquals(0, dao.getAll().size());
}

@Test
void testGetCoiffeurByIdNotFound() throws Exception {
    when(conn.prepareStatement(anyString())).thenReturn(ps);
    when(ps.executeQuery()).thenReturn(rs);
    when(rs.next()).thenReturn(false); // coiffeur non trouvé

    Coiffeur c = dao.getCoiffeurById(99);
    assertNull(c);
}

@Test
void testAddCoiffeurFails() throws Exception {
    when(conn.prepareStatement(anyString())).thenReturn(ps);
    when(ps.executeUpdate()).thenThrow(new SQLException("DB error"));

    Coiffeur c = new Coiffeur();
    assertFalse(dao.add(c)); // devrait retourner false en cas d'exception
}

@Test
void testDeleteCoiffeurFails() throws Exception {
    when(conn.prepareStatement(anyString())).thenReturn(ps);
    when(ps.executeUpdate()).thenThrow(new SQLException("DB error"));

    assertFalse(dao.delete(1));
}

@Test
void testUpdateCoiffeurFails() throws Exception {
    when(conn.prepareStatement(anyString())).thenReturn(ps);
    when(ps.executeUpdate()).thenThrow(new SQLException("DB error"));

    Coiffeur c = new Coiffeur();
    c.setIdCoiffeur(1);
    assertFalse(dao.update(c));
}


}