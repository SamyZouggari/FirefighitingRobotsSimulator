import A_etoile.Astar;
import evenements.DebutDeplacement;
import gui.GUISimulator;
import objets.Carte;
import objets.Case;
import objets.Robot;
import simulation.DonneesSimulation;
import simulation.Simulateur;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.zip.DataFormatException;

public class testAetoile {

    public static void main(String[] args) throws DataFormatException, FileNotFoundException {
        // crée la fenêtre graphique dans laquelle dessiner
        // crée l'invader, en l'associant à la fenêtre graphique précédente
//        Invader invader = new Invader(gui, Color.decode("#f2ff28"));

        Simulateur simulateur = new Simulateur(new GUISimulator(800, 800, Color.BLACK),"cartes/carteSujet.map",null);
        DonneesSimulation donneesSimulation = simulateur.getDonneesSimulation("cartes/carteSujet.map");
        Robot robotCourant = donneesSimulation.getListeRobots().get(1);
        LinkedList<Carte.Direction> path = Astar.AStarSearch(donneesSimulation.getCarte(),robotCourant,donneesSimulation.getCarte().getCase(7,0));
        //LinkedList<Case> path = astar.AStarSearch(donneesSimulation.getCarte(),robotCourant,donneesSimulation.getCarte().getCase(7,0));
//        for (Case c: path) {
//            System.out.println(c + "--> ");
//        }
        for (Carte.Direction d: path) {
            System.out.println(d);
        }
    }

}