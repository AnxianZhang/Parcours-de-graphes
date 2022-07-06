package ihm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import exeption.*;
import graphe.la.GrapheLA;
import graphe.ma.GrapheMA;
import pcc.IGraph;
import pcc.PCC;
import pcc.algorithme.PCCBellman;
import pcc.algorithme.PCCDijkstra;

public class GrapheImporter {
    public static void main(String[] args) throws NumberFormatException, FileNotFoundException {
        Arc a = new Arc();
        IGraph g = importer("graphes\\ac\\g-1000-1.txt", a);

        IPCC pcc;
        try {
            if (g.aCycle()) {
                pcc = new PCCDijkstra(g, Integer.toString(a.getSource()));
                pcc.estOK();
                System.out.print("Dijkstra\n" + pcc.pCC(Integer.toString(a.getDestination())));
            } else {
                pcc = new PCCBellman(g, Integer.toString(a.getSource()));
                pcc.estOK();
                System.out.print("Bellman sans circuit \n" + pcc.pCC(Integer.toString(a.getDestination())));
            }
        } catch (ArcNegatifEx n) {
            System.out.println("Arc negative detecte, calcule impossible");
        } catch (NoPathEx p){
            System.out.println("pas de chemin entre " + a.getSource() + " et " + a.getDestination());
        } catch (CircuitAbsorbantEx ca){
            System.out.println("Circuit absorbant detecte, calcule impossible");
        }
    }

    public static IGraph importer(String filepath, Arc df) throws NumberFormatException, FileNotFoundException {
        File file = new File(filepath);
        return importer(file, df);
    }

    private static IGraph importer(File file, Arc df) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        String line;
        IGraph g;
        if (!sc.hasNextLine()) {
            sc.close();
            throw new IllegalArgumentException("Pas de graphe dans " + file);
        }
        line = sc.nextLine();
        int nbNodes = Integer.parseInt(line.trim());
        String[] noms = new String[nbNodes];
        for (int i = 0; i < nbNodes; ++i)
            noms[i] = String.valueOf(i + 1);

        // ***** ajout *****
        Random r = new Random();
        int i = r.nextInt(10);
        if (i % 2 == 0)
            g = new GrapheLA(noms);
        else
            g = new GrapheMA(noms);
        // *****************

        Arc a;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            a = parse(line);
            if (sc.hasNextLine())
                g.ajouterArc(String.valueOf(a.getSource()), String.valueOf(a.getDestination()), a.getValuation());
            else {
                // la dernière ligne n'est pas un arc mais le début et la fin du chemin à trouver
                df.set(a);
            }
        }
        sc.close();
        return g;
    }

    public static Arc parse(String string) {
        String[] parts = string.split(" ");
        int source;
        int valuation;
        int destination;
        try {
            source = Integer.parseInt(parts[0]);
            valuation = Integer.parseInt(parts[1]);
            destination = Integer.parseInt(parts[2]);
        } catch (Exception e) {
            throw new IllegalArgumentException(string + " n'est pas un arc");
        }
        return new Arc(source, valuation, destination);
    }

    public static boolean comparer(String fichierGraphe,String fichierReponse, IPCC algo, IGraph g , Arc a)
            throws NumberFormatException, IOException {

        String reponceDuFichierReponce = importerReponse(fichierReponse);
        StringBuilder calculeDeLaReponce = new StringBuilder();
        try {
            algo.estOK();

            if (g.aCycle())
                calculeDeLaReponce.append("Dijkstra\n");
            else
                calculeDeLaReponce.append("Bellman sans circuit\n");
            calculeDeLaReponce.append(algo.pCC(Integer.toString(a.getDestination())));
            calculeDeLaReponce.append("\n");

            System.out.println(fichierGraphe + " vs " + fichierReponse);
            System.out.println("Resultat calculer : \n" + calculeDeLaReponce);
            System.out.println("Resultat reponce : \n" + reponceDuFichierReponce);


        }catch (ArcNegatifEx n) {
            calculeDeLaReponce = new StringBuilder();
            calculeDeLaReponce.append("Arc negative detecte, calcule impossible");
            calculeDeLaReponce.append("\n");
        } catch (NoPathEx p){
            calculeDeLaReponce = new StringBuilder();
            calculeDeLaReponce.append("pas de chemin entre ").append(a.getSource());
            calculeDeLaReponce.append(" et ").append(a.getDestination());
            calculeDeLaReponce.append("\n");
        } catch (CircuitAbsorbantEx ca){
            calculeDeLaReponce = new StringBuilder();
            calculeDeLaReponce.append("Circuit absorbant detecte, calcule impossible");
            calculeDeLaReponce.append("\n");
        }
        System.out.println(calculeDeLaReponce);
        return reponceDuFichierReponce.equals(calculeDeLaReponce.toString());
    }

    public static String importerReponse(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner sc = new Scanner(file);
        StringBuilder sb = new StringBuilder();
        if (! sc.hasNextLine()) {
            sc.close();
            throw new IllegalArgumentException("Pas de reponse dans "+ file);
        }

        while (sc.hasNextLine()) {
            sb.append(sc.nextLine());
            sb.append("\n");
        }
        return sb.toString();
    }
}
