package pcc;

import exeption.NoPathEx;
import ihm.IPCC;

import java.util.*;

/**
 * Brief: Classe PCC, permettant de construire des
 * algorithme pour trouver les plus court chemin d'un
 * point sourcee au sommet de destination (tant que
 * cela est possible)
 * @author Anxian Zhang, Vick Ye, Duong Tran-Nam
 * @version 3
 * @since 27/04/2022
 */
public abstract class PCC implements IPCC {
    // les deux hashmap ont pour but de recuperer les les sommets
    // sous forme d'entier et inversement pour une manipulagtion plus facil
    private final HashMap<String, Integer> sommetsGrapheInt;
    private final HashMap<Integer, String> sommetsGrapheString;
    private final ArrayList<SommetParent> sP; // sommets parents
    private final ArrayList<Chaine> chaines;
    private final String source;
    private String pcc;
    private final IGraph iG;

    public PCC(IGraph iG, String source){
        this.sommetsGrapheInt = new HashMap<>();
        this.sommetsGrapheString = new HashMap<>();
        this.sP = new ArrayList<>();
        this.chaines = new ArrayList<>();
        this.iG = iG;
        this.source = source;

        initSommet();
        initChaine();
    }

    /**
     * Initilise tout les sommets
     */
    private void initSommet() {
        Iterator<String> it = this.iG.iterator();
        int i = 0;
        while (it.hasNext()){
            String sommet = it.next();
            this.sommetsGrapheInt.put(sommet, i);
            this.sommetsGrapheString.put(i, sommet);
            this.sP.add(new SommetParent());
            ++i;
        }
    }

    /**
     * Initialise tout les chaines
     */
    private void initChaine(){
        for (int i = 0; i < this.iG.getNbSommets(); ++i){
            for (int j = 0; j < this.iG.getNbSommets(); ++j) {
                if (this.iG.aArc(getSString(i), getSString(j))) {
                    this.chaines.add(new Chaine(getSString(i), getSString(j),
                            this.iG.getValuation(getSString(i), getSString(j))));
                }
            }
        }
    }

    /**
     * Initialise le sommet de parent en sommet de départ
     */
    protected void initSDeDepart(){
        this.sP.get(getSInt(this.source)).sommetDeDepart();
    }

    /**
     * Va rechercher le chemin le plus court
     * en commencant par le sommet de destination
     * puis par recurcivite cherche son parent, pour
     * abtenir tout le chemin
     *
     * @param dest la destination
     *
     * @see #getSInt(String)
     * @see #recherchePCC(String)
     * @see #ajouterSommetAuChemin(String)
     * @see SommetParent#getsParent()
     */
    private void recherchePCC(String dest){
        String parent = this.sP.get(getSInt(dest)).getsParent();
        if (parent == null)
            return;
        recherchePCC(parent);
        ajouterSommetAuChemin(parent);
    }

    /**
     * Verifis si un chemin existe vers la destination donne
     * si oui, cherche le chemin, sinon renvoie une
     * exeption du type: noPathEx
     *
     * @param dest la destination
     *
     * @see #ajouterSommetAuChemin(String)
     * @see #recherchePCC(String)
     * @see IGraph#aSommetSource(String)
     */
    private void essayPCC(String dest){
        if (!this.iG.aSommetSource(dest)) {
            throw new NoPathEx();
        }
        else {
            recherchePCC(dest);
            if (Objects.equals(this.pcc, ""))
                throw new NoPathEx();
            else
                ajouterSommetAuChemin(dest);
        }
    }

    public String pCC(String dest){
        this.pcc = ""; // initialise le plus court chemin
        try {
            essayPCC(dest);
        } catch (NoPathEx n) {
            throw new NoPathEx();
        }
        return this.sP.get(getSInt(dest))
                .getCoutParent() + "\n" + this.pcc;
    }

    /**
     * Met a jour le sommet parent a la position i
     * Si il y a une mise a jour alors renvoie true, sinon false
     * @param i sommet parent a la position i
     * @return true si il y a une mise a jour, sinon false
     *
     * @see #getSInt(String)
     * @see SommetParent#miseAJourSommet(int, String)
     * @see Chaine#getDestination()
     * @see Chaine#calculeCoutDestination(SommetParent)
     * @see Chaine#getSource()
     */
    protected boolean miseAJourParents(int i) {
        SommetParent sp = this.sP.get(getSInt(this.chaines.get(i).getSource()));
        if (canMaj(sp, i)) {
                // cout pour aller du sommet a vers un sommet b
                this.sP.get(getSInt(this.chaines.get(i).getDestination())).
                        miseAJourSommet(this.chaines.get(i).calculeCoutDestination(sp),
                                this.chaines.get(i).getSource());
                return true;
            }
        return false;
    }

