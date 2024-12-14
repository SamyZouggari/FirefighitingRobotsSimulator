import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.zip.DataFormatException;

import A_etoile.Astar;
import evenements.DebutDeplacement;
import gui.GUISimulator;

import javax.tools.Diagnostic;

import objets.Carte;
import objets.Carte.Direction;
import objets.Robot;
import simulation.Simulateur;
import simulation.DonneesSimulation;


public class testKO {
    public static void main(String[] args) throws DataFormatException, FileNotFoundException {
        // crée la fenêtre graphique dans laquelle dessiner
        Simulateur simulateur = new Simulateur(new GUISimulator(800, 800, Color.BLACK), "cartes/carteSujet.map", null);
        DonneesSimulation donneesSimulation = Simulateur.getDonneesSimulation("cartes/carteSujet.map");
        Robot robotCourant = DonneesSimulation.getListeRobots().get(0);
        robotCourant.setDeplacement(new LinkedList<>(Arrays.asList(Direction.NORD, Direction.NORD, Direction.NORD, Direction.NORD)));
        Simulateur.ajouteEvenement(new DebutDeplacement(0, robotCourant));
    }
}

