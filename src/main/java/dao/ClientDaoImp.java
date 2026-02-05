package dao;

import model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du DAO Client
 * Toutes les corrections SonarQube appliquées
 */
public class ClientDaoImp implements ClientDao {

    private static final Logger logger = LoggerFactory.getLogger(ClientDaoImp.class);

    // Constantes pour les noms de colonnes (évite la duplication de littéraux)
    private static final String COLUMN_ID_CLIENT = "id_client";
    private static final String COLUMN_NOM = "nom";
    private static final String COLUMN_PRENOM = "prenom";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_TELEPHONE = "telephone";
    private static final String COLUMN_MOT_DE_PASSE = "mot_de_passe";

    // Requêtes SQL (pas de SELECT *, colonnes explicites)
    private static final String SQL_INSERT = 
        "INSERT INTO client (prenom, nom, email, telephone, mot_de_passe) VALUES (?, ?, ?, ?, ?)";
    
    private static final String SQL_SELECT_BY_EMAIL = 
        "SELECT id_client, nom, prenom, email, telephone, mot_de_passe FROM client WHERE email = ?";
    
    private static final String SQL_SELECT_BY_ID = 
        "SELECT id_client, nom, prenom, email, telephone, mot_de_passe FROM client WHERE id_client = ?";
    
    private static final String SQL_SELECT_ALL = 
        "SELECT id_client, nom, prenom, email, telephone, mot_de_passe FROM client ORDER BY id_client";
    
    private static final String SQL_UPDATE = 
        "UPDATE client SET nom = ?, prenom = ?, email = ?, telephone = ? WHERE id_client = ?";
    
    private static final String SQL_DELETE = 
        "DELETE FROM client WHERE id_client = ?";
    
    private static final String SQL_COUNT = 
        "SELECT COUNT(*) FROM client";

    private final Connection connection;

    /**
     * Constructeur avec connexion (pour les transactions)
     */
    public ClientDaoImp(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        this.connection = connection;
    }

    /**
     * Constructeur par défaut (crée sa propre connexion)
     */
    public ClientDaoImp() {
        this.connection = null;
    }

    /**
     * Ajoute un nouveau client
     */
    public boolean ajouterClient(Client client) {
        validateClient(client);

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {

            setInsertParameters(stmt, client);
            int rowsAffected = stmt.executeUpdate();
            
            logger.info("Client added successfully: {}", client.getEmail());
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.error("Error adding client: {}", client.getEmail(), e);
            throw new DaoException("Failed to add client", e);
        }
    }

    /**
     * Vérifie si un email existe déjà
     */
    public boolean emailExiste(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_EMAIL)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            logger.error("Error checking email existence: {}", email, e);
            throw new DaoException("Failed to check email existence", e);
        }
    }

    /**
     * Récupère un client par son ID
     */
    public Client getClientById(int id) {
        validateId(id);

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToClient(rs);
                }
            }

        } catch (SQLException e) {
            logger.error("Error fetching client with id: {}", id, e);
            throw new DaoException("Failed to retrieve client with id: " + id, e);
        }

        return null;
    }

    /**
     * Récupère tous les clients
     */
    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Client client = mapResultSetToClient(rs);
                clients.add(client);
            }

            logger.info("Retrieved {} clients", clients.size());

        } catch (SQLException e) {
            logger.error("Error fetching all clients", e);
            throw new DaoException("Failed to retrieve clients", e);
        }

        return clients;
    }

    /**
     * Ajoute un client (méthode CRUD standard)
     */
    @Override
    public boolean add(Client client) {
        validateClient(client);

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {

            setInsertParameters(stmt, client);
            int rowsAffected = stmt.executeUpdate();

            logger.info("Client added successfully: {}", client.getEmail());
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.error("Error adding client: {}", client.getEmail(), e);
            throw new DaoException("Failed to add client", e);
        }
    }

    /**
     * Met à jour un client existant
     */
    @Override
    public boolean update(Client client) {
        validateClient(client);
        validateId(client.getId());

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {

            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getTelephone());
            stmt.setInt(5, client.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Client updated successfully: {}", client.getId());
                return true;
            } else {
                logger.warn("No client found with id: {}", client.getId());
                return false;
            }

        } catch (SQLException e) {
            logger.error("Error updating client with id: {}", client.getId(), e);
            throw new DaoException("Failed to update client", e);
        }
    }

    /**
     * Supprime un client par son ID
     */
    @Override
    public boolean delete(int id) {
        validateId(id);

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Client deleted successfully: {}", id);
                return true;
            } else {
                logger.warn("No client found with id: {}", id);
                return false;
            }

        } catch (SQLException e) {
            logger.error("Error deleting client with id: {}", id, e);
            throw new DaoException("Failed to delete client with id: " + id, e);
        }
    }

    /**
     * Compte le nombre total de clients
     */
    public int countClients() {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_COUNT);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int count = rs.getInt(1);
                logger.debug("Total clients: {}", count);
                return count;
            }

        } catch (SQLException e) {
            logger.error("Error counting clients", e);
            throw new DaoException("Failed to count clients", e);
        }

        return 0;
    }

    // ========== MÉTHODES UTILITAIRES PRIVÉES ==========

    /**
     * Récupère la connexion (utilise celle du constructeur ou crée une nouvelle)
     */
    private Connection getConnection() throws SQLException {
        if (this.connection != null) {
            return this.connection;
        }
        return Factory.getConnection();
    }

    /**
     * Mappe un ResultSet vers un objet Client
     * Évite la duplication de code
     */
    private Client mapResultSetToClient(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getInt(COLUMN_ID_CLIENT));
        client.setNom(rs.getString(COLUMN_NOM));
        client.setPrenom(rs.getString(COLUMN_PRENOM));
        client.setEmail(rs.getString(COLUMN_EMAIL));
        client.setTelephone(rs.getString(COLUMN_TELEPHONE));
        client.setMotDePasse(rs.getString(COLUMN_MOT_DE_PASSE));
        return client;
    }

    /**
     * Définit les paramètres pour une insertion
     * Évite la duplication de code
     */
    private void setInsertParameters(PreparedStatement stmt, Client client) throws SQLException {
        stmt.setString(1, client.getPrenom());
        stmt.setString(2, client.getNom());
        stmt.setString(3, client.getEmail());
        stmt.setString(4, client.getTelephone());
        stmt.setString(5, client.getMotDePasse());
    }

    /**
     * Valide un objet Client
     */
    private void validateClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        if (client.getEmail() == null || client.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Client email cannot be null or empty");
        }
    }

    /**
     * Valide un ID
     */
    private void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid client ID: " + id);
        }
    }
}