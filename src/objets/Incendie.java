package objets;

import gui.GUISimulator;
import gui.ImageElement;
import simulation.Simulateur;

import java.util.ArrayList;

import static simulation.Simulateur.getGui;
import static simulation.Simulateur.getTailleCase;

/**
 * Classe représentant un incendie sur une case de la carte.
 * Un incendie a une intensité (exprimée en litres d'eau nécessaires pour l'éteindre)
 * et peut être attribué ou non à un robot pour intervention.
 */
public class Incendie {

        /**
         * La case où se situe l'incendie.
         */
        private Case caseFeu;

        /**
         * Le nombre de litres d'eau nécessaires pour éteindre l'incendie.
         */
        private int nbEau;

        /**
         * Indicateur pour savoir si l'incendie est attribué à un robot.
         */
        private boolean attribue;

        /**
         * Constructeur de la classe Incendie.
         *
         * @param caseFeu La case où se situe l'incendie.
         * @param nbEau   Le nombre de litres d'eau nécessaires pour éteindre l'incendie.
         */
        public Incendie(Case caseFeu, int nbEau) {
                this.caseFeu = caseFeu;
                this.nbEau = nbEau;
                this.attribue = false; // Par défaut, l'incendie n'est pas encore attribué.
        }

        /**
         * Retourne la case où se situe l'incendie.
         *
         * @return La case de l'incendie.
         */
        public Case getCaseFeu() {
                return this.caseFeu;
        }

        /**
         * Retourne le nombre de litres d'eau nécessaires pour éteindre l'incendie.
         *
         * @return Le nombre de litres d'eau.
         */
        public int getNbEau() {
                return this.nbEau;
        }

        /**
         * Modifie le nombre de litres d'eau nécessaires pour éteindre l'incendie.
         *
         * @param nbEau Le nouveau nombre de litres d'eau.
         */
        public void setNbEau(int nbEau) {
                this.nbEau = nbEau;
        }

        /**
         * Indique si l'incendie est attribué à un robot.
         *
         * @return true si l'incendie est attribué, false sinon.
         */
        public boolean isAttribue() {
                return this.attribue;
        }

        /**
         * Définit si l'incendie est attribué à un robot.
         *
         * @param attribue true si un robot est attribué, false sinon.
         */
        public void setAttribue(boolean attribue) {
                this.attribue = attribue;
        }

        /**
         * Dessine une liste d'incendies sur l'interface graphique.
         *
         * @param listeIncendies La liste des incendies à dessiner.
         */
        public static void drawIncendies(ArrayList<Incendie> listeIncendies) {
                for (Incendie incendie : listeIncendies) {
                        drawIncendie(incendie);
                }
        }

        /**
         * Dessine un incendie spécifique sur l'interface graphique.
         *
         * @param incendie L'incendie à dessiner.
         */
        public static void drawIncendie(Incendie incendie) {
                Case caseIncendie = incendie.getCaseFeu();
                int ligne = caseIncendie.getLigne();
                int colonne = caseIncendie.getColonne();
                String imagePath = "Images/feu.png";
                GUISimulator gui = getGui();
                int tailleCase = getTailleCase();
                gui.addGraphicalElement(new ImageElement(colonne * tailleCase, ligne * tailleCase, imagePath, tailleCase, tailleCase, null));
        }
}
