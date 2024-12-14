package objets;

import gui.GUISimulator;
import simulation.DonneesSimulation;
import simulation.Simulateur;

import java.util.ArrayList;

import static objets.Case.natureTerrain;
import static objets.Case.drawCase;
import static objets.Incendie.drawIncendies;
import static objets.Robot.drawRobots;
import static objets.Robot.typeRobot;

/**
 * Classe représentant une carte composée de cases.
 * Elle gère les informations relatives aux cases, leur nature, et les déplacements sur la carte.
 */
public class Carte {
    /**
     * Taille des cases en mètres.
     */
    private static int tailleCases;

    /**
     * Tableau contenant les cases de la carte.
     */
    private static Case[][] caseTab;

    /**
     * Constructeur de la classe Carte. Initialise la carte avec un tableau de cases.
     *
     * @param nbLignes Nombre de lignes de la carte.
     * @param nbColonnes Nombre de colonnes de la carte.
     * @param taille Taille des cases en mètres.
     */
    public Carte(int nbLignes, int nbColonnes, int taille) {
        caseTab = new Case[nbLignes][nbColonnes];
        tailleCases = taille;
    }

    /**
     * Retourne la nature du terrain d'une case à des coordonnées données.
     *
     * @param ligne Ligne de la case.
     * @param colonne Colonne de la case.
     * @return La nature du terrain.
     */
    public natureTerrain getNatureTerrain(int ligne, int colonne) {
        return this.getCaseTab()[ligne][colonne].getNature();
    }

    /**
     * Retourne la taille des cases.
     *
     * @return La taille des cases en mètres.
     */
    public static int getTailleCases() {
        return tailleCases;
    }

    /**
     * Retourne le tableau contenant les cases de la carte.
     *
     * @return Le tableau de cases.
     */
    public Case[][] getCaseTab() {
        return caseTab;
    }

    /**
     * Retourne une case spécifique de la carte.
     *
     * @param ligne Ligne de la case.
     * @param colonne Colonne de la case.
     * @return La case demandée.
     */
    public Case getCase(int ligne, int colonne) {
        return caseTab[ligne][colonne];
    }

    /**
     * Définit une case à des coordonnées spécifiques dans la carte.
     *
     * @param ligne Ligne de la case.
     * @param colonne Colonne de la case.
     * @param data La case à définir.
     */
    public void setCase(int ligne, int colonne, Case data) {
        caseTab[ligne][colonne] = data;
    }

    /**
     * Enumération des directions possibles sur la carte : Nord, Sud, Est, Ouest.
     */
    public enum Direction {
        NORD, SUD, EST, OUEST;
    }

    /**
     * Vérifie si une case voisine existe dans une direction donnée.
     *
     * @param src La case source.
     * @param dir La direction dans laquelle vérifier.
     * @return true si un voisin existe, sinon false.
     */
    public static boolean voisinExiste(Case src, Direction dir) {
        int ligne = src.getLigne();
        int colonne = src.getColonne();
        switch (dir) {
            case NORD:
                return ligne != 0;
            case SUD:
                return ligne != caseTab.length - 1;
            case EST:
                return colonne != caseTab[0].length - 1;
            case OUEST:
                return colonne != 0;
            default:
                return false;
        }
    }

    /**
     * Retourne une case voisine dans une direction donnée.
     *
     * @param src La case source.
     * @param dir La direction du voisin.
     * @return La case voisine ou null si elle n'existe pas.
     */
    public static Case getVoisin(Case src, Direction dir) {
        int ligne = src.getLigne();
        int colonne = src.getColonne();
        if (!voisinExiste(src, dir)) {
            return null;
        }
        switch (dir) {
            case NORD:
                return caseTab[ligne - 1][colonne];
            case SUD:
                return caseTab[ligne + 1][colonne];
            case EST:
                return caseTab[ligne][colonne + 1];
            case OUEST:
                return caseTab[ligne][colonne - 1];
            default:
                return null;
        }
    }

    /**
     * Retourne tous les voisins accessibles autour d'une case pour un robot donné.
     *
     * @param caseCourante La case actuelle.
     * @param robot Le robot considéré.
     * @return Une liste des cases voisines accessibles.
     */
    public ArrayList<Case> allVoisins(Case caseCourante, Robot robot) {
        ArrayList<Case> voisins = new ArrayList<>();
        Case new_voisin;
        for (Direction d : Direction.values()) {
            new_voisin = getVoisin(caseCourante, d);
            if (new_voisin != null) {
                natureTerrain terrain = new_voisin.getNature();
                switch (terrain) {
                    case TERRAIN_LIBRE:
                    case HABITAT:
                        voisins.add(new_voisin);
                        break;
                    case FORET:
                        if (robot.getType() != typeRobot.ROUES) {
                            voisins.add(new_voisin);
                        }
                        break;
                    case EAU:
                        if (robot.getType() == typeRobot.DRONE) {
                            voisins.add(new_voisin);
                        }
                        break;
                    case ROCHE:
                        if (robot.getType() == typeRobot.PATTES || robot.getType() == typeRobot.DRONE) {
                            voisins.add(new_voisin);
                        }
                        break;
                }
            }
        }
        return voisins;
    }

    /**
     * Dessine la carte avec ses cases, incendies et robots.
     *
     * @param data Les données de la simulation.
     */
    public static void drawCarte(DonneesSimulation data) {
        int tailleH;
        int tailleW;
        int taille;
        GUISimulator gui = Simulateur.getGui();
        Carte carte = DonneesSimulation.getCarte();
        tailleW = gui.getPanelHeight() / carte.getCaseTab()[0].length;
        tailleH = gui.getPanelWidth() / carte.getCaseTab().length;
        taille = Math.min(tailleW, tailleH);
        Simulateur.setTailleCase(taille);

        for (int i = 0; i < carte.getCaseTab().length; i++) {
            for (int j = 0; j < carte.getCaseTab()[0].length; j++) {
                drawCase(carte.getCase(i, j));
            }
        }
        drawIncendies(DonneesSimulation.getListeIncendies());
        drawRobots(DonneesSimulation.getListeRobots());
    }
}
