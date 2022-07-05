package tests;

import exeption.ArcNegatifEx;
import exeption.NoPathEx;
import graphe.la.GrapheLA;
import graphe.ma.GrapheMA;
import ihm.IPCC;
import org.junit.Test;
import pcc.IGraph;
import pcc.algorithme.PCCDijkstra;

import static org.junit.Assert.*;

public class PCCDijkstraTest {
    @Test
    public void test() {
        String[] s3point1 = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
        String[] s3point2 = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        String[] s3point6 = {"A", "B", "C", "D", "E", "F", "G"};

        GrapheLA g1LA = new GrapheLA(s3point1);
        GrapheMA g1MA = new GrapheMA(s3point1);
        exo3point1(g1LA);
        exo3point1(g1MA);

        GrapheLA g2LA = new GrapheLA(s3point2);
        GrapheMA g2MA = new GrapheMA(s3point2);
        exo3point2(g2LA);
        exo3point2(g2MA);

        GrapheLA g3LA = new GrapheLA(s3point6);
        GrapheMA g3MA = new GrapheMA(s3point6);
        exo3point6(g3LA);
        exo3point6(g3MA);
    }

    public void exo3point1(IGraph g) {
        g.ajouterArc("A", "D", 1);
        g.ajouterArc("A", "C", 2);
        g.ajouterArc("D", "C", 5);
        g.ajouterArc("D", "E", 3);
        g.ajouterArc("D", "B", 3);
        g.ajouterArc("C", "H", 2);
        g.ajouterArc("E", "C", 1);
        g.ajouterArc("E", "H", 7);
        g.ajouterArc("E", "G", 3);
        g.ajouterArc("B", "G", 3);
        g.ajouterArc("G", "B", 2);
        g.ajouterArc("G", "F", 1);
        g.ajouterArc("H", "G", 2);
        g.ajouterArc("H", "F", 4);
        g.ajouterArc("I", "H", 10);

        IPCC d = new PCCDijkstra(g, "A");
        assertTrue(d.estOK());

        assertEquals("4\nA D B ", d.pCC("B"));
        assertEquals("2\nA C ", d.pCC("C"));
        assertEquals("1\nA D ", d.pCC("D"));
        assertEquals("4\nA D E ", d.pCC("E"));
        assertEquals("7\nA C H G F ", d.pCC("F"));
        assertEquals("6\nA C H G ", d.pCC("G"));
        assertEquals("4\nA C H ", d.pCC("H"));

        assertThrows(NoPathEx.class, () -> {
            d.pCC("A");
            d.pCC("a");
            d.pCC("R");
            d.pCC("Z");
        });
    }

    public void exo3point2(IGraph g) {
        g.ajouterArc("A", "D", 3);
        g.ajouterArc("A", "B", 8);
        g.ajouterArc("D", "J", 1);
        g.ajouterArc("D", "E", 2);
        g.ajouterArc("B", "E", 5);
        g.ajouterArc("B", "C", 4);
        g.ajouterArc("C", "I", 5);
        g.ajouterArc("C", "F", 1);
        g.ajouterArc("E", "G", 3);
        g.ajouterArc("E", "I", 2);
        g.ajouterArc("F", "H", 5);
        g.ajouterArc("G", "H", 4);
        g.ajouterArc("I", "H", 2);
        g.ajouterArc("J", "G", 6);
        g.ajouterArc("J", "F", 6);

        IPCC d = new PCCDijkstra(g, "A");
        assertTrue(d.estOK());

        assertEquals("8\nA B ", d.pCC("B"));
        assertEquals("12\nA B C ", d.pCC("C"));
        assertEquals("3\nA D ", d.pCC("D"));
        assertEquals("5\nA D E ", d.pCC("E"));
        assertEquals("10\nA D J F ", d.pCC("F"));
        assertEquals("8\nA D E G ", d.pCC("G"));
        assertEquals("9\nA D E I H ", d.pCC("H"));
        assertEquals("7\nA D E I ", d.pCC("I"));
        assertEquals("4\nA D J ", d.pCC("J"));
    }

    /**
     * Test sur un graphe avec valuation nÃ©gative
     */
    public void exo3point6(IGraph g) {
        g.ajouterArc("A", "C", 1);
        g.ajouterArc("A", "B", 7);
        g.ajouterArc("B", "D", 4);
        g.ajouterArc("B", "E", 2);
        g.ajouterArc("B", "F", -3);
        g.ajouterArc("C", "B", 5);
        g.ajouterArc("C", "F", 7);
        g.ajouterArc("C", "E", 2);
        g.ajouterArc("D", "G", 4);
        g.ajouterArc("E", "G", 10);
        g.ajouterArc("F", "D", 5);
        g.ajouterArc("F", "E", 3);

        IPCC d = new PCCDijkstra(g, "A");
        assertThrows(ArcNegatifEx.class, d::estOK);
    }
}