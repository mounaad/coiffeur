package dao;

import model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ClientDaoImpTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @Mock
    private Factory mockFactory;

    private ClientDaoImp clientDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientDao = new ClientDaoImp(mockConnection);
    }

    // ========== Tests pour getClientById() ==========

    @Test
    void testGetClientById_Success() throws SQLException {
        // Arrange
        int testId = 1;

        try (MockedStatic<Factory> mockedFactory = mockStatic(Factory.class)) {
            mockedFactory.when(Factory::getConnection).thenReturn(mockConnection);

            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getString("nom")).thenReturn("Dupont");
            when(mockResultSet.getString("prenom")).thenReturn("Jean");
            when(mockResultSet.getString("email")).thenReturn("jean.dupont@example.com");
            when(mockResultSet.getString("telephone")).thenReturn("0612345678");
            when(mockResultSet.getString("mot_de_passe")).thenReturn("password123");

            // Act
            Client result = clientDao.getClientById(testId);

            // Assert
            assertNotNull(result);
            assertEquals("Dupont", result.getNom());
            assertEquals("Jean", result.getPrenom());
            assertEquals("jean.dupont@example.com", result.getEmail());

            verify(mockStatement).setInt(1, testId);
            verify(mockStatement).executeQuery();
        }
    }

    @Test
    void testGetClientById_NotFound() throws SQLException {
        // Arrange
        try (MockedStatic<Factory> mockedFactory = mockStatic(Factory.class)) {
            mockedFactory.when(Factory::getConnection).thenReturn(mockConnection);

            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false);

            // Act
            Client result = clientDao.getClientById(999);

            // Assert
            assertNull(result);
        }
    }

    // ========== Tests pour getClienttById() (avec double 't') ==========

    @Test
    void testGetClienttById_Success() throws SQLException {
        // Arrange
        int testId = 1;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id_client")).thenReturn(testId);
        when(mockResultSet.getString("nom")).thenReturn("Dupont");
        when(mockResultSet.getString("prenom")).thenReturn("Jean");
        when(mockResultSet.getString("email")).thenReturn("jean.dupont@example.com");
        when(mockResultSet.getString("telephone")).thenReturn("0612345678");
        when(mockResultSet.getString("mot_de_passe")).thenReturn("password123");

        // Act
        Client result = clientDao.getClienttById(testId);

        // Assert
        assertNotNull(result);
        assertEquals(testId, result.getId());
        assertEquals("Dupont", result.getNom());
        assertEquals("Jean", result.getPrenom());

        verify(mockStatement).setInt(1, testId);
        verify(mockStatement).executeQuery();
    }

    @Test
    void testGetClienttById_NotFound() throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        // Act
        Client result = clientDao.getClienttById(999);

        // Assert
        assertNull(result);
    }

    // ========== Tests pour add() ==========

    @Test
    void testAdd_Success() throws SQLException {
        // Arrange
        Client client = new Client();
        client.setNom("Dupont");
        client.setPrenom("Jean");
        client.setEmail("jean.dupont@example.com");
        client.setTelephone("0612345678");
        client.setMotDePasse("password123");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);

        // Act
        boolean result = clientDao.add(client);

        // Assert
        assertTrue(result);

        // Vérifier l'ordre : nom, prenom, email, telephone, mot_de_passe
        verify(mockStatement).setString(1, "Dupont");
        verify(mockStatement).setString(2, "Jean");
        verify(mockStatement).setString(3, "jean.dupont@example.com");
        verify(mockStatement).setString(4, "0612345678");
        verify(mockStatement).setString(5, "password123");
        verify(mockStatement).executeUpdate();
    }

    @Test
    void testAdd_Failure() throws SQLException {
        // Arrange
        Client client = new Client();
        client.setNom("Dupont");
        client.setPrenom("Jean");
        client.setEmail("jean.dupont@example.com");
        client.setTelephone("0612345678");
        client.setMotDePasse("password123");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(0);

        // Act
        boolean result = clientDao.add(client);

        // Assert
        assertFalse(result);
    }

    // ========== Tests pour ajouterClient() ==========

    @Test
    void testAjouterClient_Success() throws SQLException {
        // Arrange
        Client client = new Client();
        client.setPrenom("Jean");
        client.setNom("Dupont");
        client.setEmail("jean.dupont@example.com");
        client.setTelephone("0612345678");
        client.setMotDePasse("password123");

        try (MockedStatic<Factory> mockedFactory = mockStatic(Factory.class)) {
            Factory factoryInstance = mock(Factory.class);
            mockedFactory.when(Factory::getInstance).thenReturn(factoryInstance);
            when(factoryInstance.getConnection()).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockStatement.executeUpdate()).thenReturn(1);

            // Act
            boolean result = clientDao.ajouterClient(client);

            // Assert
            assertTrue(result);

            // Vérifier l'ordre : prenom, nom, email, telephone, mot_de_passe
            verify(mockStatement).setString(1, "Jean");
            verify(mockStatement).setString(2, "Dupont");
            verify(mockStatement).setString(3, "jean.dupont@example.com");
            verify(mockStatement).setString(4, "0612345678");
            verify(mockStatement).setString(5, "password123");
            verify(mockStatement).executeUpdate();
        }
    }

    @Test
    void testAjouterClient_Failure() throws SQLException {
        // Arrange
        Client client = new Client();
        client.setPrenom("Jean");
        client.setNom("Dupont");
        client.setEmail("jean.dupont@example.com");
        client.setTelephone("0612345678");
        client.setMotDePasse("password123");

        try (MockedStatic<Factory> mockedFactory = mockStatic(Factory.class)) {
            Factory factoryInstance = mock(Factory.class);
            mockedFactory.when(Factory::getInstance).thenReturn(factoryInstance);
            when(factoryInstance.getConnection()).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockStatement.executeUpdate()).thenReturn(0);

            // Act
            boolean result = clientDao.ajouterClient(client);

            // Assert
            assertFalse(result);
        }
    }

    // ========== Tests pour update() ==========

    @Test
    void testUpdate_Success() throws SQLException {
        // Arrange
        Client client = new Client();
        client.setId(1);
        client.setNom("Dupont");
        client.setPrenom("Jean");
        client.setEmail("jean.dupont@example.com");
        client.setTelephone("0612345678");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);

        // Act
        boolean result = clientDao.update(client);

        // Assert
        assertTrue(result);
        verify(mockStatement).setString(1, "Dupont");
        verify(mockStatement).setString(2, "Jean");
        verify(mockStatement).setString(3, "jean.dupont@example.com");
        verify(mockStatement).setString(4, "0612345678");
        verify(mockStatement).setInt(5, 1);
        verify(mockStatement).executeUpdate();
    }

    @Test
    void testUpdate_NotFound() throws SQLException {
        // Arrange
        Client client = new Client();
        client.setId(999);
        client.setNom("Dupont");
        client.setPrenom("Jean");
        client.setEmail("jean.dupont@example.com");
        client.setTelephone("0612345678");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(0);

        // Act
        boolean result = clientDao.update(client);

        // Assert
        assertFalse(result);
    }

    // ========== Tests pour delete() ==========

    @Test
    void testDelete_Success() throws SQLException {
        // Arrange
        int testId = 1;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);

        // Act
        boolean result = clientDao.delete(testId);

        // Assert
        assertTrue(result);
        verify(mockStatement).setInt(1, testId);
        verify(mockStatement).executeUpdate();
    }

    @Test
    void testDelete_NotFound() throws SQLException {
        // Arrange
        int testId = 999;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(0);

        // Act
        boolean result = clientDao.delete(testId);

        // Assert
        assertFalse(result);
    }

    // ========== Tests pour getAll() ==========

    @Test
    void testGetAll_Success() throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        // Simuler 2 clients
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id_client")).thenReturn(1, 2);
        when(mockResultSet.getString("nom")).thenReturn("Dupont", "Martin");
        when(mockResultSet.getString("prenom")).thenReturn("Jean", "Marie");
        when(mockResultSet.getString("email")).thenReturn("jean@test.com", "marie@test.com");
        when(mockResultSet.getString("telephone")).thenReturn("0612345678", "0698765432");

        // Act
        List<Client> result = clientDao.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Dupont", result.get(0).getNom());
        assertEquals("Martin", result.get(1).getNom());
    }

    @Test
    void testGetAll_Empty() throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        // Act
        List<Client> result = clientDao.getAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ========== Tests pour countClients() ==========

    @Test
    void testCountClients_Success() throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(5);

        // Act
        int result = clientDao.countClients();

        // Assert
        assertEquals(5, result);
    }

    @Test
    void testCountClients_Zero() throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(0);

        // Act
        int result = clientDao.countClients();

        // Assert
        assertEquals(0, result);
    }

    // ========== Tests pour emailExiste() ==========

    @Test
    void testEmailExiste_True() throws SQLException {
        // Arrange
        String email = "test@example.com";

        try (MockedStatic<Factory> mockedFactory = mockStatic(Factory.class)) {
            mockedFactory.when(Factory::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);

            // Act
            boolean result = clientDao.emailExiste(email);

            // Assert
            assertTrue(result);
            verify(mockStatement).setString(1, email);
        }
    }

    @Test
    void testEmailExiste_False() throws SQLException {
        // Arrange
        String email = "nonexistent@example.com";

        try (MockedStatic<Factory> mockedFactory = mockStatic(Factory.class)) {
            mockedFactory.when(Factory::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false);

            // Act
            boolean result = clientDao.emailExiste(email);

            // Assert
            assertFalse(result);
        }
    }

    // ========== Test Constructeur ==========

    @Test
    void testConstructorWithoutConnection() {
        // Test du constructeur par défaut
        ClientDaoImp dao = new ClientDaoImp();
        assertNotNull(dao);
    }

    @Test
    void testConstructorWithConnection() {
        // Test du constructeur avec connexion
        ClientDaoImp dao = new ClientDaoImp(mockConnection);
        assertNotNull(dao);
    }
}