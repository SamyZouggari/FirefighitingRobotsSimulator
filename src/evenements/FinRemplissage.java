package evenements;

import objets.Robot;

/**
 * Classe représentant la fin d'un événement de remplissage pour un robot.
 * Une fois l'événement exécuté, le réservoir du robot est rempli, et il est libéré pour d'autres tâches.
 */
public class FinRemplissage extends Evenement {

    /**
     * Le robot ayant effectué l'opération de remplissage.
     */
    private final Robot robot;

    /**
     * Constructeur pour initialiser un événement de fin de remplissage.
     *
     * @param date La date de fin du remplissage.
     * @param robot Le robot ayant rempli son réservoir.
     */
    FinRemplissage(long date, Robot robot) {
        super(date);
        this.robot = robot;
    }

    /**
     * Exécute l'événement de fin de remplissage.
     * Remplit le réservoir du robot et met à jour son état pour indiquer qu'il n'est plus occupé.
     */
    @Override
    public void execute() {
        robot.remplirReservoir(); // Remplit le réservoir du robot.
        robot.setOccupe(false);   // Met à jour l'état du robot pour indiquer qu'il est disponible.
    }
}
