package fr.insalyonif.hubert.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourierTest {

    private Courier courier;
    private final int testId = 123;

    @BeforeEach
    void setUp() {
        courier = new Courier(testId);
    }

    @Test
    void testConstructorAndGetter() {
        assertEquals(testId, courier.getId(), "Le constructeur ou le getter ne fonctionne pas correctement");
    }

    @Test
    void testToString() {
        String expectedString = "Courier " + testId;
        assertEquals(expectedString, courier.toString(), "La méthode toString ne retourne pas la chaîne attendue");
    }
}