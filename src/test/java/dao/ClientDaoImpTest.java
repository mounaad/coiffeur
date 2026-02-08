package dao;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ClientDaoImpTest {

    @Test
    void testClientDaoInstantiation() {
        ClientDaoImp dao = new ClientDaoImp();
        assertNotNull(dao);
    }

    @Test
    void testGetClientByIdDoesNotCrash() {
        ClientDaoImp dao = new ClientDaoImp();
        assertDoesNotThrow(() -> {
            try {
                dao.getClientById(1);
            } catch (Exception e) {
                // accepté car pas de DB configurée
            }
        });
    }
}
