
package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CoiffeurTest {

    @Test
    void testConstructeurEtGetters() {
        Coiffeur c = new Coiffeur(1, "Ali", "Rabat", "ali@mail.com",
                "1234", "0612345678", "actif");

        assertEquals(1, c.getIdCoiffeur());
        assertEquals("Ali", c.getNom());
        assertEquals("Rabat", c.getAdresse());
        assertEquals("ali@mail.com", c.getEmail());
        assertEquals("1234", c.getMotDePasse());
        assertEquals("0612345678", c.getTelephone());
        assertEquals("actif", c.getStatut());
    }

    @Test
    void testSetters() {
        Coiffeur c = new Coiffeur();

        c.setIdCoiffeur(2);
        c.setNom("Sara");
        c.setAdresse("Casablanca");
        c.setEmail("sara@mail.com");
        c.setMotDePasse("pass");
        c.setTelephone("0600000000");
        c.setStatut("inactif");

        assertEquals(2, c.getIdCoiffeur());
        assertEquals("Sara", c.getNom());
        assertEquals("Casablanca", c.getAdresse());
        assertEquals("sara@mail.com", c.getEmail());
        assertEquals("pass", c.getMotDePasse());
        assertEquals("0600000000", c.getTelephone());
        assertEquals("inactif", c.getStatut());
    }
}