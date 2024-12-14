package simulation;

import objets.Carte;
import objets.Case;
import objets.Incendie;
import objets.Robot;

import java.util.ArrayList;

/**
 * Classe représentant les données de simulation.
 * Cette classe centralise toutes les informations nécessaires à la simulation,
 * telles que les robots, les incendies, les cases contenant de l'eau et la carte.
 */
public class DonneesSimulation {

    // Liste des robots présents dans la simulation
    private static ArrayList<Robot> listeRobot;

    // Liste des incendies présents sur la carte
    private static ArrayList<Incendie> listeIncendie;

    // Liste des cases contenant de l'eau
    private static ArrayList<Case> listeEau;

    // Carte représentant l'environnement de la simulation
    private static Carte carte;

    /**
     * Constructeur de la classe DonneesSimulation.
     * Initialise les listes pour les robots et les incendies.
     */
    public DonneesSimulation() {
        listeRobot = new ArrayList<>();
        listeIncendie = new ArrayList<>();
    }

    /**
     * Ajoute un robot à la liste des robots.
     *
     * @param robot Le robot à ajouter.
     */
    public void addRobot(Robot robot) {
        listeRobot.add(robot);
    }

    /**
     * Ajoute un incendie à la liste des incendies.
     * Si la liste est nulle, elle est initialisée avant l'ajout.
     *
     * @param incendie L'incendie à ajouter.
     */
    public void addIncendie(Incendie incendie) {
        if (listeIncendie != null) {
            listeIncendie.add(incendie);
        } else {
            listeIncendie = new ArrayList<>();
            listeIncendie.add(incendie);
        }
    }

    /**
     * Ajoute une case contenant de l'eau à la liste des cases d'eau.
     * Si la liste est nulle, elle est initialisée avant l'ajout.
     *
     * @param eau La case contenant de l'eau à ajouter.
     */
    public void addEau(Case eau) {
        if (listeEau != null) {
            listeEau.add(eau);
        } else {
            listeEau = new ArrayList<>();
            listeEau.add(eau);
        }
    }

    /**
     * Définit la carte de simulation.
     *
     * @param newCarte La nouvelle carte à définir.
     */
    public void setCarte(Carte newCarte) {
        carte = newCarte;
    }

    /**
     * Retourne la carte de simulation.
     *
     * @return La carte de simulation.
     */
    public static Carte getCarte() {
        return carte;
    }

    /**
     * Retourne la liste des robots présents dans la simulation.
     *
     * @return Une liste contenant les robots.
     */
    public static ArrayList<Robot> getListeRobots() {
        return listeRobot;
    }

    public static void setListeRobots(ArrayList<Robot> listeRobots){listeRobot=listeRobots;}

    /**
     * Retourne la liste des incendies présents sur la carte.
     *
     * @return Une liste contenant les incendies.
     */
    public static ArrayList<Incendie> getListeIncendies() {
        return listeIncendie;
    }

    public static void setListeIncendies(ArrayList<Incendie> listeIncendies){listeIncendie=listeIncendies;}

    /**
     * Retourne la liste des cases contenant de l'eau.
     *
     * @return Une liste contenant les cases d'eau.
     */
    public static ArrayList<Case> getListeEau() {
        return listeEau;
    }

    public static void setListeEau(ArrayList<Case> listeEaux){listeEau=listeEaux;}
}
