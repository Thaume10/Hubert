package fr.insalyonif.hubert.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CheminTest {

    private Chemin cheminTest;
    private Intersection debut;
    private Intersection fin;
    private int[] pi;
    private double cout;

    @BeforeEach
    void setUp() {
        debut = new Intersection(45.4, 8.7, 12345, 1);
        fin = new Intersection(45.9, 8.0, 54321, 2);
        pi = new int[]{1, 2, 3}; // Exemple de chemin
        cout = 10.0;
        cheminTest = new Chemin(debut, fin, pi, cout);
    }

    @Test
    void testConstructorAndGetter() {
        assertSame(debut, cheminTest.getDebut(), "Le début ne correspond pas");
        assertSame(fin, cheminTest.getFin(), "La fin ne correspond pas");
        assertArrayEquals(pi, cheminTest.getPi(), "Le tableau pi ne correspond pas");
        assertEquals(cout, cheminTest.getCout(), "Le coût ne correspond pas");
    }

    @Test
    void testSetterDebut() {
        Intersection nouveauDebut = new Intersection(46.0, 9.0, 23456, 3);
        cheminTest.setDebut(nouveauDebut);
        assertSame(nouveauDebut, cheminTest.getDebut(), "Le setter de début ne fonctionne pas correctement");
    }

    @Test
    void testSetterFin() {
        Intersection nouvelleFin = new Intersection(46.5, 9.5, 65432, 4);
        cheminTest.setFin(nouvelleFin);
        assertSame(nouvelleFin, cheminTest.getFin(), "Le setter de fin ne fonctionne pas correctement");
    }

    @Test
    void testSetterPi() {
        int[] nouveauPi = new int[]{4, 5, 6};
        cheminTest.setPi(nouveauPi);
        assertArrayEquals(nouveauPi, cheminTest.getPi(), "Le setter de pi ne fonctionne pas correctement");
    }

    @Test
    void testSetterCout() {
        double nouveauCout = 20.0;
        cheminTest.setCout(nouveauCout);
        assertEquals(nouveauCout, cheminTest.getCout(), "Le setter de coût ne fonctionne pas correctement");
    }

    @Test
    void testToString() {
        String expectedString = "Chemin{" +
                "debut=" + debut +
                ", fin=" + fin +
                ", pi=" + Arrays.toString(pi) +
                ", cout=" + cout +
                '}';
        assertEquals(expectedString, cheminTest.toString(), "La méthode toString ne fonctionne pas correctement");
    }
}