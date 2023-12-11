package fr.insalyonif.hubert.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TimeWindowTest {

    private TimeWindow timeWindow;
    private int startTime;
    private int endTime;

    @BeforeEach
    void setUp() {
        startTime = 8;  // 08:00 in 24-hour format
        endTime = 10;   // 10:00 in 24-hour format
        timeWindow = new TimeWindow(startTime, endTime);
    }

    @Test
    void testConstructor() {
        assertEquals(startTime, timeWindow.getStartTime(), "Le constructeur ne définit pas correctement l'heure de début");
        assertEquals(endTime, timeWindow.getEndTime(), "Le constructeur ne définit pas correctement l'heure de fin");
    }

    @Test
    void testSetAndGetStartTime() {
        int newStartTime = 9; // 09:00 in 24-hour format
        timeWindow.setStartTime(newStartTime);
        assertEquals(newStartTime, timeWindow.getStartTime(), "Le setter/getter pour l'heure de début ne fonctionne pas correctement");
    }

    @Test
    void testSetAndGetEndTime() {
        int newEndTime = 11; // 11:00 in 24-hour format
        timeWindow.setEndTime(newEndTime);
        assertEquals(newEndTime, timeWindow.getEndTime(), "Le setter/getter pour l'heure de fin ne fonctionne pas correctement");
    }

    @Test
    void testIsInTimeWindow() {
        assertTrue(timeWindow.isInTimeWindow(9), "La méthode isInTimeWindow ne fonctionne pas correctement pour un temps dans la fenêtre");
        assertFalse(timeWindow.isInTimeWindow(7), "La méthode isInTimeWindow ne fonctionne pas correctement pour un temps avant la fenêtre");
        assertFalse(timeWindow.isInTimeWindow(10), "La méthode isInTimeWindow ne fonctionne pas correctement pour un temps à la fin de la fenêtre");
    }

    @Test
    void testToString() {
        String expectedString = "Passage entre " + startTime + "h et " + endTime + "h";
        assertEquals(expectedString, timeWindow.toString(), "La méthode toString ne retourne pas la chaîne attendue");
    }
}
