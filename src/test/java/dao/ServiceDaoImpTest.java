package dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import model.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceDaoImpTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private ServiceDaoImp serviceDao;

    @BeforeEach
    void setup() throws Exception {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        serviceDao = new ServiceDaoImp(connection);
    }

    @Test
    void testGetAllServices() throws Exception {
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getInt("id_service")).thenReturn(1);
        when(resultSet.getString("nom_service")).thenReturn("Coupe");
        when(resultSet.getString("description")).thenReturn("Coupe classique");
        when(resultSet.getInt("duree")).thenReturn(30);
        when(resultSet.getDouble("prix")).thenReturn(50.0);
        when(resultSet.getString("photo")).thenReturn("photo.jpg");

        List<Service> services = serviceDao.getAllServices();

        assertNotNull(services);
        assertEquals(1, services.size());

        Service s = services.get(0);
        assertEquals(1, s.getId());
        assertEquals("Coupe", s.getNom());
        assertEquals("Coupe classique", s.getDescription());
        assertEquals(30, s.getDuree());
        assertEquals(50.0, s.getPrix());
        assertEquals("photo.jpg", s.getPhoto());

        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();
    }
}