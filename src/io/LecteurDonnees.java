package io;

import simulation.DonneesSimulation;
import objets.*;
import objets.Case.natureTerrain;
import objets.Robot.typeRobot;

import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;

import static simulation.DonneesSimulation.getCarte;

/**
 * Classe permettant de lire les données depuis un fichier au format spécifié.
 * Cette classe permet de construire une instance de {@link DonneesSimulation}
 * en lisant les informations de la carte, des incendies et des robots.
 */
public class LecteurDonnees {

    private static Scanner scanner;

    /**
     * Constructeur privé pour empêcher l'instanciation en dehors de la classe.
     *
     * @param fichierDonnees Le fichier contenant les données.
     * @throws FileNotFoundException Si le fichier n'est pas trouvé.
     */
    private LecteurDonnees(String fichierDonnees) throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }

    /**
     * Lit les données depuis le fichier et crée une instance de {@link DonneesSimulation}.
     *
     * @param fichierDonnees Le fichier contenant les données.
     * @return Une instance de {@link DonneesSimulation} contenant toutes les données lues.
     * @throws FileNotFoundException Si le fichier n'existe pas.
     * @throws DataFormatException   Si les données sont mal formatées.
     */
    public static DonneesSimulation creeDonnees(String fichierDonnees)
            throws FileNotFoundException, DataFormatException {
        DonneesSimulation data = new DonneesSimulation();
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);

        Carte initCarte = lecteur.creeCarte(data);
        data.setCarte(initCarte);
        lecteur.creeIncendies(data);
        lecteur.creerRobots(data);
        scanner.close();

        return data;
    }

    /**
     * Crée une carte en lisant les données correspondantes depuis le fichier.
     *
     * @param data Une instance de {@link DonneesSimulation} où enregistrer les données.
     * @return Une instance de {@link Carte}.
     * @throws DataFormatException Si le format des données est incorrect.
     */
    private Carte creeCarte(DonneesSimulation data) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();

            Carte newMap = new Carte(nbLignes, nbColonnes, tailleCases);

            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    newMap.setCase(lig, col, creeCase(lig, col, data));
                }
            }

            return newMap;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. Attendu : nbLignes nbColonnes tailleCases.");
        }
    }

    /**
     * Crée une case en lisant les données correspondantes depuis le fichier.
     *
     * @param lig  La ligne de la case.
     * @param col  La colonne de la case.
     * @param data Une instance de {@link DonneesSimulation} où enregistrer les données.
     * @return Une instance de {@link Case}.
     * @throws DataFormatException Si le format des données est incorrect.
     */
    private Case creeCase(int lig, int col, DonneesSimulation data) throws DataFormatException {
        ignorerCommentaires();
        try {
            String chaineNature = scanner.next();
            natureTerrain nature = natureTerrain.valueOf(chaineNature);
            Case nouvelleCase = new Case(lig, col, nature);

            if (nature == natureTerrain.EAU) {
                data.addEau(nouvelleCase);
            }

            return nouvelleCase;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format de case invalide. Attendu : natureTerrain.");
        }
    }

    /**
     * Crée les incendies en lisant les données correspondantes depuis le fichier.
     *
     * @param data Une instance de {@link DonneesSimulation} où enregistrer les données.
     * @throws DataFormatException Si le format des données est incorrect.
     */
    private void creeIncendies(DonneesSimulation data) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            for (int i = 0; i < nbIncendies; i++) {
                int lig = scanner.nextInt();
                int col = scanner.nextInt();
                int intensite = scanner.nextInt();

                if (intensite <= 0) {
                    throw new DataFormatException("Intensité d'incendie invalide. Elle doit être > 0.");
                }

                Case caseFeu = getCarte().getCase(lig, col);
                data.addIncendie(new Incendie(caseFeu, intensite));
            }
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide pour les incendies.");
        }
    }

    /**
     * Crée les robots en lisant les données correspondantes depuis le fichier.
     *
     * @param data Une instance de {@link DonneesSimulation} où enregistrer les données.
     * @throws DataFormatException Si le format des données est incorrect.
     */
    private void creerRobots(DonneesSimulation data) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            for (int i = 0; i < nbRobots; i++) {
                creerRobot(i, data);
            }
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide pour les robots.");
        }
    }

    /**
     * Crée un robot en lisant ses données depuis le fichier.
     *
     * @param i    L'index du robot.
     * @param data Une instance de {@link DonneesSimulation} où enregistrer les données.
     * @throws DataFormatException Si le format des données est incorrect.
     */
    private void creerRobot(int i, DonneesSimulation data) throws DataFormatException {
        ignorerCommentaires();
        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            String type = scanner.next();

            String s = scanner.findInLine("(\\d+)");
            Robot robot;

            if (s == null) {
                robot = creerTypeRobot(type, lig, col, data);
            } else {
                int vitesse = Integer.parseInt(s);
                robot = creerTypeRobot(type, lig, col, data, vitesse);
            }

            data.addRobot(robot);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide pour un robot.");
        }
    }

    /**
     * Ignore les lignes de commentaires commençant par '#'.
     */
    private void ignorerCommentaires() {
        while (scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Crée un robot sans vitesse personnalisée.
     *
     * @param type Le type du robot.
     * @param lig  La ligne où se trouve le robot.
     * @param col  La colonne où se trouve le robot.
     * @param data Une instance de {@link DonneesSimulation}.
     * @return Une instance de {@link Robot}.
     */
    private Robot creerTypeRobot(String type, int lig, int col, DonneesSimulation data) {
        typeRobot typerobot = typeRobot.valueOf(type);
        switch (typerobot) {
            case DRONE:
                return new Drone(getCarte().getCase(lig, col));
            case ROUES:
                return new RobotARoue(getCarte().getCase(lig, col));
            case PATTES:
                return new RobotAPattes(getCarte().getCase(lig, col));
            case CHENILLES:
                return new RobotAChenilles(getCarte().getCase(lig, col));
            default:
                throw new AssertionError();
        }
    }

    /**
     * Crée un robot avec une vitesse personnalisée.
     *
     * @param type    Le type du robot.
     * @param lig     La ligne où se trouve le robot.
     * @param col     La colonne où se trouve le robot.
     * @param data    Une instance de {@link DonneesSimulation}.
     * @param vitesse La vitesse personnalisée du robot.
     * @return Une instance de {@link Robot}.
     */
    private Robot creerTypeRobot(String type, int lig, int col, DonneesSimulation data, double vitesse) {
        typeRobot typerobot = typeRobot.valueOf(type);
        switch (typerobot) {
            case DRONE:
                return new Drone(getCarte().getCase(lig, col), vitesse);
            case ROUES:
                return new RobotARoue(getCarte().getCase(lig, col), vitesse);
            case PATTES:
                return new RobotAPattes(getCarte().getCase(lig, col));
            case CHENILLES:
                return new RobotAChenilles(getCarte().getCase(lig, col), vitesse);
            default:
                throw new AssertionError();
        }
    }
}
