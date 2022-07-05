package tests;

import static org.junit.Assert.*;

import ihm.Arc;
import ihm.GrapheImporter;
import ihm.IPCC;
import org.junit.Test;
import pcc.IGraph;
import pcc.algorithme.PCCBellman;
import pcc.algorithme.PCCDijkstra;

import java.io.IOException;

public class GrapheImporterTest {

    //nous avons retire le "-10-9.txt" du tableau cf.rapport
    private final String[] fileAC = {"-10-1.txt", "-10-2.txt", "-10-3.txt", "-10-4.txt","-10-5.txt",
                               "-10-6.txt", "-10-7.txt", "-10-8.txt", "-10-10.txt",
                               "-100-1.txt", "-100-2.txt", "-100-3.txt", "-100-4.txt", "-100-5.txt"};
    @Test
    public void testDijkstra() throws NumberFormatException, IOException {
        for (String exo : fileAC) {
            Arc a = new Arc();
            String file = "graphes\\ac\\g"+exo;
            IGraph g = GrapheImporter.importer(file, a);
            IPCC pcc = new PCCDijkstra(g, Integer.toString(a.getSource()));
            assertTrue(GrapheImporter.comparer(file, "reponses\\ac\\r"+exo, pcc, g, a));
        }
    }

    @Test
    public void testBellman() throws NumberFormatException, IOException {
        for (String exo : fileAC) {
            Arc a = new Arc();
            String file = "graphes\\sc\\g" +exo;
            IGraph g = GrapheImporter.importer(file, a);
            IPCC pcc = new PCCBellman(g, Integer.toString(a.getSource()));
            assertTrue(GrapheImporter.comparer(file, "reponses\\sc\\r" + exo, pcc, g, a));
        }
    }
}