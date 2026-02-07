package dao;

/**
 * Exception personnalisée pour les erreurs DAO
 * Remplace les printStackTrace() et améliore la gestion des erreurs
 */
public class DaoException extends RuntimeException {

    /**
     * Constructeur avec message uniquement
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Constructeur avec message et cause
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructeur avec cause uniquement
     */
    public DaoException(Throwable cause) {
        super(cause);
    }
}