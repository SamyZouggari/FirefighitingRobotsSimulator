package evenements;

/**
 * Classe abstraite représentant un événement dans la simulation.
 * Chaque événement a une date d'exécution et doit définir une méthode d'exécution spécifique.
 */
public abstract class Evenement {

    /**
     * La date à laquelle l'événement doit être exécuté.
     */
    private long date;

    /**
     * Constructeur pour initialiser un événement avec une date donnée.
     *
     * @param date La date à laquelle l'événement doit être exécuté.
     */
    Evenement(long date) {
        this.date = date;
    }

    /**
     * Retourne la date de l'événement.
     *
     * @return La date de l'événement.
     */
    public long getDate() {
        return this.date;
    }

    /**
     * Méthode abstraite à implémenter par les sous-classes pour définir
     * le comportement spécifique de l'événement lors de son exécution.
     */
    public abstract void execute();
}
