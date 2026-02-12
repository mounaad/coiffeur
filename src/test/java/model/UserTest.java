package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void testConstructeurVideEtSetters() {
        User user = new User();

        user.setId(1);
        user.setNom("Admin");
        user.setRole("admin");

        assertEquals(1, user.getId());
        assertEquals("Admin", user.getNom());
        assertEquals("admin", user.getRole());
    }

    @Test
    void testConstructeurAvecParametres() {
        User user = new User(2, "Coiffeur", "coiffeur");

        assertEquals(2, user.getId());
        assertEquals("Coiffeur", user.getNom());
        assertEquals("coiffeur", user.getRole());
    }
}