package fr.insalyonif.hubert.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class CityMapTest {

    private CityMap cityMap;
    private List<Intersection> testIntersections;
    private Intersection testWareHouseLocation;

    @BeforeEach
    void setUp() {
        cityMap = new CityMap();
        testIntersections = new ArrayList<>();
        testIntersections.add(new Intersection(45.4, 8.7, 12345, 1));
        testIntersections.add(new Intersection(45.9, 8.0, 54321, 2));
        testWareHouseLocation = new Intersection(46.0, 9.0, 23456, 3);
    }

    @Test
    void testConstructor() {
        assertTrue(cityMap.getIntersections().isEmpty(), "Le constructeur par défaut ne crée pas une liste vide d'intersections");
    }

    @Test
    void testGetAndSetIntersections() {
        cityMap.setIntersections(testIntersections);
        assertEquals(testIntersections, cityMap.getIntersections(), "Le getter/setter pour intersections ne fonctionne pas correctement");
    }

    @Test
    void testGetAndSetWareHouseLocation() {
        cityMap.setWareHouseLocation(testWareHouseLocation);
        assertEquals(testWareHouseLocation, cityMap.getWareHouseLocation(), "Le getter/setter pour wareHouseLocation ne fonctionne pas correctement");
    }

    @Test
    public void testLoadFromXML() throws Exception {
        cityMap.loadFromXML("src/main/resources/fr/insalyonif/hubert/fichiersXML2022/smallMap.xml");

        // Test pour vérifier si le nombre d'intersections chargées est correct
        int expectedNumberIntersection = 308;
        assertEquals(expectedNumberIntersection, cityMap.getIntersections().size(),"Nombre d'intersections chargées incorrect");

        // Test pour vérifier l'exactitude de l'emplacement du dépôt
        double expectedLng = 4.87572;
        assertEquals(expectedLng, cityMap.getWareHouseLocation().getLongitude(), "WareHouse incorrect");
    }

    @Test
    void testFindIntersectionByPos() throws Exception {
        cityMap.loadFromXML("src/main/resources/fr/insalyonif/hubert/fichiersXML2022/smallMap.xml");

        // Test pour vérifier que la fonction findIntersectionByPos est correct
        double expectedLng = 4.8704023;
        assertEquals(expectedLng, cityMap.findIntersectionByPos(2).getLongitude());
    }
}