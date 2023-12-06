package fr.insalyonif.hubert.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DeliveryTourTest {

    private DeliveryTour deliveryTour;
    private Courier testCourier;
    private ArrayList<DeliveryRequest> testRequests;
    private ArrayList<Chemin> testChemins;
    private List<Chemin> testPaths;
    private Instant testStartTime;
    private Instant testEndTime;
    private Dijkstra testDijkstra;
    private DijkstraInverse testDijkstraInverse;

    @BeforeEach
    void setUp() {
        deliveryTour = new DeliveryTour();
        testCourier = new Courier(1);
        testRequests = new ArrayList<>();
        testChemins = new ArrayList<>();
        testPaths = new ArrayList<>();
        testStartTime = Instant.now();
        testEndTime = testStartTime.plusSeconds(3600); // Ajoute 1 heure
        //testDijkstra = new Dijkstra();
        //testDijkstraInverse = new DijkstraInverse();

        // Setup pour testRequests, testChemins, testPaths si nécessaire
    }

    @Test
    void testSetAndGetCourier() {
        deliveryTour.setCourier(testCourier);
        assertSame(testCourier, deliveryTour.getCourier(), "Le setter/getter pour courier ne fonctionne pas correctement");
    }

    @Test
    void testSetAndGetRequests() {
        deliveryTour.setRequests(testRequests);
        assertEquals(testRequests, deliveryTour.getRequests(), "Le setter/getter pour requests ne fonctionne pas correctement");
    }

    @Test
    void testSetAndGetPaths() {
        deliveryTour.setPaths(testPaths);
        assertEquals(testPaths, deliveryTour.getPaths(), "Le setter/getter pour paths ne fonctionne pas correctement");
    }

    @Test
    void testSetAndGetStartTime() {
        deliveryTour.setStartTime(testStartTime);
        assertEquals(testStartTime, deliveryTour.getStartTime(), "Le setter/getter pour startTime ne fonctionne pas correctement");
    }

    @Test
    void testSetAndGetEndTime() {
        deliveryTour.setEndTime(testEndTime);
        assertEquals(testEndTime, deliveryTour.getEndTime(), "Le setter/getter pour endTime ne fonctionne pas correctement");
    }

    @Test
    void testMajEtClearCheminsDij() {
        deliveryTour.majCheminsDij(testChemins);
        assertEquals(testChemins, deliveryTour.getCheminDij(), "La méthode majCheminsDij ne fonctionne pas correctement");

        deliveryTour.clearCheminsDij();
        assertTrue(deliveryTour.getCheminDij().isEmpty(), "La méthode clearCheminsDij ne fonctionne pas correctement");
    }

    @Test
    void testSetAndGetDijkstra() {
        deliveryTour.setDijkstra(testDijkstra);
        assertSame(testDijkstra, deliveryTour.getDijkstra(), "Le setter/getter pour Dijkstra ne fonctionne pas correctement");
    }

    @Test
    void testSetAndGetDijkstraInverse() {
        deliveryTour.setDijkstraInverse(testDijkstraInverse);
        assertSame(testDijkstraInverse, deliveryTour.getDijkstraInverse(), "Le setter/getter pour DijkstraInverse ne fonctionne pas correctement");
    }
}
