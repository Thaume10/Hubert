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
        // Remplacez 'path/to/testfile.xml' par le chemin de votre fichier XML de test
        cityMap.loadFromXML("src/main/resources/fr/insalyonif/hubert/fichiersXML2022/smallMap.xml");

        // Test pour vérifier si le nombre d'intersections chargées est correct
        int expectedNumberIntersection = 308;
        assertEquals(expectedNumberIntersection, cityMap.getIntersections().size(),"Nombre d'intersections chargées incorrect");

        // Test pour vérifier l'exactitude de l'emplacement du dépôt
        double expectedLng = 4.87572;
        assertEquals(expectedLng, cityMap.getWareHouseLocation().getLongitude(), "WareHouse incorrect");

        // Autres assertions pour tester la validité des segments de route et autres données si nécessaire
    }
}