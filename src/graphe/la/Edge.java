package graphe.la;

import java.util.Objects;
/**
 * Brief: Classe GrapheMA Edge, permettant de manipuler
 * des sommets
 * @author Anxian Zhang, Vick Ye, Duong Tran-Nam
 * @version 6
 * @since 20/04/2022
 */
public class Edge {
    private final String destination;
    private final int valuation;

    protected Edge(String dest, int valuation){
        this.destination = dest;
        this.valuation = valuation;
    }

    /**
     * Permet de connaitre si le sommet de destination exite
     * @param dest le sommet de destination
     * @return true s'il exite, sinon false
     */
    protected boolean aSommetDestination(String dest){
        return Objects.equals(this.destination, dest);
    }

    /**
     * Renvoi la valuation jusqu'a la destination
     * @return la valuation
     */
    protected int getValuation() {
        return this.valuation;
    }

    @Override
    public String toString(){
        return this.destination + "(" + this.valuation + ") ";
    }
}