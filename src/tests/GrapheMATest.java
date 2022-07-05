package tests;

import static org.junit.Assert.*;

import graphe.ma.GrapheMA;
import org.junit.Test;

public class GrapheMATest {
    @Test
    public void testMA(){
        String [] s = {"a", "b", "c", "d", "e"};
        GrapheMA g = new GrapheMA(s);

        assertEquals(5, g.getNbSommets());
        g.ajouterArc("c", "c", 1);
        g.ajouterArc("d", "a", -9);
        g.ajouterArc("c", "a", -8);
        g.ajouterArc("a", "d", -9);
        g.ajouterArc("b", "c", 10);
        g.ajouterArc("a", "a", 8);
        g.ajouterArc("b", "a", -1);
        g.ajouterArc("c", "d", 65);
        //test ne passant pas car l'arc c-d existe deja
        //tla.ajouterArc("c", "d", 67);

        assertTrue(g.aArc("c", "c"));
        assertTrue(g.aArc("d", "a"));
        assertTrue(g.aArc("a", "a"));
        assertTrue(g.aArc("b", "c"));
        assertTrue(g.aArc("a", "d"));
        assertTrue(g.aArc("b", "a"));
        assertTrue(g.aArc("c", "a"));
        assertTrue(g.aArc("c", "d"));

        assertFalse(g.aArc("d", "b"));
        assertFalse(g.aArc("c", "b"));
        assertFalse(g.aArc("a", "b"));
        assertFalse(g.aArc("a", "c"));
        assertFalse(g.aArc("b", "b"));


        assertNotEquals(35, g.dOut("a"));

        assertEquals(0, g.dOut("e"));
        assertEquals(3, g.dOut("c"));
        assertEquals(2, g.dOut("a"));
        assertEquals(1, g.dOut("d"));
        assertEquals(2, g.dOut("b"));

        assertEquals(4, g.dIn("a"));
        assertEquals(0, g.dIn("b"));
        assertEquals(2, g.dIn("c"));
        assertEquals(2, g.dIn("d"));
        assertEquals(0, g.dIn("e"));

        assertNotEquals(5, g.dIn("a"));
        assertNotEquals(785, g.dIn("e"));
        assertNotEquals(17, g.dIn("b"));

        assertEquals(1, g.getValuation("c", "c"));
        assertEquals(-9, g.getValuation("d", "a"));
        assertEquals(-9, g.getValuation("a", "d"));
        assertEquals(8, g.getValuation("a", "a"));
        assertEquals(10, g.getValuation("b", "c"));
        assertEquals(-1, g.getValuation("b", "a"));
        assertEquals(65, g.getValuation("c", "d"));
        assertEquals(-8, g.getValuation("c", "a"));

        assertNotEquals(17, g.getValuation("c", "c"));
        assertNotEquals(13, g.getValuation("c", "d"));
        assertNotEquals(-96, g.getValuation("c", "a"));

        assertEquals("8 0 0 -9 0 \n" +
                "-1 0 10 0 0 \n" +
                "-8 0 1 65 0 \n" +
                "-9 0 0 0 0 \n" +
                "0 0 0 0 0 \n", g.toString());
    }
}
