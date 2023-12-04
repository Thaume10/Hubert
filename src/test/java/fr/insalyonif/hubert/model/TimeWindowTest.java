package fr.insalyonif.hubert.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TimeWindowTest {
    Instant huit;
    Instant neuf;
    Instant dix;
    Instant onze;
    TimeWindow test;

    @BeforeEach
    void setUp() {
        // Initialisation des instants de temps
        huit = Instant.parse("2023-11-27T08:00:00Z");
        neuf = Instant.parse("2023-11-27T09:00:00Z");
        dix = Instant.parse("2023-11-27T10:00:00Z");
        onze = Instant.parse("2023-11-27T11:00:00Z");

        // Initialisation de la fenêtre de temps pour le test
        test = new TimeWindow(neuf, onze); // Supposons que la fenêtre de temps soit de 9h à 11h
    }

    @Test
    void isInTimeWindow() {
        // Test si un instant est dans la fenêtre de temps
        assertTrue(test.isInTimeWindow(dix), "Dix heures devrait être dans la fenêtre de temps");

        // Test si un instant est en dehors de la fenêtre de temps
        assertFalse(test.isInTimeWindow(huit), "Huit heures ne devrait pas être dans la fenêtre de temps");
        assertFalse(test.isInTimeWindow(onze), "Onze heures ne devrait pas être dans la fenêtre de temps");
    }
}
