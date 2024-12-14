package objets;

import gui.GUISimulator;
import gui.ImageElement;
import simulation.Simulateur;
import objets.Case.natureTerrain;

public class RobotAChenilles extends Robot {
    /**
     * Constructeur par défaut d'un robot à chenilles.
     * @param case_robot La case initiale du robot.
     */
    public RobotAChenilles(Case case_robot) {
        super(case_robot);
        super.setVitesse(60*3.6); // Vitesse par défaut : 60 km/h.
        super.setReservoir(2000); // Capacité du réservoir d'eau : 2000 unités.
        super.setTempsRemplissage(5 * 60); // Temps de remplissage du réservoir : 5 minutes.
        super.setVolEau(super.getReservoir()); // Volume d'eau initial : égal à la capacité du réservoir.
        super.setTempsIntervention(8); // Temps d'intervention sur un incendie : 8 secondes.
        super.setOccupe(false); // Indique que le robot n'est pas occupé.
        super.setType(typeRobot.CHENILLES); // Définit le type du robot.
    }

    /**
     * Constructeur avec vitesse personnalisée pour le robot à chenilles.
     * @param case_robot La case initiale du robot.
     * @param vitesse La vitesse du robot.
     */
    public RobotAChenilles(Case case_robot, double vitesse) {
        super(case_robot);
        // La vitesse ne doit pas dépasser 80 km/h pour ce type de robot.
        if (vitesse > 80) {
            throw new IllegalArgumentException("La vitesse ne doit pas dépasser 80 km/h");
        }
        super.setVitesse(vitesse);
        super.setReservoir(2000);
        super.setTempsRemplissage(5 * 60);
        super.setVolEau(super.getReservoir());
        super.setTempsIntervention(8);
        super.setOccupe(false);
        super.setType(typeRobot.CHENILLES);
    }

    /**
     * Détermine si une case est accessible pour le robot à chenilles.
     * Le robot ne peut pas accéder aux cases de type EAU ou ROCHE.
     * @param src La case à vérifier.
     * @return Vrai si la case est accessible, faux sinon.
     */
    @Override
    public boolean caseAccessible(Case src) {
        natureTerrain nature = src.getNature();
        // Le robot ne peut pas accéder aux cases d'eau ou rocheuses.
        if (nature == natureTerrain.EAU || nature == natureTerrain.ROCHE) {
            return false;
        }
        return true;
    }

    /**
     * Remplit le réservoir d'eau du robot.
     * Le robot peut uniquement remplir son réservoir sur une case d'eau.
     */
    @Override
    public void remplirReservoir() {
        Case position = super.getCaseRobot();
        if (this.voisinEstEau(position)) {
            this.setVolEau(this.getReservoir()); // Remplit complètement le réservoir.
        } else {
            throw new RuntimeException("Le robot ne peut remplir son réservoir que sur une case d'eau");
        }
    }

    /**
     * Retourne la vitesse du robot en fonction du type de terrain.
     * La vitesse est réduite de moitié sur les terrains de type FORÊT.
     * @param nature Le type de terrain.
     * @return La vitesse du robot sur le terrain donné.
     */
    public double getVitesse(natureTerrain nature) {
        double vitesse = super.getVitesse();
        if (nature == natureTerrain.FORET) {
            return vitesse / 2; // Vitesse réduite de moitié en forêt.
        }
        return vitesse;
    }

    /**
     * Récupère le type du robot.
     * @return TypeRobot.CHENILLES.
     */
    @Override
    public typeRobot getTypeRobot() {
        return typeRobot.CHENILLES;
    }

    /**
     * Effectue une intervention sur un incendie.
     * Le robot utilise 100 unités d'eau pour réduire l'intensité de l'incendie.
     * @param incendie L'incendie sur lequel le robot intervient.
     */
    public void intervention(Incendie incendie) {
        int nbEau = incendie.getNbEau();

        // Si l'incendie nécessite plus de 100 unités d'eau, réduit l'intensité de l'incendie.
        if (nbEau > 100) {
            incendie.setNbEau(nbEau - 100);
        } else {
            incendie.setNbEau(0); // Éteint complètement l'incendie.
        }
        // Réduit le volume d'eau du réservoir du robot de 100 unités.
        int j=this.getVolEau()-100;
        if(j<=0){
            j=0;
        }
        this.setVolEau(j);
    }

    public void  drawRobotAbstrait() {
        Case caseRobot = super.getCaseRobot();
        if (caseRobot == null) {
            return;
        }
        int ligne = caseRobot.getLigne();
        int colonne = caseRobot.getColonne();
        String imagePath = "Images/CHENILLES.png";
        int tailleCase = Simulateur.getTailleCase();
        GUISimulator gui = Simulateur.getGui();
        gui.addGraphicalElement(new ImageElement(colonne * tailleCase, ligne * tailleCase, imagePath, tailleCase, tailleCase, null));
    }
}
