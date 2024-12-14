package objets;

import A_etoile.Astar;
import evenements.DebutDeplacement;
import simulation.DonneesSimulation;
import simulation.Simulateur;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import static objets.Carte.getVoisin;
import static objets.Case.natureTerrain;
import static simulation.DonneesSimulation.getListeEau;
import static simulation.DonneesSimulation.getListeIncendies;
import static objets.Robot.typeRobot;
import objets.Carte.Direction;

/**
 * Classe représentant le chef pompier, responsable de la gestion et de l'attribution
 * des robots pour éteindre les incendies et gérer les déplacements vers les sources d'eau.
 */
public class ChefPompier {

    /**
     * Attribue les robots aux incendies de manière simple, en assignant le premier robot disponible
     * à un incendie non attribué.
     *
     * @param carte La carte sur laquelle opère le chef pompier.
     */
    static public void AttributionSimple(Carte carte) {
        ArrayList<Incendie> incendies = DonneesSimulation.getListeIncendies();
        ArrayList<Robot> robots = DonneesSimulation.getListeRobots();
        for (Incendie incendie : incendies) {
            if (!incendie.isAttribue()) {
                for (Robot robot : robots) {
                    if (!robot.isOccupe()) {
                        LinkedList<Carte.Direction> chemin = Astar.AStarSearch(carte, robot, incendie.getCaseFeu());
                        if (chemin != null) {
                            robot.setDeplacement(chemin);
                            incendie.setAttribue(true);
                            robot.setOccupe(true);
                            Simulateur.ajouteEvenement(new DebutDeplacement(Simulateur.getDateSimulation() + 1, robot, true, false));
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Trouve le chemin le plus court vers une source d'eau pour un robot donné.
     *
     * @param carte La carte sur laquelle le robot se déplace.
     * @param robot Le robot cherchant une source d'eau.
     * @return Une liste des directions vers la source d'eau la plus proche.
     */
    public static LinkedList<Direction> EauPlusProche(Carte carte, Robot robot) {
        ArrayList<Case> allEau = getListeEau();
        int dist = Integer.MAX_VALUE;
        int tailleRef = Integer.MAX_VALUE;
        LinkedList<Direction> plusCourtCheminEau = new LinkedList<>();

        for (Case eau : allEau) {
            if (eau.getNature() == natureTerrain.EAU) {
                LinkedList<Direction> cheminEau = new LinkedList<>();
                if (robot.getTypeRobot() == typeRobot.DRONE) {
                    cheminEau = Astar.AStarSearch(carte, robot, eau);
                } else {
                    for (Direction d : Direction.values()) {
                        Case voisin = getVoisin(eau, d);
                        if (voisin == null || voisin.getNature() == natureTerrain.EAU) {
                            continue;
                        }
                        LinkedList<Carte.Direction> testCheminEau = Astar.AStarSearch(carte, robot, voisin);
                        if (testCheminEau != null && !testCheminEau.isEmpty() && testCheminEau.size() < tailleRef) {
                            tailleRef = testCheminEau.size();
                            cheminEau = testCheminEau;
                        }
                    }
                }

                if (!cheminEau.isEmpty() && cheminEau.size() < dist) {
                    dist = cheminEau.size();
                    plusCourtCheminEau = cheminEau;
                }
            }
        }
        return plusCourtCheminEau;
    }

    /**
     * Attribue les robots aux incendies en prenant en compte le temps nécessaire pour atteindre
     * chaque incendie et choisit le robot le plus adapté.
     *
     * @param carte La carte sur laquelle opère le chef pompier.
     */
    static public void AttributionAvancee(Carte carte) {
        ArrayList<Incendie> incendies = DonneesSimulation.getListeIncendies();
        ArrayList<Robot> robots = DonneesSimulation.getListeRobots();
        int tempsMin = Integer.MAX_VALUE;
        LinkedList<Carte.Direction> directionIncendie = new LinkedList<>();
        Robot bonRobot = null;

        for (Incendie incendie : incendies) {
            if (!incendie.isAttribue()) {
                for (Robot robot : robots) {
                    if (!robot.isOccupe() && robot.getVolEau() != 0) {
                        LinkedList<Carte.Direction> plusCourtChemin = Astar.AStarSearch(carte, robot, incendie.getCaseFeu());
                        if (plusCourtChemin == null) {
                            continue;
                        }
                        int temps = robot.tempsDeplacement(robot.getCaseRobot(), incendie.getCaseFeu(), carte, plusCourtChemin);
                        if (temps < tempsMin) {
                            tempsMin = temps;
                            directionIncendie = plusCourtChemin;
                            bonRobot = robot;
                        }
                    }
                }
                if (bonRobot != null) {
                    bonRobot.setDeplacement(directionIncendie);
                    incendie.setAttribue(true);
                    bonRobot.setOccupe(true);
                    Simulateur.ajouteEvenement(new DebutDeplacement(Simulateur.getDateSimulation() + 1, bonRobot, true, false));
                    break;
                }
            }
        }
    }

    /**
     * Trouve le chemin vers l'incendie le plus proche pour un robot donné.
     *
     * @param carte La carte sur laquelle le robot se déplace.
     * @param robot Le robot cherchant un incendie.
     * @return Une liste des directions vers l'incendie le plus proche.
     */
    public static LinkedList<Carte.Direction> TrouveFeuPlusProche(Carte carte, Robot robot) {
        ArrayList<Incendie> allFeu = getListeIncendies();
        Incendie feuOccupe = allFeu.get(0);
        int dist = Integer.MAX_VALUE;
        LinkedList<Carte.Direction> plusCourtCheminFeu = new LinkedList<>();

        for (Incendie feu : allFeu) {
            LinkedList<Carte.Direction> testChemin = Astar.AStarSearch(carte, robot, feu.getCaseFeu());
            if (testChemin != null && testChemin.size() < dist) {
                dist = testChemin.size();
                plusCourtCheminFeu = testChemin;
                feuOccupe = feu;
            }
        }
        feuOccupe.setAttribue(true);
        return plusCourtCheminFeu;
    }

    /**
     * Méthode d'attribution avancée utilisant une file de priorité pour organiser les robots
     * en fonction de leur vitesse et gérer les incendies de manière optimisée.
     *
     * @param carte La carte sur laquelle opère le chef pompier.
     */
    static public void AttributionReflechie(Carte carte) {
        ArrayList<Incendie> incendies = DonneesSimulation.getListeIncendies();
        ArrayList<Robot> robots = DonneesSimulation.getListeRobots();
        PriorityQueue<Robot> SortedRobots = new PriorityQueue<>(Comparator.comparingDouble(Robot::getVitesse));
        SortedRobots.addAll(robots);
        LinkedList<Carte.Direction> feuPlusProche;

        int i = 0;
        for (Robot rob : SortedRobots) {
            if (!rob.isOccupe() && rob.getVolEau() != 0) {
                if (i >= incendies.size()) {
                    break;
                }
                Incendie feu = incendies.get(i);
                if (!feu.isAttribue()) {
                    feuPlusProche = TrouveFeuPlusProche(carte, rob);
                    if (!feuPlusProche.isEmpty()) {
                        rob.setDeplacement(feuPlusProche);
                        rob.setOccupe(true);
                        Simulateur.ajouteEvenement(new DebutDeplacement(Simulateur.getDateSimulation() + 1, rob, true, false));
                        break;
                    }
                } else {
                    i++;
                }
            }
        }
        for (Robot rob : SortedRobots) {
            if (!rob.isOccupe() && rob.getVolEau() != 0) {
                for (Incendie feu : incendies) {
                    if (feu.isAttribue()) { // Attribue un robot aux incendies déjà attribués pour renforcer l'intervention
                        feuPlusProche = TrouveFeuPlusProche(carte, rob);
                        if (!feuPlusProche.isEmpty()) {
                            rob.setDeplacement(feuPlusProche);
                            rob.setOccupe(true);
                            Simulateur.ajouteEvenement(new DebutDeplacement(Simulateur.getDateSimulation() + 1, rob, true, false));
                            break; // Passe au robot suivant après attribution
                        }
                    }
                }
            }
        }
    }
}
