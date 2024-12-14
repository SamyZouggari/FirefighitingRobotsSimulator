package objets;

import gui.GUISimulator;
import gui.ImageElement;
import objets.Case.natureTerrain;
import simulation.Simulateur;

/**
 * Classe représentant un robot à pattes.
 * Ce type de robot peut se déplacer sur presque tous les types de terrain, sauf l'eau.
 * Son réservoir est symbolique, et il possède une vitesse réduite sur les terrains rocheux.
 */
public class RobotAPattes extends Robot {

    /**
     * Constructeur par défaut d'un robot à pattes.
     * Initialise les caractéristiques spécifiques au robot à pattes.
     *
     * @param caseRobot La case initiale du robot.
     */
    public RobotAPattes(Case caseRobot) {
        super(caseRobot);
        super.setVitesse(30 * 3.6); // Vitesse par défaut du robot : 30 km/h.
        super.setReservoir(1); // Capacité du réservoir d'eau : 1 unité.
        super.setVolEau(1); // Volume d'eau initial : 1 unité.
        super.setTempsRemplissage(1); // Temps de remplissage symbolique : 1 seconde.
        super.setTempsIntervention(1); // Temps d'intervention : 1 seconde.
        super.setType(typeRobot.PATTES); // Définit le type du robot.
        super.setOccupe(false); // Le robot est initialement non occupé.
    }

    /**
     * Vérifie si une case est accessible pour le robot à pattes.
     * Le robot à pattes ne peut pas accéder aux cases de type EAU.
     *
     * @param src La case à vérifier.
     * @return true si la case est accessible, false sinon.
     */
    @Override
    public boolean caseAccessible(Case src) {
        return src.getNature() != natureTerrain.EAU;
    }

    /**
     * Cette méthode est vide car le robot à pattes n'a pas besoin de remplir son réservoir.
     * Son réservoir est symbolique et toujours plein.
     */
    @Override
    public void remplirReservoir() {
        // Le réservoir du robot à pattes ne nécessite pas de remplissage.
    }

    /**
     * Retourne la vitesse du robot sur un terrain donné.
     * La vitesse est réduite à 10 km/h sur les terrains rocheux.
     *
     * @param natureTerrain Le type de terrain.
     * @return La vitesse du robot sur le terrain donné.
     */
    @Override
    public double getVitesse(natureTerrain natureTerrain) {
        if (natureTerrain == natureTerrain.ROCHE) {
            return 10; // Vitesse réduite sur terrain rocheux.
        }
        return super.getVitesse(); // Vitesse par défaut sur les autres terrains.
    }

    /**
     * Récupère le type du robot.
     *
     * @return Le type du robot (TypeRobot.PATTES).
     */
    @Override
    public typeRobot getTypeRobot() {
        return typeRobot.PATTES;
    }

    /**
     * Effectue une intervention sur un incendie.
     * Le robot réduit l'intensité de l'incendie en utilisant 10 unités d'eau.
     *
     * @param incendie L'incendie sur lequel le robot intervient.
     */
    @Override
    public void intervention(Incendie incendie) {
        int nbEau = incendie.getNbEau();
        if (nbEau > 10) {
            incendie.setNbEau(nbEau - 10);
        } else {
            incendie.setNbEau(0); // L'incendie est complètement éteint.
        }
    }

    /**
     * Dessine le robot à pattes sur la carte à l'aide d'une image.
     * La position de l'image est déterminée par la position actuelle du robot sur la carte.
     */
    @Override
    public void drawRobotAbstrait() {
        Case caseRobot = super.getCaseRobot();
        if (caseRobot == null) {
            return; // Pas de case associée au robot.
        }

        int ligne = caseRobot.getLigne();
        int colonne = caseRobot.getColonne();
        String imagePath = "Images/PATTES.png"; // Chemin de l'image représentant le robot.
        int tailleCase = Simulateur.getTailleCase();
        GUISimulator gui = Simulateur.getGui();

        // Ajoute l'image du robot à l'interface graphique.
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
