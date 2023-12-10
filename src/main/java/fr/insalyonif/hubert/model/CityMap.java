package fr.insalyonif.hubert.model;

import java.io.File;
import java.util.List;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Représente une carte de ville avec un ensemble d'intersections et l'emplacement d'un entrepôt.
 * Fournit des méthodes pour gérer les intersections, définir l'emplacement de l'entrepôt,
 * et charger les données à partir d'un fichier XML.
 */
public class CityMap {
    private List<Intersection> intersections;
    private Intersection wareHouseLocation;
    //private List<Chemin> chemins; // List to store Chemin objects

    /**
     * Constructeur par défaut qui initialise la liste des intersections.
     */
    public CityMap() {
        this.intersections = new ArrayList<>();
    }
    
    //   // Getter for chemins
    //   public List<Chemin> getChemins() {
    //     return chemins;
    // }

    //   // Getter for chemins
    //   public void setChemins(List<Chemin>paths ) {
    //     chemins=paths;
    // }

    /**
     * Retourne la liste des intersections de la ville.
     *
     * @return la liste des intersections.
     */
    public List<Intersection> getIntersections() {
        return intersections;
    }

    /**
     * Définit la liste des intersections de la ville.
     *
     * @param intersections la nouvelle liste des intersections.
     */
    public void setIntersections(List<Intersection> intersections) {
        this.intersections = intersections;
    }

    /**
     * Retourne l'emplacement de l'entrepôt.
     *
     * @return l'intersection représentant l'emplacement de l'entrepôt.
     */
    public Intersection getWareHouseLocation() {
        return wareHouseLocation;
    }

    /**
     * Définit l'emplacement de l'entrepôt.
     *
     * @param wareHouseLocation l'intersection représentant le nouvel emplacement de l'entrepôt.
     */
    public void setWareHouseLocation(Intersection wareHouseLocation) {
        this.wareHouseLocation = wareHouseLocation;
    }

    /**
     * Charge les données de la carte à partir d'un fichier XML.
     * Parse le fichier et initialise les intersections et segments de route.
     *
     * @param filename le chemin du fichier XML à charger.
     * @throws Exception si une erreur survient pendant le chargement ou le parsing du fichier.
     */
    public void loadFromXML(String filename) throws Exception {
        // Création d'une instance de File pour le fichier XML
        File xmlFile = new File(filename);

        // Initialisation du constructeur de documents XML
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        // Parsing du document XML
        Document doc = dBuilder.parse(xmlFile);

        // Normalisation du document XML pour éliminer les espaces blancs inutiles
        doc.getDocumentElement().normalize();

        // Utilisation d'une Map pour stocker les intersections avec leur ID comme clé
        Map<Long, Intersection> intersectionMap = new HashMap<>();

        // Traitement des éléments "intersection" du XML
        NodeList intersectionList = doc.getElementsByTagName("intersection");
        for (int i = 0; i < intersectionList.getLength(); i++) {
            Element intersectionElement = (Element) intersectionList.item(i);

            // Extraction des attributs de chaque intersection
            long id = Long.parseLong(intersectionElement.getAttribute("id"));
            double latitude = Double.parseDouble(intersectionElement.getAttribute("latitude"));
            double longitude = Double.parseDouble(intersectionElement.getAttribute("longitude"));

            // Création d'un nouvel objet Intersection et ajout dans la Map et la liste
            Intersection intersection = new Intersection(latitude, longitude, id, i);
            intersectionMap.put(id, intersection);
            this.intersections.add(intersection);
        }

        // Traitement de l'élément "warehouse" pour définir l'emplacement de l'entrepôt
        Element warehouse = (Element) doc.getElementsByTagName("warehouse").item(0);
        long warehouseId = Long.parseLong(warehouse.getAttribute("address"));
        this.wareHouseLocation = intersectionMap.get(warehouseId);

        // Traitement des éléments "segment" du XML
        NodeList segmentList = doc.getElementsByTagName("segment");
        for (int i = 0; i < segmentList.getLength(); i++) {
            Element segmentElement = (Element) segmentList.item(i);

            // Extraction des attributs de chaque segment
            long originId = Long.parseLong(segmentElement.getAttribute("origin"));
            long destinationId = Long.parseLong(segmentElement.getAttribute("destination"));
            String name = segmentElement.getAttribute("name");
            double length = Double.parseDouble(segmentElement.getAttribute("length"));

            // Récupération des intersections d'origine et de destination
            Intersection origin = intersectionMap.get(originId);
            Intersection destination = intersectionMap.get(destinationId);

            // Création d'un nouveau RoadSegment et mise à jour des listes de successeurs et de prédécesseurs
            RoadSegment segment = new RoadSegment(origin, destination, name, length);
            origin.getSuccessors().add(segment);
            destination.getPredecessors().add(segment);
        }
    }

    /**
     * Trouve une intersection basée sur sa position.
     *
     * @param pos la position de l'intersection à trouver
     * @return l'intersection correspondante, ou null si elle n'est pas trouvée
     */
    public Intersection findIntersectionByPos(int pos) {
        // Parcourt toutes les intersections
        for (Intersection intersection : this.intersections) {
            if (intersection.getPos() == pos) {
                // Retourne l'intersection si la position correspond
                return intersection;
            }
        }
        // Retourne null si aucune intersection correspondante n'est trouvée
        return null;
    }
}
