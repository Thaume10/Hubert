package fr.insalyonif.hubert.model;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CityMap {
    private List<Intersection> intersections;
    private Intersection wareHouseLocation;

    public CityMap() {
        this.intersections = new ArrayList<>();
    }

    public CityMap(List<Intersection> intersections, Intersection wareHouseLocation) {
        this.intersections = intersections;
        this.wareHouseLocation = wareHouseLocation;
    }

    public List<Intersection> getIntersections() {
        return intersections;
    }

    public void setIntersections(List<Intersection> intersections) {
        this.intersections = intersections;
    }

    public Intersection getWareHouseLocation() {
        return wareHouseLocation;
    }

    public void setWareHouseLocation(Intersection wareHouseLocation) {
        this.wareHouseLocation = wareHouseLocation;
    }

    public void loadFromXML(InputStream xmlInputStream) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlInputStream);
        
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
}
