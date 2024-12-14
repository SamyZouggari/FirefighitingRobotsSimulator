package A_etoile;

import objets.Case;
import java.lang.Math;
import java.util.*;

import objets.Carte;
import objets.Robot;
import objets.Case.*;
import objets.Carte.Direction;
import objets.Robot.typeRobot;

/**
 * Classe implémentant l'algorithme A* (A étoile) pour trouver le plus court chemin
 * entre deux cases sur une carte, en tenant compte des contraintes du robot.
 */
public class Astar {

    /**
     * Calcule la distance euclidienne entre deux cases.
     *
     * @param caseDebut La case de départ.
     * @param caseFin La case d'arrivée.
     * @return La distance euclidienne entre caseDebut et caseFin.
     */
    static public double EuclidienneDist(Case caseDebut, Case caseFin) {
        return Math.sqrt(Math.pow(Math.abs(caseFin.getLigne() - caseDebut.getLigne()), 2) +
                Math.pow(Math.abs(caseFin.getColonne() - caseDebut.getColonne()), 2));
    }

    /**
     * Classe interne représentant une cellule utilisée dans l'algorithme A*.
     */
    public static class Cell {
        /**
         * Cellule parente, pour reconstruire le chemin.
         */
        public Cell parent;

        /**
         * Case associée à la cellule.
         */
        public Case courant;

        /**
         * Coût pour atteindre cette cellule depuis le point de départ.
         */
        public double g;

        /**
         * Estimation heuristique du coût pour atteindre la destination.
         */
        public double h;

        /**
         * Somme de g et h, utilisée pour trier les cellules.
         */
        public double f;

        /**
         * Constructeur pour initialiser une cellule.
         *
         * @param parent La cellule parente.
         * @param courant La case associée.
         * @param g Coût pour atteindre cette cellule.
         * @param h Estimation heuristique pour atteindre la destination.
         * @param f Somme des coûts g et h.
         */
        public Cell(Cell parent, Case courant, double g, double h, double f) {
            this.parent = parent;
            this.courant = courant;
            this.g = g;
            this.h = h;
            this.f = f;
        }

        /**
         * Vérifie si deux cellules sont égales, en comparant leurs cases associées.
         *
         * @param o L'objet à comparer.
         * @return true si les cellules représentent la même case, sinon false.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return this.courant.getLigne() == cell.courant.getLigne() &&
                    this.courant.getColonne() == cell.courant.getColonne();
        }
    }

    /**
     * Vérifie si une case est la destination.
     *
     * @param position La position actuelle.
     * @param destination La destination.
     * @return true si la position correspond à la destination, sinon false.
     */
    static public boolean EstArrive(Case position, Case destination) {
        return position.getLigne() == destination.getLigne() &&
                position.getColonne() == destination.getColonne();
    }

    /**
     * Recherche le plus court chemin en utilisant l'algorithme A*.
     *
     * @param carte La carte sur laquelle naviguer.
     * @param robot Le robot effectuant la navigation.
     * @param destination La case de destination.
     * @return Une liste de directions représentant le chemin à suivre, ou null si aucun chemin n'est trouvé.
     */
    static public LinkedList<Direction> AStarSearch(Carte carte, Robot robot, Case destination) {
        if (!robot.caseAccessible(destination)) {
            return null;
        }

        // Initialisation des directions cardinales.
        ArrayList<Direction> cardinaux = new ArrayList<>();
        cardinaux.add(Direction.NORD);
        cardinaux.add(Direction.SUD);
        cardinaux.add(Direction.EST);
        cardinaux.add(Direction.OUEST);

        // Initialisation des structures pour l'algorithme.
        Case depart = robot.getCaseRobot();
        Cell initCell = new Cell(null, depart, 0, EuclidienneDist(depart, destination), 0);
        initCell.f = initCell.g + initCell.h;
        ArrayList<Case> listeFermee = new ArrayList<>();
        PriorityQueue<Cell> listeOuverte = new PriorityQueue<>(Comparator.comparingDouble(Cell -> Cell.f));

        listeOuverte.add(initCell);

        while (!listeOuverte.isEmpty()) {
            Cell courante = listeOuverte.poll();

            if (EstArrive(courante.courant, destination)) {
                return convertiDirection(reconstruieChemin(courante));
            }

            listeFermee.add(courante.courant);

            ArrayList<Case> voisins = carte.allVoisins(courante.courant, robot);
            for (Case vois : voisins) {
                Cell newVoisin = new Cell(null, vois, Integer.MAX_VALUE, 0, 0);
                if (listeFermee.contains(vois)) {
                    continue;
                }

                double tentativeG = courante.g + poidsDeplacement(courante.courant, robot);
                if (tentativeG < newVoisin.g) {
                    newVoisin.parent = courante;
                    newVoisin.g = tentativeG;
                    newVoisin.h = EuclidienneDist(newVoisin.courant, destination);
                    newVoisin.f = newVoisin.g + newVoisin.h;
                    if (!listeOuverte.contains(newVoisin)) {
                        listeOuverte.add(newVoisin);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Reconstruit le chemin à partir de la cellule finale.
     *
     * @param courant La cellule finale.
     * @return Une liste chaînée des cases composant le chemin.
     */
    static public LinkedList<Case> reconstruieChemin(Cell courant) {
        LinkedList<Case> chemin = new LinkedList<>();
        while (courant != null) {
            chemin.add(courant.courant);
            courant = courant.parent;
        }
        Collections.reverse(chemin);
        return chemin;
    }

    /**
     * Convertit une liste chaînée de cases en une liste de directions.
     *
     * @param chemin Le chemin en termes de cases.
     * @return Le chemin en termes de directions.
     */
    static public LinkedList<Direction> convertiDirection(LinkedList<Case> chemin) {
        LinkedList<Direction> plusCourtChemin = new LinkedList<>();

        Case cour = chemin.get(0);
        chemin.removeFirst();
        for (Case c : chemin) {
            if (c.getColonne() == cour.getColonne() + 1) {
                plusCourtChemin.addLast(Direction.EST);
            } else if (c.getColonne() == cour.getColonne() - 1) {
                plusCourtChemin.addLast(Direction.OUEST);
            } else if (c.getLigne() == cour.getLigne() + 1) {
                plusCourtChemin.addLast(Direction.SUD);
            } else if (c.getLigne() == cour.getLigne() - 1) {
                plusCourtChemin.addLast(Direction.NORD);
            }
            cour = c;
        }
        return plusCourtChemin;
    }

    /**
     * Calcule le coût de déplacement en fonction du terrain et du type de robot.
     *
     * @param caseCourante La case actuelle.
     * @param robot Le robot effectuant le déplacement.
     * @return Le coût de déplacement.
     */
    static public int poidsDeplacement(Case caseCourante, Robot robot) {
        Case.natureTerrain terrain = caseCourante.getNature();
        if (robot.getTypeRobot() == typeRobot.PATTES) {
            if (terrain == natureTerrain.ROCHE) {
                return 3;
            }
        } else if (robot.getTypeRobot() == typeRobot.CHENILLES) {
            if (terrain == natureTerrain.FORET) {
                return 2;
            }
        }
        return 1;
    }
}
