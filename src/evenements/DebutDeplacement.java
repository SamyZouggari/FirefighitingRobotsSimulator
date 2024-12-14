package evenements;

import objets.Carte.Direction;

import java.util.LinkedList;
import objets.Robot;
import objets.Case;
import simulation.Simulateur;

import static objets.Carte.getVoisin;

/**
 * Classe représentant le début d'un déplacement pour un robot dans une simulation.
 * Permet de gérer les différents types de déplacements et d'enchaîner des événements
 * comme des interventions ou des remplissages après le déplacement.
 */
public class DebutDeplacement extends Evenement {

    /**
     * Le robot concerné par l'événement.
     */
    private final Robot robot;

    /**
     * La direction du déplacement, si applicable.
     */
    private final Direction direction;

    /**
     * Indique si une intervention doit être lancée après le déplacement.
     */
    private final boolean InterventionApresDeplacement;

    /**
     * Indique si un remplissage doit être lancé après le déplacement.
     */
    private final boolean RemplissageApresDeplacement;

    /**
     * Constructeur pour un déplacement sans direction précise.
     *
     * @param date La date de début de l'événement.
     * @param robot Le robot concerné par le déplacement.
     */
    public DebutDeplacement(long date, Robot robot) {
        super(date);
        this.robot = robot;
        this.direction = null;
        this.InterventionApresDeplacement = false;
        this.RemplissageApresDeplacement = false;
        this.robot.setOccupe(true);
    }

    /**
     * Constructeur pour un déplacement dans une direction donnée.
     *
     * @param date La date de début de l'événement.
     * @param robot Le robot concerné par le déplacement.
     * @param direction La direction du déplacement.
     */
    public DebutDeplacement(long date, Robot robot, Direction direction) {
        super(date);
        this.robot = robot;
        this.direction = direction;
        this.InterventionApresDeplacement = false;
        this.RemplissageApresDeplacement = false;
        this.robot.setOccupe(true);
    }

    /**
     * Constructeur pour un déplacement avec des actions postérieures comme
     * une intervention ou un remplissage.
     *
     * @param date La date de début de l'événement.
     * @param robot Le robot concerné par le déplacement.
     * @param InreventionApresDeplacement Indique si une intervention doit être lancée après.
     * @param RemplissageApresDeplacement Indique si un remplissage doit être lancé après.
     */
    public DebutDeplacement(long date, Robot robot, boolean InreventionApresDeplacement, boolean RemplissageApresDeplacement) {
        super(date);
        this.robot = robot;
        this.direction = null;
        this.InterventionApresDeplacement = InreventionApresDeplacement;
        this.RemplissageApresDeplacement = RemplissageApresDeplacement;
        this.robot.setOccupe(true);
    }

    /**
     * Exécute l'événement de début de déplacement. Peut lancer des événements
     * subséquents comme des interventions ou des remplissages.
     *
     * @throws IllegalArgumentException Si le robot sort de la carte.
     */
    public void execute() {
        Case caseCourante = this.robot.getCaseRobot();

        if (direction == null) {
            LinkedList<Direction> deplacement = this.robot.getDeplacement();
            if (deplacement.isEmpty()) {
                double temps = super.getDate() + 1;
                AjouteEvenementApres(temps, caseCourante);
                return;
            }
            double temps = 0;
            while (!deplacement.isEmpty()) {
                caseCourante = getVoisin(caseCourante, deplacement.get(0));
                temps += this.robot.tempsChangement(this.robot.getCaseRobot());
                if (caseCourante == null) {
                    throw new IllegalArgumentException("Le robot sort de la carte !!!");
                }
                Simulateur.ajouteEvenement(new FinDeplacement((super.getDate() + Math.round(temps) + 1), this.robot, caseCourante));
                deplacement.remove();
            }
            temps = super.getDate() + Math.round(temps) + 2;
            AjouteEvenementApres(temps, caseCourante);

        } else {
            caseCourante = getVoisin(caseCourante, this.direction);
            double temps = this.robot.tempsChangement(this.robot.getCaseRobot());
            if (caseCourante == null) {
                throw new IllegalArgumentException("Le robot sort de la carte !!!");
            }
            Simulateur.ajouteEvenement(new FinDeplacement((long) (super.getDate() + Math.round(temps) + 1), this.robot, caseCourante));
            temps = super.getDate() + Math.round(temps) + 2;
            AjouteEvenementApres(temps, caseCourante);
        }
    }

    /**
     * Ajoute un événement subséquent (intervention ou remplissage) après le déplacement.
     *
     * @param temps La date à laquelle l'événement subséquent doit être lancé.
     * @param CaseIncendie La case de destination après le déplacement.
     */
    private void AjouteEvenementApres(double temps, Case CaseIncendie) {
        if (this.InterventionApresDeplacement) {
            Simulateur.ajouteEvenement(new DebutIntervention(Math.round(temps), this.robot, CaseIncendie));
        } else if (this.RemplissageApresDeplacement) {
            Simulateur.ajouteEvenement(new DebutRemplissage(Math.round(temps), this.robot));
        }
    }
}
