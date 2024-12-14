package evenements;

import objets.Carte;
import objets.Robot;
import objets.Case;
import simulation.DonneesSimulation;
import simulation.Simulateur;
import objets.Incendie;

import static simulation.DonneesSimulation.getListeIncendies;

/**
 * Classe représentant le début d'une intervention pour un robot dans une simulation.
 * Une intervention correspond à l'action de traiter un incendie sur une case spécifique.
 */
public class DebutIntervention extends Evenement {

    /**
     * Le robot concerné par l'intervention.
     */
    private final Robot robot;

    /**
     * L'incendie à traiter.
     */
    private Incendie incendie;

    /**
     * Constructeur pour une intervention avec un incendie spécifié.
     *
     * @param date La date de début de l'intervention.
     * @param robot Le robot effectuant l'intervention.
     * @param incendie L'incendie à traiter.
     */
    public DebutIntervention(long date, Robot robot, Incendie incendie) {
        super(date);
        this.robot = robot;
        this.incendie = incendie;
        this.robot.setOccupe(true);
    }

    /**
     * Constructeur pour une intervention en fonction d'une case, où l'incendie est déterminé automatiquement.
     *
     * @param date La date de début de l'intervention.
     * @param robot Le robot effectuant l'intervention.
     * @param caseIncendie La case où l'incendie doit être traité.
     */
    public DebutIntervention(long date, Robot robot, Case caseIncendie) {
        super(date);
        this.robot = robot;

        // Recherche de l'incendie correspondant à la case donnée.
        for (Incendie i : getListeIncendies()) {
            if (i.getCaseFeu() == caseIncendie) {
                this.incendie = i;
                break;
            }
        }
        this.robot.setOccupe(true);
    }

    /**
     * Exécute l'intervention. Ajoute un événement de fin d'intervention une fois le traitement terminé.
     */
    public void execute() {
        int temps = this.robot.getTempsIntervention();
        Simulateur.ajouteEvenement(new FinIntervention((super.getDate() + temps), this.robot, this.incendie));
    }
}
