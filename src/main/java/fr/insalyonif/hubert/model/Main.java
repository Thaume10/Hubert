package fr.insalyonif.hubert.model;

// Classe Main
public class Main {
    public static void main(String[] args) throws Exception {
        // Création d'intersections et de segments de route ici...

        // Créez et initialisez votre CityMap
        CityMap cityMap = new CityMap();
        cityMap.loadFromXML("src/main/resources/fr/insalyonif/hubert/fichiersXML2022/smallMap.xml");

        // Obtenez la taille du graphe à partir de la CityMap
        int sizeGraph = cityMap.getIntersections().size();

        // Créez une instance de Dijkstra et exécutez l'algorithme
        Dijkstra dijkstra = new Dijkstra(sizeGraph, cityMap);
        //dijkstra.addDeliveryRequest(cityMap.getWareHouseLocation());
        dijkstra.runDijkstra(cityMap.findIntersectionByPos(7), sizeGraph);

        // Affichez les résultats, par exemple, les chemins calculés
        //for (Chemin chemin : dijkstra.getChemins()) {
            //System.out.println(chemin);
        //}
        //System.out.println("fin");
        // Créez une instance de DijkstraInverse et exécutez l'algorithme
        DijkstraInverse dijkstraInverse = new DijkstraInverse(sizeGraph, cityMap);
        dijkstraInverse.runDijkstra(cityMap.findIntersectionByPos(7), sizeGraph);
        //System.out.println("encore fin");
        // Affichez les résultats, par exemple, les chemins inverses calculés
        for (Chemin chemin : dijkstraInverse.getChemins()) {
            System.out.println(chemin);
        }
    }


}
