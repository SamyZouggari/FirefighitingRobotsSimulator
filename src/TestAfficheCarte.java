import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;
import simulation.Simulateur;


public class TestAfficheCarte {
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        // crée l'invader, en l'associant à la fenêtre graphique précédente
//        Invader invader = new Invader(gui, Color.decode("#f2ff28"));

        Simulateur new_simulateur = new Simulateur(new GUISimulator(800, 800, Color.BLACK),"cartes/carteSujet.map",null);
    }
}
