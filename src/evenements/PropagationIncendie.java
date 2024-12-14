package evenements;

import objets.Carte;
import objets.Case;
import objets.Incendie;
import simulation.DonneesSimulation;

import java.util.ArrayList;
import java.util.Random;

/**
 * Classe représentant la propagation d'un incendie sur une carte.
 * Si les conditions sont remplies, un incendie peut se propager à une case voisine.
 */
public class PropagationIncendie extends Evenement {

    /**
     * La date de l'événement.
     */
    private long date;

    /**
     * Liste des directions possibles pour la propagation de l'incendie.
     */
    private final ArrayList<Carte.Direction> Directions;

    /**
     * Constructeur pour initialiser l'événement de propagation d'incendie.
     *
     * @param date La date à laquelle l'événement de propagation se produit.
     */
    public PropagationIncendie(long date) {
        super(date);
        Directions = new ArrayList<>();
        Directions.add(Carte.Direction.NORD);
        Directions.add(Carte.Direction.SUD);
        Directions.add(Carte.Direction.EST);
        Directions.add(Carte.Direction.OUEST);
    }

    /**
     * Exécute l'événement de propagation d'incendie.
     * Vérifie pour chaque incendie si les conditions de propagation sont remplies,
     * puis crée un nouvel incendie sur une case voisine si possible.
     */
    @Override
    public void execute() {
        Random nb = new Random();
        long n;
        Carte.Direction direction;

        // Parcourt tous les incendies de la simulation.
        for (Incendie incendie : DonneesSimulation.getListeIncendies()) {
            // Vérifie si l'incendie est suffisamment important pour se propager.
            if (incendie.getNbEau() > 1000) {
                n = Math.round(Math.random() * 1000);

                // Si la condition aléatoire est remplie, tente de propager l'incendie.
                if (n == 1) {
                    int m = (int) (Math.random() * 3);
                    direction = Directions.get(m);

                    // Récupère la case voisine dans la direction sélectionnée.
                    Case case_propagation = Carte.getVoisin(incendie.getCaseFeu(), direction);

                    // Si la case voisine est valide, crée un nouvel incendie.
                    if (case_propagation != null) {
                        Incendie nouvelle_Incendie = new Incendie(case_propagation, incendie.getNbEau() / 10);
                        DonneesSimulation.getListeIncendies().add(nouvelle_Incendie);
                    }
                }
            }
        }
    }
}
