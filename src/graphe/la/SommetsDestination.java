package graphe.la;

import java.util.ArrayList;
/**
 * Brief: Classe SommetsDestination, permettant de manipuler
 * des sommets de destinations
 * @author Anxian Zhang, Vick Ye, Duong Tran-Nam
 * @version 6
 * @since 20/04/2022
 */
public class SommetsDestination{
    private final ArrayList<Edge> destinations;

    protected SommetsDestination(){
        this.destinations = new ArrayList<>();
    }

    /**
     * Verifit l'existance d'un sommet de destination
     * @param dest sommet de destination
     * @return true s'il existe, sinon false
     * @see Edge#aSommetDestination(String)
     */
    protected boolean aSommetDestination(String dest){
        boolean aDest = false;
        for(Edge e : this.destinations)
            if (e.aSommetDestination(dest))
                aDest = true;
        return aDest;
    }

    /**
     * Permet l'ajout d'un arc avec un sommet de
     * destination et une valuation fournie
     * @param dest sommet de destination
     * @param val la valuation
     */
    protected void ajouterArc(String dest, int val){
        this.destinations.add(new Edge(dest, val));
    }

    /**
     * Retourne le nombre de destination d'un sommet
     * @return le nombre de destination
     */
    protected int nbDestination(){
        return this.destinations.size();
    }

    /**
     * Permet d'obtenire la valuation d'un arc, en fonction
     * du sommet de destination fournie
     * @param dest le sommet de destination
     * @return la valuation de l'arc
     */
    protected int getValuation(String dest){
        for (Edge e : this.destinations)
            if (e.aSommetDestination(dest))
                return e.getValuation();
        return 0;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(" -> ");
        for (Edge e : this.destinations)
            sb.append(e);
        return sb.toString();
    }
}

