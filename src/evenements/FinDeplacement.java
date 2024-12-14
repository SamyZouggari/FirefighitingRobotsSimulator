package evenements;

import java.util.ArrayList;
import objets.Case;
import objets.Incendie;
import objets.Robot;
import simulation.DonneesSimulation;

import static objets.Case.drawCase;
import static objets.Incendie.drawIncendie;
import static objets.Robot.drawRobot;

/**
 * Classe représentant la fin d'un déplacement pour un robot.
 * Cette classe gère la mise à jour de la position du robot sur la carte
 * et redessine les éléments graphiques associés.
 */
public class FinDeplacement extends Evenement {

    /**
     * Le robot qui a terminé son déplacement.
     */
    private final Robot robot;

    /**
     * La nouvelle case où se trouve le robot après son déplacement.
     */
    private final Case nouvelleCase;

    /**
     * Constructeur pour initialiser un événement de fin de déplacement.
     *
     * @param date La date à laquelle l'événement se produit.
     * @param robot Le robot ayant effectué le déplacement.
     * @param nouvelleCase La case de destination du robot.
     */
    FinDeplacement(long date, Robot robot, Case nouvelleCase) {
        super(date);
        this.robot = robot;
        this.nouvelleCase = nouvelleCase;
        this.robot.setOccupe(false); // Le robot n'est plus occupé après le déplacement.
    }

    /**
     * Exécute l'événement de fin de déplacement.
     * Met à jour la position du robot, gère le dessin de la carte, des incendies, et des robots.
     */
    public void execute() {
        Case caseRobot = this.robot.getCaseRobot();
        this.robot.deplaceRobot(this.nouvelleCase);

        ArrayList<Incendie> incendies = DonneesSimulation.getListeIncendies();
        boolean dessinerRobot = false;
        boolean dessinerIncendie = false;

        // Vérifie si un incendie est présent sur l'ancienne case du robot.
        for (Incendie incendie : incendies) {
            if (incendie.getCaseFeu() == caseRobot) {
                drawCase(caseRobot);
                drawIncendie(incendie);
                dessinerIncendie = true;
                break;
            }
        }

        // Vérifie si un autre robot est présent sur l'ancienne case.
        ArrayList<Robot> robots = DonneesSimulation.getListeRobots();
        for (Robot robot : robots) {
            if (robot.getCaseRobot() == caseRobot) {
                if (!dessinerIncendie) {
                    drawCase(caseRobot);
                }
                drawRobot(robot);
                dessinerRobot = true;
                break;
            }
        }

        // Si aucun incendie ni robot n'est présent, redessine simplement la case.
        if (!dessinerRobot && !dessinerIncendie) {
            drawCase(caseRobot);
        }

        // Dessine le robot à sa nouvelle position.
        drawRobot(this.robot);
    }
}
