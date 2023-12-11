package fr.insalyonif.hubert.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RoadSegmentTest {

    private RoadSegment roadSegment;
    private Intersection origin;
    private Intersection destination;
    private String name;
    private double length;

    @BeforeEach
    void setUp() {
        origin = new Intersection(45.4, 8.7, 12345, 1);
        destination = new Intersection(45.9, 8.0, 54321, 2);
        name = "Route 66";
        length = 10.0;
        roadSegment = new RoadSegment(origin, destination, name, length);
    }

    @Test
    void testConstructor() {
        assertSame(origin, roadSegment.getOrigin(), "Le constructeur ne définit pas correctement l'origine");
        assertSame(destination, roadSegment.getDestination(), "Le constructeur ne définit pas correctement la destination");
        assertEquals(name, roadSegment.getName(), "Le constructeur ne définit pas correctement le nom");
        assertEquals(length, roadSegment.getLength(), "Le constructeur ne définit pas correctement la longueur");
    }

    @Test
    void testSetAndGetOrigin() {
        Intersection newOrigin = new Intersection(46.0, 9.0, 23456, 3);
        roadSegment.setOrigin(newOrigin);
        assertSame(newOrigin, roadSegment.getOrigin(), "Le setter/getter pour l'origine ne fonctionne pas correctement");
    }

    @Test
    void testSetAndGetDestination() {
        Intersection newDestination = new Intersection(47.0, 10.0, 65432, 4);
        roadSegment.setDestination(newDestination);
        assertSame(newDestination, roadSegment.getDestination(), "Le setter/getter pour la destination ne fonctionne pas correctement");
    }

    @Test
    void testSetAndGetName() {
        String newName = "Route 67";
        roadSegment.setName(newName);
        assertEquals(newName, roadSegment.getName(), "Le setter/getter pour le nom ne fonctionne pas correctement");
    }

    @Test
    void testSetAndGetLength() {
        double newLength = 15.0;
        roadSegment.setLength(newLength);
        assertEquals(newLength, roadSegment.getLength(), "Le setter/getter pour la longueur ne fonctionne pas correctement");
    }

    @Test
    void testToString() {
        String expectedString = "RoadSegment{" +
                "origin=" + origin +
                ", destination=" + destination +
                ", name='" + name + '\'' +
                ", length=" + length +
                '}';
        assertEquals(expectedString, roadSegment.toString(), "La méthode toString ne retourne pas la chaîne attendue");
    }
}
