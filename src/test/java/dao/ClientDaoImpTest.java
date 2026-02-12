package dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientDaoImpTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private ClientDaoImp clientDao;

    @BeforeEach
    void setup() throws Exception {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        clientDao = new ClientDaoImp(connection);
    }

    @Test
    void testGetAllClients() throws Exception {
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getInt("id_client")).thenReturn(1);
        when(resultSet.getString("nom")).thenReturn("Ali");
        when(resultSet.getString("prenom")).thenReturn("Ben");
        when(resultSet.getString("email")).thenReturn("ali@test.com");
        when(resultSet.getString("telephone")).thenReturn("0600000000");

        List<Client> clients = clientDao.getAll();

        assertNotNull(clients);
        assertEquals(1, clients.size());

        Client c = clients.get(0);
        assertEquals(1, c.getId());
        assertEquals("Ali", c.getNom());
        assertEquals("Ben", c.getPrenom());
        assertEquals("ali@test.com", c.getEmail());
        assertEquals("0600000000", c.getTelephone());
    }

    @Test
    void testAddClient() throws Exception {
        Client c = new Client();
        c.setNom("Ali");
        c.setPrenom("Ben");
        c.setEmail("ali@test.com");
        c.setTelephone("0600000000");
        c.setMotDePasse("1234");

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = clientDao.add(c);

        assertTrue(result);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testUpdateClient() throws Exception {
        Client c = new Client();
        c.setId(1);
        c.setNom("Ali");
        c.setPrenom("Ben");
        c.setEmail("new@test.com");
        c.setTelephone("0611111111");

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = clientDao.update(c);

        assertTrue(result);
    }

    @Test
    void testDeleteClient() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = clientDao.delete(1);

        assertTrue(result);
    }

    @Test
    void testCountClients() throws Exception {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(5);

        int count = clientDao.countClients();

        assertEquals(5, count);
    }

    @Test
    void testGetClienttByIdFound() throws Exception {
        when(resultSet.next()).thenReturn(true);

        when(resultSet.getInt("id_client")).thenReturn(1);
        when(resultSet.getString("nom")).thenReturn("Ali");
        when(resultSet.getString("prenom")).thenReturn("Ben");
        when(resultSet.getString("email")).thenReturn("ali@test.com");
        when(resultSet.getString("telephone")).thenReturn("0600000000");
        when(resultSet.getString("mot_de_passe")).thenReturn("1234");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Client client = clientDao.getClienttById(1);

        assertNotNull(client);
        assertEquals("Ali", client.getNom());
        assertEquals("Ben", client.getPrenom());
        assertEquals("ali@test.com", client.getEmail());
    }
}