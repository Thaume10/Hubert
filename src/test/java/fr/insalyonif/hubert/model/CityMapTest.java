package fr.insalyonif.hubert.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityMapTest {

    private CityMap cityMap;

    @BeforeEach
    void setUp() {
        cityMap = new CityMap();
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