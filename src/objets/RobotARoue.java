package objets;

import A_etoile.Astar;
import gui.GUISimulator;
import gui.ImageElement;
import objets.Case.natureTerrain;
import simulation.Simulateur;

import static simulation.Simulateur.getTailleCase;

/**
 * Classe représentant un robot à roues.
 * Ce type de robot est rapide, possède un grand réservoir d'eau,
 * mais est limité aux terrains de type HABITAT et TERRAIN_LIBRE.
 */
public class RobotARoue extends Robot {

    /**
     * Constructeur par défaut d'un robot à roues.
     * Initialise les caractéristiques spécifiques au robot.
     *
     * @param case_robot La case initiale du robot.
     */
    public RobotARoue(Case case_robot) {
        super(case_robot);
        super.setVitesse(80 * 3.6); // Vitesse par défaut : 80 km/h.
        super.setReservoir(5000); // Capacité du réservoir : 5000 unités.
        super.setTempsRemplissage(10 * 60); // Temps de remplissage : 10 minutes.
        super.setVolEau(super.getReservoir()); // Volume d'eau initial : pleine capacité.
        super.setTempsIntervention(5); // Temps d'intervention : 5 secondes.
        super.setOccupe(false); // Le robot est initialement libre.
        super.setType(typeRobot.ROUES); // Définit le type de robot.
    }

    /**
     * Constructeur permettant de définir une vitesse personnalisée.
     *
     * @param case_robot La case initiale du robot.
     * @param vitesse    La vitesse du robot en km/h.
     */
    public RobotARoue(Case case_robot, double vitesse) {
        super(case_robot);
        super.setVitesse(vitesse);
        super.setReservoir(5000);
        super.setTempsRemplissage(10 * 60);
        super.setVolEau(super.getReservoir());
        super.setTempsIntervention(5);
        super.setOccupe(false);
        super.setType(typeRobot.ROUES);
    }

    /**
     * Remplit le réservoir d'eau si une case adjacente contient de l'eau.
     * Lève une exception si aucune case voisine n'est une case d'eau.
     */
    @Override
    public void remplirReservoir() {
        Case position = super.getCaseRobot();
        if (this.voisinEstEau(position)) {
            super.setVolEau(super.getReservoir());
        } else {
            throw new RuntimeException("Le robot doit être à côté d'une case d'eau pour remplir son réservoir.");
        }
    }

    /**
     * Vérifie si une case est accessible pour le robot.
     * Les robots à roues ne peuvent accéder qu'aux terrains de type HABITAT ou TERRAIN_LIBRE.
     *
     * @param src La case à vérifier.
     * @return true si la case est accessible, false sinon.
     */
    @Override
    public boolean caseAccessible(Case src) {
        natureTerrain nature = src.getNature();
        return nature == natureTerrain.HABITAT || nature == natureTerrain.TERRAIN_LIBRE;
    }

    /**
     * Retourne la vitesse du robot sur un type de terrain donné.
     * Les robots à roues conservent la même vitesse sur tous les terrains accessibles.
     *
     * @param natureTerrain Le type de terrain.
     * @return La vitesse du robot.
     */
    @Override
    public double getVitesse(natureTerrain natureTerrain) {
        return super.getVitesse();
    }

    /**
     * Retourne le type du robot.
     *
     * @return Le type du robot (TypeRobot.ROUES).
     */
    @Override
    public typeRobot getTypeRobot() {
        return typeRobot.ROUES;
    }

    /**
     * Effectue une intervention sur un incendie.
     * Le robot utilise 100 unités d'eau pour réduire l'intensité de l'incendie.
     * Si le réservoir est vide après l'intervention, il reste à 0.
     *
     * @param incendie L'incendie sur lequel le robot intervient.
     */
    @Override
    public void intervention(Incendie incendie) {
        int nbEau = incendie.getNbEau();

        if (nbEau > 100) {
            incendie.setNbEau(nbEau - 100);
        } else {
            incendie.setNbEau(0);
        }

        int nouveauVolEau = this.getVolEau() - 100;
        this.setVolEau(Math.max(nouveauVolEau, 0)); // Le volume d'eau ne peut pas être négatif.
    }

    /**
     * Dessine le robot à roues sur la carte.
     * L'image est positionnée en fonction des coordonnées actuelles du robot.
     */
    @Override
    public void drawRobotAbstrait() {
        Case caseRobot = super.getCaseRobot();
        if (caseRobot == null) {
            return;
        }

        int ligne = caseRobot.getLigne();
        int colonne = caseRobot.getColonne();
        String imagePath = "Images/ROUES.png"; // Chemin de l'image.
        int tailleCase = Simulateur.getTailleCase();
        GUISimulator gui = Simulateur.getGui();

        gui.addGraphicalElement(new ImageElement(
                colonne * tailleCase,
                ligne * tailleCase,
                imagePath,
                tailleCase,
                tailleCase,
                null
        ));
    }
}
