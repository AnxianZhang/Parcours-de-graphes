package ihm;

/**
 * Brief: Interface IPCC, permettant d'utiliser plus
 * simplement PCC
 * @author Anxian Zhang, Vick Ye, Duong Tran-Nam
 * @version 1
 * @since 14/05/2022
 */

public interface IPCC {

    /**
     * Renvoie le chemin le plus court, vers la destination
     * indique avec son cout
     *
     * @param dest destination
     * @return le chemin le plus court et son cout
     */
    String pCC(String dest);

    /**
     * Methode qui se specialise dans les classes
     * utilisant PCC, elle permet de verifier si les
     * contions pour utiliser tel algorithme sont
     * remplit
     * @return vrais si c'est le cas sinon
     * throws une exeption avec la contion non remplie
     */
    boolean estOK();
}
