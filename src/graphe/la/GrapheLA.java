package graphe.la;

import pcc.IGraph;

import java.util.*;
/**
 * Brief: Classe GrapheLA, permettant de manipuler
 * des graphes sous forme de liste
 * @author Anxian Zhang, Vick Ye, Duong Tran-Nam
 * @version 6
 * @since 20/04/2022
 */
public class GrapheLA implements IGraph {
    private final HashMap<String, Integer> sommetsLA;
    private final ArrayList<SommetsDestination> listAdja;

    public GrapheLA (String[] sommets){
        this.sommetsLA = new HashMap<>();
        this.listAdja = new ArrayList<>();
        initSommets(sommets);
    }

    /**
     * Initialise sommetsLA avec les sommets fournie en parametre
     * pour ensuite avoir une manipulation plus simple
     * des sommets, qui sera sous forme d'entiers.
     * Initialise aussi listAdja par le nombre de sommet
     * fournie
     * @param sommets les sommets fournie pour l'utilisateur
     */
    private void initSommets(String[] sommets){
        for(int i = 0; i < sommets.length; ++i) {
            /* manipulation plus ergonomique des sommets
             * sous forme d'entier */
            this.sommetsLA.put(sommets[i], i);
            this.listAdja.add(new SommetsDestination());
        }
    }

    @Override
    public int getNbSommets(){
        return this.sommetsLA.size();
    }

    @Override
    public boolean aSommetSource(String sommet){
        return this.sommetsLA.containsKey(sommet);
    }

    /**
     * Verifit l'existance d'un sommet de destination
     * @param src sommet source
     * @param dest sommet de destination
     * @return true s'il existe, sinon false
     * @see #aSommetSource(String)
     * @see SommetsDestination#aSommetDestination(String)
     */
    private boolean aSommetDestination(String src, String dest){
        assert (aSommetSource(src));
        SommetsDestination sd = this.listAdja.get(this.sommetsLA.get(src));
        return sd.aSommetDestination(dest);
    }

    @Override
    public boolean aArc(String source, String dest){
        return aSommetSource(source) && aSommetDestination(source, dest);
    }

    @Override
    public void ajouterArc(String source, String destination, int val) {
        assert (! aArc(source, destination));
        SommetsDestination sd = this.listAdja.get(this.sommetsLA.get(source));
        sd.ajouterArc(destination, val);
    }

    @Override
    public int dOut(String sommet){
        assert (aSommetSource(sommet));
        return this.listAdja.get(this.sommetsLA.get(sommet)).nbDestination();
    }

    @Override
    public int dIn(String sommet){
        assert (aSommetSource(sommet)); // verifie si le sommet existe
        int totalIn = 0;
        for (SommetsDestination sd : this.listAdja)
            if (sd.aSommetDestination(sommet))
                ++totalIn;
        return totalIn;
    }

    @Override
    public int getValuation(String src, String dest){
        assert (aArc(src, dest));
        SommetsDestination sd = this.listAdja.get(this.sommetsLA.get(src));
        return sd.getValuation(dest);
    }

    /**
     * Permet d'optenir la clef de la hashmap en fonction de la valeur de
     * la clef
     * @param map l'hashmap
     * @param value la valeur qui sera utiliser
     *              pour la recherche de clef
     * @return la clef
     */
    private String getSommetFromValue(HashMap<String, Integer> map, Integer value) {
        for (Map.Entry<String, Integer> entry : map.entrySet())
            if (Objects.equals(value, entry.getValue()))
                return entry.getKey();
        return null;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getNbSommets(); ++i){
            sb.append(getSommetFromValue(this.sommetsLA, i));
            sb.append(this.listAdja.get(i)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public Iterator<String> iterator(){
        return this.sommetsLA.keySet().iterator();
    }

    @Override
    public boolean aCycle() {
        for (int i = 0; i < this.sommetsLA.size(); ++i) {
            if(aChemin(i,i)) {
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
		boolean[] visites = new boolean[this.sommetsLA.size()];
		ArrayList<Integer> lstS = new ArrayList<>();
		lstS.add(sSrc);
		while(!lstS.isEmpty()) {
			int courant = lstS.get(0);
			lstS.remove(0);
			visites[courant] = true;
			for (int i = 0; i < this.sommetsLA.size(); ++i) {
				if(this.listAdja.get(courant).getValuation(getSommetFromValue(
                        this.sommetsLA, i)) > 0 && !visites[i]) {
					lstS.add(i);
					visites[i] = true;
				}
				else if (this.listAdja.get(courant).getValuation(getSommetFromValue(
                        this.sommetsLA, i)) > 0 && i == sFin) {
					return true;
				}
			}
		}
        return false;
    }
}