package graphe.ma;

import pcc.IGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Brief: Classe GrapheMA, permettant de manipuler
 * des graphes sous forme de matrice
 * @author Anxian Zhang, Vick Ye, Duong Tran-Nam
 * @version 6
 * @since 20/04/2022
 */
public class GrapheMA implements IGraph {
    private final HashMap<String, Integer> sommetsMA;
    private final int [][] matriceAdja;

    public GrapheMA (String[] sommets){
        this.sommetsMA = new HashMap<>();
        this.matriceAdja = new int[sommets.length][sommets.length];
        initSommets(sommets);
    }

    /**
     * Initialise sommetsLA avec les sommets fournie en parametre
     * pour ensuite avoir une manipulation plus simple
     * des sommets, qui sera sous forme d'entiers.
     * Initialise aussi matriceAdja avec des 0, indiquant
     * que qu'il y a aucuns lien entre les sommets
     * @param sommets les sommets fournie pour l'utilisateur
     */
    private void initSommets(String[] sommets){
        for(int i = 0; i < sommets.length; ++i) {
            /* manipulation plus ergonomique des sommets
             * sous forme d'entier */
            this.sommetsMA.put(sommets[i], i);
            Arrays.fill(this.matriceAdja[i], 0);
        }
    }

    @Override
    public int getNbSommets(){
        return this.sommetsMA.size();
    }

    @Override
    public boolean aSommetSource(String sommet){
        return this.sommetsMA.containsKey(sommet);
    }

    /**
     * Verifit l'existance d'un sommet de destination
     * @param src sommet source
     * @param dest sommet de destination
     * @return true s'il existe, sinon false
     * @see #aSommetSource(String)
     */
    private boolean aSommetDestination(String src, String dest) {
        assert (aSommetSource(src) && aSommetSource(dest));
        boolean aDestination = false;
        for (int i = 0; i < getNbSommets(); ++i)
            if (i == this.sommetsMA.get(dest))
                if (this.matriceAdja[this.sommetsMA.get(src)][i] != 0)
                    aDestination = true;
        return aDestination;
    }

    @Override
    public boolean aArc(String src, String dest){
        return aSommetSource(src) && aSommetDestination(src, dest);
    }

    @Override
    public void ajouterArc(String src, String dest, int val){
        assert (! aArc(src, dest));
        this.matriceAdja[this.sommetsMA.get(src)][this.sommetsMA.get(dest)] = val;
    }

    @Override
    public int dOut(String sommet){
        assert (aSommetSource(sommet));
        int ctp_sommet = 0;
        for(int val: this.matriceAdja[this.sommetsMA.get(sommet)])
            if (val != 0)
                ++ctp_sommet;
        return ctp_sommet;
    }

    @Override
    public int dIn(String sommet) {
        assert (aSommetSource(sommet));
        int ctp_sommet = 0;
        for (int i = 0; i < getNbSommets(); ++i)
            if (this.matriceAdja[i][this.sommetsMA.get(sommet)] != 0)
                ++ctp_sommet;
        return ctp_sommet;
    }

    @Override
    public int getValuation(String src, String dest){
        assert (aArc(src, dest));
        return this.matriceAdja[this.sommetsMA.get(src)][this.sommetsMA.get(dest)];
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getNbSommets(); ++i){
            for (int j = 0; j < getNbSommets(); ++j){
                sb.append(this.matriceAdja[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public Iterator<String> iterator(){
        return this.sommetsMA.keySet().iterator();
    }

    @Override
    public boolean aCycle() {
        for (int i = 0; i < this.sommetsMA.size(); ++i) {
            if (aChemin(i,i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifie si le graphe contient un chemin partant d'un sommet sSrc
     * vers un second sommet sFin.
     * @param sSrc Le sommet de départ
     * @param sFin Le sommet d'arrivée
     * @return True si c'est le cas, false sinon.
     */
    private boolean aChemin(int sSrc, int sFin) {
        boolean[] visites = new boolean[this.sommetsMA.size()];
        ArrayList<Integer> lstS = new ArrayList<>();
        lstS.add(sSrc);
        while(!lstS.isEmpty()) {
            int courant = lstS.get(0);
            lstS.remove(0);
            visites[courant] = true;
            for (int i = 0; i < this.sommetsMA.size(); ++i) {
                if(this.matriceAdja[courant][i] > 0 && !visites[i]) {
                    lstS.add(i);
                    visites[i] = true;
                }
                else if (this.matriceAdja[courant][i] > 0 && i == sFin) {
                    return true;
                }
            }
        }
        return false;
    }
}