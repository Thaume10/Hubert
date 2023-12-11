package fr.insalyonif.hubert.controller;

import fr.insalyonif.hubert.model.Intersection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class ControllerTest {

    @Test
    void testTrouverIntersectionPlusProche() {
        List<Intersection> intersections = new ArrayList<>();
        intersections.add(new Intersection(45.4, 8.7, 12345, 1));
        intersections.add(new Intersection(45.5, 8.8, 54321, 2));
        intersections.add(new Intersection(45.6, 8.9, 67890, 3));

        double lat = 45.48;
        double lng = 8.78;

        Intersection expected = intersections.get(1);
        Intersection result = Controller.trouverIntersectionPlusProche(lat, lng, intersections);

        assertSame(expected, result, "La méthode trouverIntersectionPlusProche ne retourne pas l'intersection la plus proche attendue");
    }

    @Test
    void testDistance() {
        double lat1 = 45.4;
        double lng1 = 8.7;
        double lat2 = 45.5;
        double lng2 = 8.8;

        double expectedDistance = 13.582840414455886;
        double resultDistance = Controller.distance(lat1, lng1, lat2, lng2);

        assertEquals(expectedDistance, resultDistance, 0.01, "La méthode distance ne calcule pas correctement la distance");
    }
}