    /**
     * Verifie si la mise a jour d'un sommet parent est possible.
     * Elle est effectuer si le sommet parent n'est pas de distance
     * inconu et si la valuation de chaine i plus le cout du sommet parent
     * est inferieur au sommet de destination
     * @param sp le sommet parent
     * @param i la chaine i
     * @return true si c'est le cas sinon false
     *
     * @see #getSInt(String)
     * @see SommetParent#estInfinie()
     * @see SommetParent#getCoutParent()
     * @see Chaine#estInferieur(int, int)
     * @see Chaine#getDestination()
     */
    protected boolean canMaj(SommetParent sp,int i){
        return !sp.estInfinie() && this.chaines.get(i).estInferieur(
                sp.getCoutParent(), this.sP.get(getSInt(this.chaines.get(i)
                        .getDestination())).getCoutParent());
    }

    /**
     * Verifie s'il y a un arc negative sur la
     * chaine i donne
     * @param i la chain
     * @return true si c'est le cas sinon false
     *
     * @see Chaine#aArcNegative()
     */
    protected boolean aArcNegative (int i){
        return this.chaines.get(i).aArcNegative();
    }

    /**
     * Retourne le sommet source (le sommet départ
     * de l'algorithme)
     * @return le sommet source
     */
    protected String getSource(){
        return this.source;
    }

    /**
     * Retourne le sommet parent à la positions numSommet
     * @param numSommet l'indice du sommet parent a retouner
     * @return le sommet parent demande
     */
    protected SommetParent getSP(int numSommet){
        return this.sP.get(numSommet);
    }

    /**
     * Retourne le nombre de sommet parent
     * @return le nombre de sommet parent
     */
    protected int getNbSP(){
        return this.sP.size();
    }

    /**
     * Retourne le nombre de chaine
     * @return le nombre de chaine
     */
    protected int getNbChaine(){
        return this.chaines.size();
    }

    /**
     * Retourne la destination d'une chaine i
     * @param i la chaine
     * @return la destination
     *
     * @see Chaine#getDestination()
     */
    protected String getSDestinationChaine(int i){
        return this.chaines.get(i).getDestination();
    }

    /**
     * Retourne la source d'une chaine i
     * @param i la chaine
     * @return la source de la chaine
     *
     * @see Chaine#getSource()
     */
    protected String getSSourceChaine(int i){
        return this.chaines.get(i).getSource();
    }

    /**
     * Verifie si une chaine i possede une chaine relier
     * par le sommet j
     * @param i la chaine
     * @param j le sommet
     * @return true c'est le cas, sinon false
     *
     * @see #getSString(int)
     * @see Chaine#aChaine(String)
     */
    protected boolean aUneChaineAvec(int i, int j){
        return this.chaines.get(i).aChaine(getSString(j));
    }

    /**
     * Cumul les sommmets correspondant au chemin
     * le plus court trouve entre 2 sommets
     * @param sommet le sommet qui sera ajoute
     */
    protected void ajouterSommetAuChemin(String sommet){
        this.pcc += sommet + " ";
    }

    /**
     * Permet d'obtenir le nombre de sommet
     * que posede le graphe donne en parametre
     * @return le nombre de sommet
     *
     * @see IGraph#getNbSommets()
     */
    protected int getNbSommets(){
        return this.iG.getNbSommets();
    }

    /**
     * Permet d'obtenir l'entier par rapport au
     * sommet donne
     * @param sommet le sommet
     * @return l'entier
     */
    protected int getSInt(String sommet){
        return this.sommetsGrapheInt.get(sommet);
    }

    /**
     * Permet d'obtenir le nom du sommet par rapport
     * a l'entier donne
     * @param numSommet l'entier
     * @return le sommet
     */
    protected String getSString(int numSommet){
        return this.sommetsGrapheString.get(numSommet);
    }
}
