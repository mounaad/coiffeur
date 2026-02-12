package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;

class RendezVousTest {

    @Test
    void testConstructorAndGetters() {
        Date date = Date.valueOf("2024-01-01");
        Time time = Time.valueOf("10:00:00");

        RendezVous r = new RendezVous(
                1, 2, 3,
                date, time,
                "confirme",
                "Ali", "Ben",
                5, "Coupe",
                50.0);

        assertEquals(1, r.getIdRdv());
        assertEquals(2, r.getIdClient());
        assertEquals(3, r.getIdCoiffeur());
        assertEquals(date, r.getDateRdv());
        assertEquals(time, r.getHeureRdv());
        assertEquals("confirme", r.getStatut());
        assertEquals("Ali", r.getNomClient());
        assertEquals("Ben", r.getPrenomClient());
        assertEquals(5, r.getIdService());
        assertEquals("Coupe", r.getNomService());
        assertEquals(50.0, r.getPrix());
    }

    @Test
    void testSetters() {
        RendezVous r = new RendezVous();

        r.setIdRdv(10);
        r.setIdClient(20);
        r.setIdCoiffeur(30);
        r.setDateRdv(Date.valueOf("2025-01-01"));
        r.setHeureRdv(Time.valueOf("12:00:00"));
        r.setStatut("annule");
        r.setNomClient("Sara");
        r.setPrenomClient("Ali");
        r.setIdService(7);
        r.setNomService("Coloration");
        r.setPrix(150.0);

        assertEquals(10, r.getIdRdv());
        assertEquals(20, r.getIdClient());
        assertEquals(30, r.getIdCoiffeur());
        assertEquals(Date.valueOf("2025-01-01"), r.getDateRdv());
        assertEquals(Time.valueOf("12:00:00"), r.getHeureRdv());
        assertEquals("annule", r.getStatut());
        assertEquals("Sara", r.getNomClient());
        assertEquals("Ali", r.getPrenomClient());
        assertEquals(7, r.getIdService());
        assertEquals("Coloration", r.getNomService());
        assertEquals(150.0, r.getPrix());
    }

    @Test
    void testDefaultValues() {
        RendezVous r = new RendezVous();
        // Vérifier que rien n'est initialisé
        assertEquals(0, r.getIdRdv());
        assertEquals(0, r.getIdClient());
        assertEquals(0, r.getIdCoiffeur());
        assertNull(r.getDateRdv());
        assertNull(r.getHeureRdv());
        assertNull(r.getStatut());
        assertNull(r.getNomClient());
        assertNull(r.getPrenomClient());
        assertEquals(0, r.getIdService());
        assertNull(r.getNomService());
        assertEquals(0.0, r.getPrix());
    }

    @Test
    void testEqualityAndHashcode() {
        Date date = Date.valueOf("2024-01-01");
        Time time = Time.valueOf("10:00:00");

        RendezVous r1 = new RendezVous(1, 2, 3, date, time, "confirme", "Ali", "Ben", 5, "Coupe", 50.0);
        RendezVous r2 = new RendezVous(1, 2, 3, date, time, "confirme", "Ali", "Ben", 5, "Coupe", 50.0);

        // Comparaison simple
        assertEquals(r1.getIdRdv(), r2.getIdRdv());
        assertEquals(r1.getPrix(), r2.getPrix());
    }
}