package savep1;

import java.util.Arrays;

public class GrapheMA{
    private final int [][] matrice;
    private final int NB_NOEUDS;

    public GrapheMA(int nb_noeuds){
        this.NB_NOEUDS = nb_noeuds;
        this.matrice = new int [nb_noeuds][nb_noeuds];
        this.initMatrice();
    }

    private void initMatrice(){
        for(int i = 0; i < this.NB_NOEUDS; ++i)
            Arrays.fill(this.matrice[i], 0);
    }

    public int getNbNoeuds() {
        return this.NB_NOEUDS;
    }

    public void ajouterArc(int sommet1, int sommet2){
        if (sommet1 > 0 && sommet1 <= this.NB_NOEUDS
            && sommet2 > 0 && sommet2 <= this.NB_NOEUDS)
            ++this.matrice[sommet1 - 1][sommet2 - 1];
    }
    
    public int dOut(int sommet){
        int ctp_sommet = 0;
        if (sommet > 0 && sommet <= this.NB_NOEUDS)
            for(int val: this.matrice[sommet - 1])
                if (val > 0)
                    ++ctp_sommet;
        return ctp_sommet;
    }

    public int dIn(int sommet){
        int ctp_sommet = 0;
        if (sommet > 0 && sommet <= this.NB_NOEUDS)
            for (int i = 0; i < this.NB_NOEUDS; ++i)
                for (int j = 0; j < this.NB_NOEUDS; ++j){
                    if(this.matrice[j][i] > 0 && i == sommet - 1)
                        ++ctp_sommet;
                }
        return ctp_sommet;
    }

    public boolean aArc(int sommet1, int sommet2){
        return this.matrice[sommet1 - 1][sommet2 - 1] > 0;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.NB_NOEUDS; ++i){
            for (int j = 0; j < this.NB_NOEUDS; ++j){
                sb.append(this.matrice[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
