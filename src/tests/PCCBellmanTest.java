package tests;

import static org.junit.Assert.*;

import exeption.*;
import graphe.la.GrapheLA;
import graphe.ma.GrapheMA;
import ihm.IPCC;
import org.junit.Test;
import pcc.IGraph;
import pcc.algorithme.PCCBellman;

public class PCCBellmanTest {
    @Test
    public void test(){
        String [] s3point1 = {"A","B","C","D","E","F","G","H","I"};
        String [] s3point2 = {"A","B","C","D","E","F","G","H","I","J"};
        String [] s3point6 = {"A","B","C","D","E","F","G"};

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

    public void exo3point1(IGraph g){
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

        IPCC b = new PCCBellman(g, "A");
        assertTrue(b.estOK());

        assertEquals("4\nA D B ", b.pCC("B"));
        assertEquals("2\nA C ", b.pCC("C"));
        assertEquals("1\nA D ", b.pCC("D"));
        assertEquals("4\nA D E ", b.pCC("E"));
        assertEquals("7\nA C H G F ", b.pCC("F"));
        assertEquals("6\nA C H G ", b.pCC("G"));
        assertEquals("4\nA C H ", b.pCC("H"));

        assertThrows(NoPathEx.class, () -> {
            b.pCC("A");
            b.pCC("a");
            b.pCC("R");
            b.pCC("Z");
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

        IPCC b = new PCCBellman(g, "A");
        assertTrue(b.estOK());

        assertEquals("8\nA B ", b.pCC("B"));
        assertEquals("12\nA B C ", b.pCC("C"));
        assertEquals("3\nA D ", b.pCC("D"));
        assertEquals("5\nA D E ", b.pCC("E"));
        assertEquals("10\nA D J F ", b.pCC("F"));
        assertEquals("8\nA D E G ", b.pCC("G"));
        assertEquals("9\nA D E I H ", b.pCC("H"));
        assertEquals("7\nA D E I ", b.pCC("I"));
        assertEquals("4\nA D J ", b.pCC("J"));

        assertThrows(NoPathEx.class, () -> {
            b.pCC("A");
            b.pCC("a");
            b.pCC("R");
            b.pCC("Z");
        });
    }

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

        IPCC b = new PCCBellman(g, "A");
        assertTrue(b.estOK());

        assertEquals("6\nA C B ", b.pCC("B"));
        assertEquals("1\nA C ", b.pCC("C"));
        assertEquals("8\nA C B F D ", b.pCC("D"));
        assertEquals("3\nA C E ", b.pCC("E"));
        assertEquals("3\nA C B F ", b.pCC("F"));
        assertEquals("12\nA C B F D G ", b.pCC("G"));

        assertThrows(NoPathEx.class, () -> {
            b.pCC("A");
            b.pCC("a");
            b.pCC("R");
            b.pCC("Z");
        });
    }

    /**
     * test sur un circuit absorbant
     */
    @Test
    public void exoCircuitAbsorbant(){
        String [] sCircuitAbsorban = {"0","1","2","3"};

        GrapheLA gCircuitAbsorbantLA = new GrapheLA(sCircuitAbsorban);
        GrapheMA gCircuitAbsorbantMA = new GrapheMA(sCircuitAbsorban);

        // test avec GrapheMA
        gCircuitAbsorbantMA.ajouterArc("0", "1", 5);
        gCircuitAbsorbantMA.ajouterArc("0", "2", 4);
        gCircuitAbsorbantMA.ajouterArc("2", "1", -6);
        gCircuitAbsorbantMA.ajouterArc("3", "2", 2);
        gCircuitAbsorbantMA.ajouterArc("1", "3", 3);

        IPCC b = new PCCBellman(gCircuitAbsorbantMA, "0");
        assertThrows(CircuitAbsorbantEx.class, b::estOK);

        // test avec GrapheLA
        gCircuitAbsorbantLA.ajouterArc("0", "1", 5);
        gCircuitAbsorbantLA.ajouterArc("0", "2", 4);
        gCircuitAbsorbantLA.ajouterArc("2", "1", -6);
        gCircuitAbsorbantLA.ajouterArc("3", "2", 2);
        gCircuitAbsorbantLA.ajouterArc("1", "3", 3);

        IPCC b2 = new PCCBellman(gCircuitAbsorbantLA, "0");
        assertThrows(CircuitAbsorbantEx.class, b2::estOK);
    }
}