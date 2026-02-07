import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dao.ClientDaoImp;
import dao.DaoException;
import model.Client;

public class ClientDaoImpTest {

    private ClientDaoImp clientDao;

    @BeforeEach
    public void setup() {
        // Initialise le DAO avant chaque test
        clientDao = new ClientDaoImp();
    }

    @Test
    public void testGetClientById_ValidId() throws DaoException {
        // Test pour un ID existant
        Client client = clientDao.getClientById(1);
        assertNotNull(client); // vérifie que le client existe
        assertEquals(1, client.getId());
        assertEquals("Mohamed", client.getNom());
    }

    @Test
    public void testGetClientById_AnotherValidId() throws DaoException {
        // Test pour un autre ID existant
        Client client = clientDao.getClientById(2);
        assertNotNull(client);
        assertEquals(2, client.getId());
        assertEquals("Fatima", client.getNom());
    }

    @Test
    public void testGetClientById_InvalidId() {
        // Test pour un ID inexistant → vérifie que DaoException est levée
        assertThrows(DaoException.class, () -> {
            clientDao.getClientById(999);
        });
    }
}
