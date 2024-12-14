package objets;

import evenements.Evenement;
import objets.Case.natureTerrain;
import objets.Carte.Direction;

import java.util.ArrayList;
import java.util.LinkedList;

import static objets.Carte.Direction.*;
import static objets.Carte.getTailleCases;
import static objets.Carte.getVoisin;

/**
 * Classe abstraite représentant un robot dans la simulation.
 * Les robots possèdent des caractéristiques spécifiques (type, vitesse, réservoir, etc.)
 * et peuvent se déplacer, intervenir sur des incendies et remplir leur réservoir.
 */
public abstract class Robot {

    private int volEau; // Volume d'eau que le robot possède actuellement
    private Case caseRobot; // La case sur laquelle le robot est actuellement positionné
    private double vitesse; // La vitesse du robot (en km/h)
    private int reservoir; // Capacité du réservoir d'eau du robot
    private int tempsRemplissage; // Temps nécessaire pour remplir le réservoir (en secondes)
    private int tempsIntervention; // Temps nécessaire pour intervenir sur un incendie
    private boolean occupe; // Indique si le robot est occupé
    private typeRobot type; // Le type de robot

    private LinkedList<Direction> deplacement; // Liste des directions pour déplacer le robot

    /**
     * Enumération représentant les types de robots disponibles dans la simulation.
     */
    public enum typeRobot {
        DRONE, ROUES, PATTES, CHENILLES
    }

    /**
     * Constructeur pour initialiser un robot avec sa position de départ.
     * @param caseRobot La case initiale du robot.
     */
    public Robot(Case caseRobot) {
        this.caseRobot = caseRobot;
    }

    // ==== GETTERS & SETTERS ====

    /**
     * Retourne le type du robot.
     * @return Le type de robot.
     */
    public typeRobot getType() {
        return this.type;
    }

    /**
     * Définit le type du robot.
     * @param type Le type du robot.
     */
    public void setType(typeRobot type) {
        this.type = type;
    }

    public boolean isOccupe() {
        return occupe;
    }

    public void setOccupe(boolean occupe) {
        this.occupe = occupe;
    }

    public LinkedList<Direction> getDeplacement() {
        return this.deplacement;
    }

    public void setDeplacement(LinkedList<Direction> deplacement) {
        this.deplacement = deplacement;
    }


    public Case getCaseRobot() {
        return this.caseRobot;
    }

    public int getVolEau() {
        return this.volEau;
    }

    public void setVolEau(int volEau) {
        this.volEau = volEau;
    }

    public double getVitesse() {
        return this.vitesse;
    }

    public void setVitesse(double vitesse) {
        this.vitesse = vitesse;
    }

    public int getReservoir() {
        return this.reservoir;
    }

    public void setReservoir(int reservoir) {
        this.reservoir = reservoir;
    }

    public int getTempsRemplissage() {
        return this.tempsRemplissage;
    }

    public void setTempsRemplissage(int tempsRemplissage) {
        this.tempsRemplissage = tempsRemplissage;
    }

    public int getTempsIntervention() {
        return this.tempsIntervention;
    }

    public void setTempsIntervention(int tempsIntervention) {
        this.tempsIntervention = tempsIntervention;
    }

    // ==== MÉTHODES ====

    /**
     * Déplace le robot vers une nouvelle case si elle est accessible.
     * @param src La case vers laquelle déplacer le robot.
     */
    public void deplaceRobot(Case src) {
        if (!caseAccessible(src)) {
            throw new IllegalArgumentException("Case non accessible");
        }
        this.caseRobot = src;
    }

    /**
     * Vérifie si l'une des cases voisines du robot est de type EAU.
     * @param caseRobot La case actuelle du robot.
     * @return true si une case voisine est de type EAU, false sinon.
     */
    protected boolean voisinEstEau(Case caseRobot) {
        Case[] voisins = {
                getVoisin(caseRobot, NORD),
                getVoisin(caseRobot, SUD),
                getVoisin(caseRobot, EST),
                getVoisin(caseRobot, OUEST)
        };
        for (Case voisin : voisins) {
            if (voisin != null && voisin.getNature() == natureTerrain.EAU) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calcule le temps de changement de vitesse du robot en fonction du terrain de la case donnée.
     *
     * @param a La case pour laquelle le temps de changement est calculé.
     * @return Le temps de déplacement en secondes pour traverser la case donnée.
     */
    public double tempsChangement(Case a) {
        double vitesse = getVitesse(a.getNature()); // Obtient la vitesse du robot sur ce type de terrain.
        if (vitesse == 0) {
            throw new IllegalArgumentException("Le robot ne peut pas se déplacer sur ce type de terrain.");
        }
        return getTailleCases() / vitesse; // Temps de déplacement = taille de la case / vitesse.
    }

    /**
     * Calcule le temps de déplacement entre deux cases en fonction des vitesses sur les cases parcourues.
     * @param a La case de départ.
     * @param b La case d'arrivée.
     * @param carte La carte de simulation.
     * @param plusCourtChemin Le chemin à parcourir (liste de directions).
     * @return Le temps de déplacement en secondes.
     */
    public int tempsDeplacement(Case a, Case b, Carte carte, LinkedList<Direction> plusCourtChemin) {
        int tailleCase = simulation.Simulateur.getTailleCase();
        double sommeVitesse = 0;
        Case caseCourante = a;

        for (Direction dir : plusCourtChemin) {
            sommeVitesse += this.getVitesse(caseCourante.getNature());
            caseCourante = Carte.getVoisin(caseCourante, dir);
        }

        return (int) ((tailleCase * plusCourtChemin.size()) / (sommeVitesse / plusCourtChemin.size()));
    }

    /**
     * Dessine tous les robots de la liste.
     * @param listeRobot Liste des robots à dessiner.
     */
    public static void drawRobots(ArrayList<Robot> listeRobot) {
        for (Robot r : listeRobot) {
            r.drawRobotAbstrait();
        }
    }

    /**
     * Dessine un robot spécifique.
     * @param robot Le robot à dessiner.
     */
    public static void drawRobot(Robot robot) {
        robot.drawRobotAbstrait();
    }

    // ==== MÉTHODES ABSTRAITES ====

    /**
     * Méthode abstraite pour dessiner le robot.
     */
    public abstract void drawRobotAbstrait();

    /**
     * Retourne la vitesse du robot en fonction de la nature du terrain.
     * @param natureTerrain Le type de terrain.
     * @return La vitesse du robot sur ce terrain.
     */
    public abstract double getVitesse(natureTerrain natureTerrain);

    /**
     * Vérifie si une case est accessible pour le robot.
     * @param src La case à vérifier.
     * @return true si la case est accessible, false sinon.
     */
    public abstract boolean caseAccessible(Case src);

    /**
     * Remplit le réservoir d'eau du robot.
     */
    public abstract void remplirReservoir();

    /**
     * Intervient sur un incendie.
     * @param incendie L'incendie à traiter.
     */
    public abstract void intervention(Incendie incendie);

    /**
     * Retourne le type de robot.
     * @return Le type de robot.
     */
    public abstract typeRobot getTypeRobot();
}
