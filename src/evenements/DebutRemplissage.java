package evenements;

import simulation.Simulateur;
import objets.Robot;

/**
 * Classe représentant le début d'une opération de remplissage pour un robot.
 * Le remplissage correspond au réapprovisionnement en eau du robot pour qu'il puisse intervenir sur les incendies.
 */
public class DebutRemplissage extends Evenement {

    /**
     * Le robot qui effectue l'opération de remplissage.
     */
    private final Robot robot;

    /**
     * Constructeur pour initialiser l'événement de début de remplissage.
     *
     * @param date La date de début du remplissage.
     * @param robot Le robot qui effectue le remplissage.
     */
    public DebutRemplissage(long date, Robot robot) {
        super(date);
        this.robot = robot;
        this.robot.setOccupe(true);
    }

    /**
     * Exécute l'événement de remplissage.
     * Programme un événement de fin de remplissage après la durée nécessaire pour compléter l'opération.
     */
    public void execute() {
        Simulateur.ajouteEvenement(new FinRemplissage((super.getDate() + robot.getTempsRemplissage()), this.robot));
    }
}
