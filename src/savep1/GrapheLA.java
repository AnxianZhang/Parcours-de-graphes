package savep1;

import java.util.ArrayList;

public class GrapheLA{
    private final ArrayList<ArrayList<Integer>> liste;
    private final int NB_NOEUDS;

    public GrapheLA(int nb_noeuds) {
        this.NB_NOEUDS = nb_noeuds;
        this.liste = new ArrayList<>();
        initListe();
    }

    private void initListe(){
        for(int i = 0; i < this.NB_NOEUDS; ++i)
            this.liste.add(new ArrayList<>());
    }

    public int getNbNoeuds() {
        return this.NB_NOEUDS;
    }

    public void ajouterArc(int sommet1, int sommet2){
        if (sommet1 > 0 && sommet1 <= this.NB_NOEUDS
                && sommet2 > 0 && sommet2 <= this.NB_NOEUDS)
            this.liste.get(sommet1 - 1).add(sommet2);
    }

    public int dOut(int sommet) {
        int ctp_sommet = 0;
        if (sommet > 0 && sommet <= this.NB_NOEUDS)
            for (int i = 0; i < this.liste.get(sommet - 1).size(); ++i)
                if (this.liste.get(sommet - 1).get(i) > 0)
                    ++ctp_sommet;
        return ctp_sommet;
    }

    public int dIn(int sommet){
        int ctp_sommet = 0;
        if (sommet > 0 && sommet <= this.NB_NOEUDS)
            for (int i = 0; i < this.NB_NOEUDS; ++i)
                for (int j = 0; j < this.liste.get(i).size(); ++j){
                    if (this.liste.get(i).get(j) == sommet)
                        ++ctp_sommet;
                }
        return ctp_sommet;
    }

    public boolean aArc(int sommet1, int sommet2){
        return this.liste.get(sommet1 - 1).contains(sommet2);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < liste.size(); ++i) {
            sb.append(i + 1).append(" -> ");
            for (int j = 0; j < liste.get(i).size(); ++j){
                sb.append(this.liste.get(i).get(j)).append(' ');
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
