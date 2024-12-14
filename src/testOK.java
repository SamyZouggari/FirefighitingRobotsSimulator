import evenements.DebutDeplacement;
import evenements.DebutRemplissage;
import evenements.DebutIntervention;
import gui.GUISimulator;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.DataFormatException;

import objets.Case;
import simulation.DonneesSimulation;
import simulation.Simulateur;
import objets.Robot;
import objets.Carte;



public class testOK {
    public static void main(String[] args) throws DataFormatException, FileNotFoundException {
        // crée la fenêtre graphique dans laquelle dessiner

        new Simulateur(new GUISimulator(800, 800, Color.BLACK),"cartes/carteSujet.map",null);
        Simulateur.getDonneesSimulation("cartes/carteSujet.map");
        Robot robotCourant = DonneesSimulation.getListeRobots().get(1);
        Simulateur.ajouteEvenement(new DebutDeplacement(0, robotCourant,Carte.Direction.NORD));
        double temps = robotCourant.tempsChangement(robotCourant.getCaseRobot());
        Case feux=Carte.getVoisin(robotCourant.getCaseRobot(),Carte.Direction.NORD) ;
        Simulateur.ajouteEvenement(new DebutIntervention(Math.round(temps) + 10, robotCourant, feux));
        temps= Math.round(temps) + 10;
        temps+=50*5+100;
        Simulateur.ajouteEvenement(new DebutDeplacement(Math.round(temps) + 1, robotCourant,Carte.Direction.OUEST));
        temps+=robotCourant.tempsChangement(robotCourant.getCaseRobot())+100;
        Simulateur.ajouteEvenement(new DebutDeplacement(Math.round(temps) + 1, robotCourant,Carte.Direction.OUEST));
        temps+=robotCourant.tempsChangement(robotCourant.getCaseRobot())+100;
        Simulateur.ajouteEvenement(new DebutRemplissage(Math.round(temps) + 1, robotCourant));
        temps+=10*60+100;
        Simulateur.ajouteEvenement(new DebutDeplacement(Math.round(temps) + 1, robotCourant,Carte.Direction.EST));
        temps+=robotCourant.tempsChangement(robotCourant.getCaseRobot())+100;
        Simulateur.ajouteEvenement(new DebutDeplacement(Math.round(temps) + 1, robotCourant,Carte.Direction.EST));
        temps+=robotCourant.tempsChangement(robotCourant.getCaseRobot())+100;
        Simulateur.ajouteEvenement(new DebutIntervention(Math.round(temps) + 1, robotCourant, feux));
        temps+=50*5+100;
        Simulateur.ajouteEvenement(new DebutDeplacement(Math.round(temps) + 1, robotCourant,Carte.Direction.SUD));
    }
}

