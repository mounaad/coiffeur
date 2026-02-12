package model;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Test;

class FideliteTest {

    @Test
    void testConstructeurEtGetters() {
        Date date = Date.valueOf("2026-02-11");
        Fidelite f = new Fidelite(
                1, 101, 50, false,
                "rendezvous", date, "Test fidélité");

        assertEquals(1, f.getIdFidelite());
        assertEquals(101, f.getIdClient());
        assertEquals(50, f.getPoints());
        assertFalse(f.isReductionApplicable());
        assertEquals("rendezvous", f.getSource());
        assertEquals(date, f.getDateOperation());
        assertEquals("Test fidélité", f.getDescription());
    }
    @Test
    void testSetters() {
        Fidelite f = new Fidelite();

        Date date = Date.valueOf("2026-02-12");

        f.setIdFidelite(2);
        f.setIdClient(202);
        f.setPoints(80);
        f.setReductionApplicable(true);
        f.setSource("achat");
        f.setDateOperation(date);
        f.setDescription("Ajout points");

        assertEquals(2, f.getIdFidelite());
        assertEquals(202, f.getIdClient());
        assertEquals(80, f.getPoints());
        assertTrue(f.isReductionApplicable());
        assertEquals("achat", f.getSource());
        assertEquals(date, f.getDateOperation());
        assertEquals("Ajout points", f.getDescription());
    }
}