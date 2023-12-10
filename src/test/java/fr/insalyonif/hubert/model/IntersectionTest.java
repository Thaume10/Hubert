package fr.insalyonif.hubert.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class IntersectionTest {

    private Intersection intersection;
    private final double latitude = 45.4;
    private final double longitude = 8.7;
    private final long id = 12345;
    private final int pos = 1;

    @BeforeEach
    void setUp() {
        intersection = new Intersection(latitude, longitude, id, pos);
    }

    @Test
    void testConstructor() {
        assertEquals(latitude, intersection.getLatitude(), "Le constructeur ne définit pas correctement la latitude");
        assertEquals(longitude, intersection.getLongitude(), "Le constructeur ne définit pas correctement la longitude");
        assertEquals(id, intersection.getId(), "Le constructeur ne définit pas correctement l'ID");
        assertEquals(pos, intersection.getPos(), "Le constructeur ne définit pas correctement la position");
        assertTrue(intersection.getPredecessors().isEmpty(), "La liste des prédécesseurs doit être vide");
        assertTrue(intersection.getSuccessors().isEmpty(), "La liste des successeurs doit être vide");
    }

    @Test
    void testSetAndGetLatitude() {
        double newLatitude = 46.0;
        intersection.setLatitude(newLatitude);
        assertEquals(newLatitude, intersection.getLatitude(), "Le setter/getter pour la latitude ne fonctionne pas correctement");
    }

    @Test
    void testSetAndGetLongitude() {
        double newLongitude = 9.0;
        intersection.setLongitude(newLongitude);
        assertEquals(newLongitude, intersection.getLongitude(), "Le setter/getter pour la longitude ne fonctionne pas correctement");
    }

    @Test
    void testSetAndGetId() {
        long newId = 54321;
        intersection.setId(newId);
        assertEquals(newId, intersection.getId(), "Le setter/getter pour l'ID ne fonctionne pas correctement");
    }

    @Test
    void testSetAndGetPos() {
        int newPos = 2;
        intersection.setPos(newPos);
        assertEquals(newPos, intersection.getPos(), "Le setter/getter pour la position ne fonctionne pas correctement");
    }

    @Test
    void testSetAndGetPredecessors() {
        ArrayList<RoadSegment> newPredecessors = new ArrayList<>();
        intersection.setPredecessors(newPredecessors);
        assertSame(newPredecessors, intersection.getPredecessors(), "Le setter/getter pour les prédécesseurs ne fonctionne pas correctement");
    }

    @Test
    void testSetAndGetSuccessors() {
        ArrayList<RoadSegment> newSuccessors = new ArrayList<>();
        intersection.setSuccessors(newSuccessors);
        assertSame(newSuccessors, intersection.getSuccessors(), "Le setter/getter pour les successeurs ne fonctionne pas correctement");
    }

    @Test
    void testToString() {
        String expectedString = "Intersection{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", id=" + id +
                ", pos=" + pos +
                '}';
        assertEquals(expectedString, intersection.toString(), "La méthode toString ne retourne pas la chaîne attendue");
    }

    @Test
    void testCompareTo() {
        Intersection otherIntersection = new Intersection(46.0, 9.0, 54321, 2);
        assertTrue(intersection.compareTo(otherIntersection) < 0, "La méthode compareTo ne fonctionne pas correctement");
    }
}
