package pcc;

import java.util.Iterator;
/**
 * Brief: Interface IGraphe, permettant d'utiliser plus
 * simplement Les 2 types de graphe(matrice et liste adjacente)
 * @author Anxian Zhang, Vick Ye, Duong Tran-Nam
 * @version 2
 * @since 15/04/2022
 */
public interface IGraph {
    /**
     * Retourne le nombre de sommets d'un graphe
     * @return le nombre de sommets
     */
    int getNbSommets();

    /**
     * Permet l'ajout d'un arc valuer dans un graphe,
     * en donnant le sommet source, sa destination
     * et sa valuation
     * @param src sommet source
     * @param dest sommet de destination
     * @param val valeur de la valuation
     */
    void ajouterArc(String src, String dest, int val);

    /**
     * Permet de connaitre le nombre de sommet
     * sortant d'un graphe
     * @param sommet le sommet
     * @return le nombre de sommet sotant
     */
    int dOut(String sommet);

    /**
     * Permet de sonnaitre le nombre de sommet entrant
     * d'un graphe
     * @param sommet le sommet
     * @return le nombre de sommet sortant
     */
    int dIn(String sommet);

    /**
     * Permet de connaitre l'existance d'un arc en fonction
     * du sommet source et destination donne
     * @param src le sommet source
     * @param dest le sommet de destination
     * @return true si l'arc existe, false dans cas contraire
     */
    boolean aArc(String src, String dest);

    /**
     * Permet d'obtenire la valuation d'un arc, en fonction
     * du sommet source et destination fournie
     * @param src le sommet source
     * @param dest le sommet de destination
     * @return la valuation de l'arc
     */
    int getValuation(String src, String dest);

    /**
     * Renvoir une la chaine de caractere representent le graphe
     * @return une representation d'un graphe
     */
    String toString();

    /**
     * Verifie si le sommet source donne se trouve parmis les
     * sommets du graphe
     * @param s le sommet a verifier
     * @return true si c'est le cas sinon false
     */
    boolean aSommetSource(String s);

    /**
     * Verifie si le graphe contient un cycle.
     * @return vrai si c'est le cas, faux sinon.
     */
    boolean aCycle();

    /**
     * Permet de recuperer les sommets d'un graphe
     * @return les sommets
     */
    Iterator<String> iterator();
}