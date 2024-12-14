package simulation;

import evenements.Evenement;
import gui.*;
import objets.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.zip.DataFormatException;

import static io.LecteurDonnees.creeDonnees;
import static objets.Carte.drawCarte;

/**
 * Classe responsable de la simulation des événements et du contrôle global.
 * Gère l'interface graphique, les données de simulation, et les événements planifiés.
 */
public class Simulateur implements Simulable {
    private static GUISimulator gui; // Interface graphique de la simulation
    private static Long dateSimulation; // Date actuelle de la simulation
    private static Map<Long, LinkedList<Evenement>> EVTS; // Structure de données pour gérer les événements par date
    private static int tailleCase; // Taille d'une case sur la carte
    private String filename; // Nom du fichier utilisé pour charger la simulation
    private String attribution; // Type d'attribution utilisé par le chef pompier

    /**
     * Retourne la date actuelle de la simulation.
     *
     * @return La date actuelle sous forme de long.
     */
    public static long getDateSimulation() {
        return dateSimulation;
    }

    /**
     * Méthode appelée pour avancer d'un pas de simulation.
     * Exécute les événements planifiés pour la date actuelle et attribue les incendies
     * selon la stratégie sélectionnée.
     */
    @Override
    public void next() {
        if (!simulationTerminee()) {
            LinkedList<Evenement> events = EVTS.get(dateSimulation);
            if (events == null) {
                incrementeDate();
            } else {
                for (Evenement e : events) {
                    e.execute();
                }
                EVTS.remove(dateSimulation);
                if(this.attribution != null) {
                    switch (this.attribution) {
                        case "simple":
                            ChefPompier.AttributionSimple(DonneesSimulation.getCarte());
                            break;
                        case "avancee":
                            ChefPompier.AttributionAvancee(DonneesSimulation.getCarte());
                            break;
                        case "reflechie":
                            ChefPompier.AttributionReflechie(DonneesSimulation.getCarte());
                            break;
                        default:
                            System.out.println("Veuillez entrer l'une des trois attributions possibles : simple, avancee ou reflechie");
                            break;
                    }
                }
                incrementeDate();
            }
        }

        if (DonneesSimulation.getListeIncendies().isEmpty()) {
            String imagePath = "Images/Gagne.png";
            gui.reset();
            gui.addGraphicalElement(new ImageElement(0, 0, imagePath, 800, 800, null));
        }
    }

    /**
     * Retourne le nom du fichier utilisé pour charger la simulation.
     *
     * @return Le nom du fichier de simulation.
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * Constructeur du simulateur.
     * Initialise la simulation à partir d'un fichier, configure l'interface graphique,
     * et dessine la carte.
     *
     * @param gui      Interface graphique utilisée pour la simulation.
     * @param file     Nom du fichier de simulation.
     * @param attrib   Type d'attribution choisi pour le chef pompier.
     */
    public Simulateur(GUISimulator gui, String file, String attrib) {
        Simulateur.gui = gui;
        this.filename = file;
        this.attribution = attrib;
        dateSimulation = 0L;
        gui.setSimulable(this);
        try {
            DonneesSimulation data = creeDonnees(this.filename);
            draw(data);
        } catch (FileNotFoundException | DataFormatException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Charge les données de simulation à partir d'un fichier.
     *
     * @param filename Le fichier contenant les données de simulation.
     * @return Une instance de DonneesSimulation.
     * @throws DataFormatException En cas d'erreur de format dans le fichier.
     * @throws FileNotFoundException Si le fichier n'est pas trouvé.
     */
    public static DonneesSimulation getDonneesSimulation(String filename) throws DataFormatException, FileNotFoundException {
        return creeDonnees(filename);
    }

    /**
     * Réinitialise la simulation à son état initial.
     */
    @Override
    public void restart() {
        try {
            dateSimulation = 0L;
            EVTS = null;
            DonneesSimulation.setListeEau(null);
            DonneesSimulation.setListeIncendies(null);
            DonneesSimulation.setListeRobots(null);
            DonneesSimulation data = creeDonnees(this.filename);
            draw(data);
            switch (this.attribution) {
                case "simple":
                    ChefPompier.AttributionSimple(DonneesSimulation.getCarte());
                    break;
                case "avancee":
                    ChefPompier.AttributionAvancee(DonneesSimulation.getCarte());
                    break;
                case "reflechie":
                    ChefPompier.AttributionReflechie(DonneesSimulation.getCarte());
                    break;
                default:
                    break;
            }
        } catch (FileNotFoundException | DataFormatException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Dessine les éléments de la carte et les données associées sur l'interface graphique.
     *
     * @param data Les données de simulation à dessiner.
     */
    public void draw(DonneesSimulation data) {
        gui.reset();
        drawCarte(data);
    }

    /**
     * Ajoute un événement à la liste des événements planifiés.
     *
     * @param evenement L'événement à ajouter.
     */
    public static void ajouteEvenement(Evenement evenement) {
        long date = evenement.getDate();
        LinkedList<Evenement> events;
        if (EVTS == null) {
            EVTS = new HashMap<>();
            events = new LinkedList<>();
            events.add(evenement);
            EVTS.put(date, events);
        } else if (!EVTS.containsKey(date)) {
            events = new LinkedList<>();
            events.add(evenement);
            EVTS.put(date, events);
        } else {
            events = EVTS.get(date);
            events.add(evenement);
        }
    }

    /**
     * Incrémente la date actuelle de la simulation.
     */
    public void incrementeDate() {
        dateSimulation++;
    }

    /**
     * Vérifie si la simulation est terminée.
     *
     * @return true si la simulation est terminée, false sinon.
     */
    public boolean simulationTerminee() {
        return EVTS == null || EVTS.isEmpty();
    }

    /**
     * Définit la taille des cases sur la carte.
     *
     * @param taille La taille des cases.
     */
    public static void setTailleCase(int taille) {
        tailleCase = taille;
    }

    /**
     * Retourne la taille des cases sur la carte.
     *
     * @return La taille des cases.
     */
    public static int getTailleCase() {
        return tailleCase;
    }

    /**
     * Retourne l'interface graphique utilisée par la simulation.
     *
     * @return L'instance de GUISimulator.
     */
    public static GUISimulator getGui() {
        return gui;
    }
}
