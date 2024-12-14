package objets;

import A_etoile.Astar;
import gui.GUISimulator;
import gui.ImageElement;
import objets.Case.natureTerrain;
import objets.Carte;
import simulation.Simulateur;

import java.util.LinkedList;

import static simulation.Simulateur.getTailleCase;

/**
 * Classe représentant un drone, qui est un type spécifique de robot.
 * Le drone peut se déplacer sur tous les types de terrain et possède des capacités spécifiques
 * telles qu'une vitesse élevée et un grand réservoir d'eau.
 */
public class Drone extends Robot {

    /**
     * Constructeur par défaut qui initialise un drone avec des valeurs prédéfinies.
     *
     * @param caseRobot La case initiale du drone.
     */
    public Drone(Case caseRobot) {
        super(caseRobot);
        super.setVitesse(100 * 3.6); // Vitesse par défaut : 100 km/h.
        super.setReservoir(10000); // Capacité du réservoir d'eau : 10000 unités.
        super.setTempsRemplissage(30 * 60); // Temps de remplissage : 30 minutes.
        super.setVolEau(super.getReservoir()); // Volume d'eau initial : pleine capacité.
        super.setTempsIntervention(30); // Temps d'intervention : 30 secondes.
        super.setOccupe(false); // Initialisation : drone non occupé.
        super.setType(typeRobot.DRONE); // Définit le type du robot comme DRONE.
    }

    /**
     * Constructeur permettant de définir une vitesse personnalisée.
     *
     * @param case_robot La case initiale du drone.
     * @param vitesse    La vitesse du drone, ne doit pas dépasser 150 km/h.
     * @throws IllegalArgumentException Si la vitesse dépasse 150 km/h.
     */
    public Drone(Case case_robot, double vitesse) {
        super(case_robot);
        if (vitesse > 150) {
            throw new IllegalArgumentException("La vitesse ne doit pas dépasser 150 km/h");
        }
        super.setVitesse(vitesse);
        super.setReservoir(5000); // Capacité du réservoir d'eau : 5000 unités.
        super.setTempsRemplissage(30 * 60); // Temps de remplissage : 30 minutes.
        super.setVolEau(super.getReservoir()); // Volume d'eau initial : pleine capacité.
        super.setTempsIntervention(30); // Temps d'intervention : 30 secondes.
        super.setOccupe(false); // Initialisation : drone non occupé.
        super.setType(typeRobot.DRONE); // Définit le type du robot comme DRONE.
    }

    /**
     * Remplit le réservoir d'eau du drone s'il se trouve sur une case contenant de l'eau.
     *
     * @throws RuntimeException Si la case actuelle n'est pas une case d'eau.
     */
    public void remplirReservoir() {
        if (this.getCaseRobot().getNature() == natureTerrain.EAU) {
            this.setVolEau(this.getReservoir()); // Remplit à pleine capacité.
        } else {
            throw new RuntimeException("Le robot ne peut remplir son réservoir que sur une case d'eau");
        }
    }

    /**
     * Calcule le temps nécessaire pour se déplacer entre deux cases en fonction
     * du chemin le plus court et de la vitesse.
     *
     * @param a                La case de départ.
     * @param b                La case d'arrivée.
     * @param carte            La carte sur laquelle se déplace le drone.
     * @param plusCourtChemin  Le chemin le plus court sous forme de directions.
     * @return Le temps de déplacement en secondes.
     */
    @Override
    public int tempsDeplacement(Case a, Case b, Carte carte, LinkedList<Carte.Direction> plusCourtChemin) {
        double vitesse = this.getVitesse(null);
        int tailleCase = getTailleCase();
        return (int) (tailleCase * plusCourtChemin.size() / vitesse);
    }

    /**
     * Vérifie si une case est accessible pour le drone.
     * Un drone peut accéder à toutes les cases.
     *
     * @param src La case source.
     * @return Toujours vrai.
     */
    public boolean caseAccessible(Case src) {
        return true;
    }

    /**
     * Récupère la vitesse du drone, qui est constante indépendamment du terrain.
     *
     * @param natureTerrain Le type de terrain (non utilisé ici).
     * @return La vitesse du drone.
     */
    public double getVitesse(natureTerrain natureTerrain) {
        return super.getVitesse();
    }

    /**
     * Récupère le type du robot.
     *
     * @return Le type du robot : DRONE.
     */
    @Override
    public typeRobot getTypeRobot() {
        return typeRobot.DRONE;
    }

    /**
     * Effectue une intervention pour réduire l'intensité d'un incendie.
     * Le drone utilise son réservoir d'eau pour diminuer l'intensité.
     *
     * @param incendie L'incendie à traiter.
     */
    public void intervention(Incendie incendie) {
        int volEau = this.getVolEau();
        int nbEau = incendie.getNbEau();

        if (nbEau > volEau) {
            incendie.setNbEau(nbEau - volEau);
        } else {
            incendie.setNbEau(0);
        }
        this.setVolEau(0); // Le réservoir est vidé après l'intervention.
    }

    /**
     * Dessine l'image du drone sur l'interface graphique.
     */
    public void drawRobotAbstrait() {
        Case caseRobot = super.getCaseRobot();
        if (caseRobot == null) {
            return;
        }
        int ligne = caseRobot.getLigne();
        int colonne = caseRobot.getColonne();
        String imagePath = "Images/DRONE.png";
        int tailleCase = getTailleCase();
        GUISimulator gui = Simulateur.getGui();
        gui.addGraphicalElement(new ImageElement(colonne * tailleCase, ligne * tailleCase, imagePath, tailleCase, tailleCase, null));
    }
}
