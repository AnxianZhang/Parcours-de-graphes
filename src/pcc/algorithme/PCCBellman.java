package pcc.algorithme;

import exeption.CircuitAbsorbantEx;
import pcc.IGraph;
import pcc.PCC;
import pcc.SommetParent;

/**
 * Brief: Classe PCCBellman, permettant de Trouver
 * le plus court chemin avec l'algorithme de Bellman
 * @author Anxian Zhang, Vick Ye, Duong Tran-Nam
 * @version 10
 * @since 27/04/2022
 */
public class PCCBellman extends PCC {
    public PCCBellman(IGraph graphe, String src) {
        super(graphe, src);
    }

    /**
     * Calcule les chemins les plus courts d'un sommet donnee
     * (depuis le constructeur) vers tout les autres
     * sommets (s'il est possible)
     *
     * @see PCC#initSDeDepart()
     * @see PCC#getNbSommets()
     * @see PCC#miseAJourParents(int)
     */
    private void bellman() {
        //initialisation du sommet, en tant que sommet de depart
        super.initSDeDepart();

        for (int i = 0; i < super.getNbSommets(); ++i) {
            // verifie si un mise a jour a ete faite
            boolean aMaj = false;

            for (int j = 0; j < super.getNbChaine(); ++j) {
                super.miseAJourParents(j);
                aMaj = true;
            }
            if (!aMaj)
                break;
        }
    }


    /**
     * Verifit s'il n'y a pas de circuit absorbant, grace aux
     * chemins les plus courts calcule
     *
     * @return true s'il n'y a pas de circuit absorbant
     * sinon revoit une exeption de type: CircuitAbsorbantEx
     *
     * @see #bellman()
     * @see PCC#getNbChaine()
     * @see PCC#getSP(int)
     * @see PCC#getSInt(String)
     * @see PCC#getSSourceChaine(int)
     * @see PCC#canMaj(SommetParent, int)
     */
    public boolean estOK() {
        bellman();

        for (int i = 0; i < super.getNbChaine(); ++i) {
            SommetParent sp = super.getSP(super.getSInt(super.getSSourceChaine(i)));
            if (super.canMaj(sp, i)) {
                // cout pour aller du sommet a vers un sommet b
                throw new CircuitAbsorbantEx();
            }
        }
        return true;
    }
}