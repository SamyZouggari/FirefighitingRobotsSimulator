package evenements;

import objets.*;
import simulation.DonneesSimulation;
import simulation.Simulateur;

import java.util.ArrayList;
import java.util.LinkedList;

import static objets.Case.drawCase;
import static objets.Robot.drawRobot;
import static simulation.DonneesSimulation.getListeIncendies;
import static simulation.DonneesSimulation.getListeRobots;

/**
 * Classe représentant la fin d'une intervention d'un robot sur un incendie.
 * Cette classe gère l'état du robot, la mise à jour des incendies et les étapes suivantes
 * après la fin de l'intervention.
 */
public class FinIntervention extends Evenement {

    /**
     * Le robot ayant effectué l'intervention.
     */
    private final Robot robot;

    /**
     * L'incendie sur lequel le robot est intervenu.
     */
    private final Incendie incendie;

    /**
     * Constructeur pour initialiser un événement de fin d'intervention.
     *
     * @param date La date de fin de l'intervention.
     * @param robot Le robot ayant effectué l'intervention.
     * @param incendie L'incendie traité par le robot.
     */
    FinIntervention(long date, Robot robot, Incendie incendie) {
        super(date);
        this.robot = robot;
        this.incendie = incendie;
    }

    /**
     * Exécute l'événement de fin d'intervention.
     * Met à jour l'état du robot, des incendies, et gère les étapes suivantes :
     * - Redessine la carte et les robots.
     * - Planifie une nouvelle intervention ou un déplacement pour réapprovisionnement en eau.
     */
    public void execute() {
        // Effectuer l'intervention sur l'incendie.
        this.robot.intervention(this.incendie);

        // Si l'incendie est éteint.
        if (this.incendie.getNbEau() == 0) {
            robot.setOccupe(false);
            this.incendie.setAttribue(false);
            Case caseRobot = this.robot.getCaseRobot();
            ArrayList<Incendie> incendies = getListeIncendies();
            incendies.remove(this.incendie);

            // Redessiner la case de l'incendie.
            drawCase(incendie.getCaseFeu());

            boolean dessiner = false;

            // Vérifie si d'autres robots se trouvent sur la même case pour les redessiner.
            ArrayList<Robot> robots = getListeRobots();
            for (Robot robot : robots) {
                if (robot.getCaseRobot() == caseRobot) {
                    drawRobot(robot);
                    dessiner = true;
                    break;
                }
            }

            // Si aucun autre robot, redessine simplement la case.
            if (!dessiner) {
                drawCase(caseRobot);
            }
        }
        // Si l'incendie n'est pas encore éteint mais le robot a encore de l'eau.
        else if (this.robot.getVolEau() > 0) {
            Simulateur.ajouteEvenement(new DebutIntervention((super.getDate()) + 1, this.robot, this.incendie));
        }

        // Si le robot n'a plus d'eau disponible.
        if (this.robot.getVolEau() == 0) {
            this.incendie.setAttribue(false);
            LinkedList<Carte.Direction> directions = ChefPompier.EauPlusProche(DonneesSimulation.getCarte(), this.robot);
            this.robot.setDeplacement(directions);
            Simulateur.ajouteEvenement(new DebutDeplacement((super.getDate()) + 1, this.robot, false, true));
        }
    }
}
