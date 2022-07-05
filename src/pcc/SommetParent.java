package pcc;

/**
 * Brief: Classe SommetParent, representant un sommet d'un graphe.
 * @author Anxian Zhang, Vick Ye, Duong Tran-Nam
 * @version 4
 * @since 27/04/2022
 */
public class SommetParent {
    private String sParent; // sommet parent
    private int sSource;
    private int coutParent; // cout pour venir jusqu'au parent

    /**
     * Constructeur d'objet SommetParent.
     */
    public SommetParent(){
        sParent = null;
        coutParent = Integer.MAX_VALUE;
    }

    /**
     * verifie si ce sommet est le sommet source.
     * @return True si ce sommet est le sommet source.
     * 		   False sinon.
     */
    private boolean estSommetDeDepart(){
        return this.coutParent == 0;
    }

    /**
     * renvoie le cout du sommet parent.
     * @return le cout du sommet parent.
     */
    public int getCoutParent(){
        return this.coutParent;
    }

    /**
     * renvoie le nom du sommet parent
     * @return le nom du sommet parent
     */
    public String getsParent(){
        return this.sParent;
    }

    /**
     * Met a jour un sommet parent
     * @param cp le nouveau cout
     * @param sp le nouveau sommet
     */
    public void miseAJourSommet(int cp, String sp){
        this.coutParent = cp;
        this.sParent = sp;
    }

    /**
     * Verifie si le cout du sommet parent est egale
     * a l'infinie
     * @return true si c'est le cas, sinon false
     */
    public boolean estInfinie(){
        return this.coutParent == Integer.MAX_VALUE;
    }

    /**
     * Met le sommet parent a 0 pour indiquer
     * que que c'est ce sommet qui fera office de
     * depart
     */
    public void sommetDeDepart(){
        assert(!estSommetDeDepart());
        this.coutParent = 0;
    }

    /**
     * Renvoie l'indice du sommet source.
     * @return l'indice du sommet source.
     */
    public int getsSource() {
        return sSource;
    }

    /**
     * Compare le cout du sommet parent de deux sommets.
     * @param sp1 Le premier sommet.
     * @param sp2 Le second.
     * @return La différence entre le cout du sommet parent
     * du premier sommet par rapport au au cout du second.
     */
    public static int comparator(SommetParent sp1, SommetParent sp2){
        return sp1.coutParent - sp2.coutParent;
    }
}