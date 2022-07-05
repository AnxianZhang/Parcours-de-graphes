package pcc.algorithme;

import exeption.ArcNegatifEx;
import pcc.IGraph;
import pcc.PCC;
import pcc.SommetParent;

import java.util.PriorityQueue;

/**
 * Brief: Classe PCCDijkstra, permettant de Trouver
 * le plus court chemin avec l'algorithme de Dijkstra
 * @author Anxian Zhang, Vick Ye, Duong Tran-Nam
 * @version 9
 * @since 27/04/2022
 */
public class PCCDijkstra extends PCC {
    /*Liste des sommets du graphe n'etant pas encore
    traites par l'agorithme.*/
    private final PriorityQueue<SommetParent> pQ;

    /**
     * Constructeur d'objet PCCDijkstra
     * @param iG Le graphe sur lequel est applique l'agorithme de Dijkstra.
     * @param src Le sommet a partir duquel l'agorithme debutera.
     */
    public PCCDijkstra(IGraph iG, String src){
        super(iG, src);
        this.pQ = new PriorityQueue<>(SommetParent::comparator);
    }

    /**
     * Determine le chemin le plus court (lorsqu'il existe) a partir du sommet source
     * definit dans le constructeur vers tous les autres sommets du graphe.
     *
     * @see PCC#getSP(int)
     * @see PCC#getSInt(String)
     * @see PCC#getSource()
     * @see PCC#initSDeDepart()
     * @see PCC#getNbSommets()
     * @see PCC#getNbChaine()
     * @see PCC#aUneChaineAvec(int, int)
     * @see #searchIdxOfSommetParent(SommetParent)
     * @see #miseAJourP(int)
     */
    private void dijkstra(){
        this.pQ.add(super.getSP(super.getSInt(super.getSource())));
        super.initSDeDepart();

        while (!this.pQ.isEmpty()){
            SommetParent sommetMin = this.pQ.poll();
            for(int i = searchIdxOfSommetParent(sommetMin); i < super.getNbSommets(); ++i)
                for (int j = 0; j < super.getNbChaine(); ++j)
                    if(super.aUneChaineAvec(j, i))
                        miseAJourP(j);
        }
    }

    /**
     * Si le sommet a ete prealablement mise a jour,
     * ajoute un sommet a la liste de sommets pas encore visité par
     * l'algorithme.
     * @param i L'indice du sommet a mettre a jour.
     *
     * @see PCC#getSP(int)
     * @see PCC#getSInt(String)
     * @see PCC#getSDestinationChaine(int)
     * @see #searchIdxOfSommetParent(SommetParent)
     */
    private void miseAJourP(int i) {
        if (super.miseAJourParents(i))
            this.pQ.add(super.getSP(searchIdxOfSommetParent(super.getSP(
                    super.getSInt(super.getSDestinationChaine(i))))));
    }

    /**
     * Cherche l'indice d'un sommet parmis les sommets du graphe donne
     * en constructeur.
     * @param sp Le sommet
     * @return L'indice du sommet demande s'il appartient au graphe.
     * 		   -1 sinon.
     */
    private int searchIdxOfSommetParent(SommetParent sp){
        for (int i = 0; i < super.getNbSP(); ++i){
            if (super.getSP(i).getsSource() == sp.getsSource())
                return i;
        }
        return -1;
    }

    /**
     * Test si le graphe contient un arc negatif.
     * @return True si la graphe contient un arc negatif.
     * 		   False sinon.
     */
    public boolean aArcNegative(){
        boolean estCorrrect = false;
        for (int j = 0; j < super.getNbChaine(); ++j)
            if (super.aArcNegative(j))
                estCorrrect = true;
        return estCorrrect;
    }

    /**
     * Si le graphe ne contient pas d'arc negatif,
     * cherche le plus court chemin du sommet source
     * vers tous les sommets du graphe.
     */
    public boolean estOK(){
        if (aArcNegative())
            throw new ArcNegatifEx();
        dijkstra();
        return true;
    }
}