package fr.insalyonif.hubert.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class DeliveryRequestTest {

    private DeliveryRequest deliveryRequest;
    private Intersection testDeliveryLocation;
    private TimeWindow testTimeWindow;

    @BeforeEach
    void setUp() {
        testDeliveryLocation = new Intersection(45.4, 8.7, 12345, 1);
        testTimeWindow = new TimeWindow(8, 9);
        deliveryRequest = new DeliveryRequest(testDeliveryLocation, testTimeWindow);
    }

    @Test
    void testConstructorWithTimeWindow() {
        assertSame(testDeliveryLocation, deliveryRequest.getDeliveryLocation(), "Le constructeur avec TimeWindow ne définit pas correctement le lieu de livraison");
        assertSame(testTimeWindow, deliveryRequest.getTimeWindow(), "Le constructeur avec TimeWindow ne définit pas correctement la fenêtre temporelle");
    }

    @Test
    void testConstructorWithoutTimeWindow() {
        DeliveryRequest deliveryRequestWithoutTimeWindow = new DeliveryRequest(testDeliveryLocation);
        assertSame(testDeliveryLocation, deliveryRequestWithoutTimeWindow.getDeliveryLocation(), "Le constructeur sans TimeWindow ne définit pas correctement le lieu de livraison");
        assertNotNull(deliveryRequestWithoutTimeWindow.getTimeWindow(), "Le constructeur sans TimeWindow ne crée pas de fenêtre temporelle par défaut");
    }

    @Test
    void testSetAndGetDeliveryLocation() {
        Intersection newLocation = new Intersection(46.0, 9.0, 23456, 3);
        deliveryRequest.setDeliveryLocation(newLocation);
        assertSame(newLocation, deliveryRequest.getDeliveryLocation(), "Le setter/getter pour le lieu de livraison ne fonctionne pas correctement");
    }

    @Test
    void testSetAndGetTimeWindow() {
        TimeWindow newTimeWindow = new TimeWindow(8, 9);
        deliveryRequest.setTimeWindow(newTimeWindow);
        assertSame(newTimeWindow, deliveryRequest.getTimeWindow(), "Le setter/getter pour la fenêtre temporelle ne fonctionne pas correctement");
    }

    @Test
    void testSetAndGetDeliveryTime() {
        Instant newDeliveryTime = Instant.parse("2023-11-27T09:00:00Z");
        deliveryRequest.setDeliveryTime(newDeliveryTime);
        assertEquals(newDeliveryTime, deliveryRequest.getDeliveryTime(), "Le setter/getter pour l'heure de livraison ne fonctionne pas correctement");
    }

    @Test
    void testToString() {
        String expectedString = "Delivery at intersection: " + testDeliveryLocation.getId() +
                "\nScheduled for: Non spécifié" +
                "\n" + testTimeWindow.toString();
        assertEquals(expectedString, deliveryRequest.toString(), "La méthode toString ne retourne pas la chaîne attendue");
    }
}
