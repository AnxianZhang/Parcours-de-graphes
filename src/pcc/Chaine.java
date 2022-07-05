package pcc;

import java.util.Objects;

/**
 * Brief: Classe Chaine, permettant de creer une chaine
 * @author Anxian Zhang, Vick Ye, Duong Tran-Nam
 * @version 6
 * @since 27/04/2022
 */
public class Chaine {

    private final String source, destination;
    private final int valuation;

    public Chaine(String src, String dest, int val){
        this.source = src;
        this.destination = dest;
        this.valuation = val;
    }

    /**
     * Verifit si l'addition entre la valuation de la chaine
     * + le cout du parent est bien inferieur au cout de la
     * destination
     *
     * @param coutParent cout du parent (le depart de la arc)
     * @param coutDestination cout de la destination (de la chaine)
     * @return true si c'est le cas, sinon false
     */
    public boolean estInferieur(int coutParent, int coutDestination){
        return this.valuation + coutParent < coutDestination;
    }

    /**
     * Calcule le cout (la valuation + le cout du parent)
     * pour aller jusqu'a la destination
     *
     * @param sp le sommet parent
     * @return le cout de la destination
     */
    public int calculeCoutDestination(SommetParent sp){
        return this.valuation + sp.getCoutParent();
    }

    /**
     * Renvoit la source de la chaine
     * @return la source
     */
    public String getSource() {
        return this.source;
    }

    /**
     * Determine si le sommet donne correspond a
     * la source de chaine
     * @param sommet le sommet a comparer
     * @return true si c'est le cas, sinon false
     */
    public boolean aChaine(String sommet){
        return Objects.equals(this.source, sommet);
    }

    /**
     * Renvoie la destination de la chaine
     * @return la destination
     */
    public String getDestination(){
        return this.destination;
    }

    /**
     * Verifit si la chaine possede une valuation negative
     * @return true si c'est le cas sinon false
     */
    public boolean aArcNegative(){
        return this.valuation < 0;
    }
}
