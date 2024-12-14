import A_etoile.Astar;
import evenements.DebutDeplacement;
import gui.GUISimulator;
import objets.Carte;
import objets.Case;
import objets.ChefPompier;
import objets.Robot;
import simulation.DonneesSimulation;
import simulation.Simulateur;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.zip.DataFormatException;

public class testChefPompier {

        public static void main(String[] args) throws DataFormatException, FileNotFoundException {

                if (args.length == 0) {
                        System.out.println("Veuillez spécifier le chemin du fichier en argument.");
                        return;
                }

                if (!Objects.equals(args[1], "simple") && !Objects.equals(args[1], "avancee") && !Objects.equals(args[1], "reflechie")) {
                        System.out.println("Veuillez choisir l'attribution que vous voulez que le chef pompier adopte : ");
                        System.out.println(" - simple");
                        System.out.println(" - avancee");
                        System.out.println(" - reflechie");
                        return;
                }
                String filepath = args[0];
                String attribution = args[1];
                new Simulateur(new GUISimulator(800, 800, Color.BLACK), filepath,attribution);
                Simulateur.getDonneesSimulation(filepath);
                switch (attribution) {
                        case "simple":
                                ChefPompier.AttributionSimple(DonneesSimulation.getCarte());
                                break;
                        case "avancee":
                                ChefPompier.AttributionAvancee(DonneesSimulation.getCarte());
                                break;
                        case "reflechie":
                                ChefPompier.AttributionReflechie(DonneesSimulation.getCarte());
                                break;
                        default:
                                System.out.println("Veuillez entrer l'une des trois attribution possible : simple, avancee ou reflechie");
                                break;
                }
        }

}
