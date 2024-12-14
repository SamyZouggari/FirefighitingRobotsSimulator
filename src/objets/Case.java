package objets;

import gui.GUISimulator;
import gui.ImageElement;
import simulation.Simulateur;

/**
 * Classe représentant une case sur la carte.
 * Une case possède une position (ligne, colonne) et une nature de terrain.
 */
public class Case {
    /**
     * Ligne de la case sur la carte.
     */
    private int ligne;

    /**
     * Colonne de la case sur la carte.
     */
    private int colonne;

    /**
     * Nature du terrain de la case.
     */
    private natureTerrain nature;

    /**
     * Enumération des différents types de terrain possibles pour une case.
     */
    public enum natureTerrain {
        EAU, FORET, ROCHE, TERRAIN_LIBRE, HABITAT; // Types de terrain disponibles
    }

    /**
     * Constructeur de la classe Case.
     *
     * @param ligne  Ligne de la case.
     * @param colonne Colonne de la case.
     * @param nature  Nature du terrain de la case.
     */
    public Case(int ligne, int colonne, natureTerrain nature) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.nature = nature;
    }

    /**
     * Définit la ligne de la case.
     *
     * @param ligne La ligne à assigner à la case.
     */
    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    /**
     * Définit la colonne de la case.
     *
     * @param colonne La colonne à assigner à la case.
     */
    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    /**
     * Définit la nature du terrain de la case.
     *
     * @param nature La nature à assigner à la case.
     */
    public void setNature(natureTerrain nature) {
        this.nature = nature;
    }

    /**
     * Retourne la ligne de la case.
     *
     * @return La ligne de la case.
     */
    public int getLigne() {
        return this.ligne;
    }

    /**
     * Retourne la colonne de la case.
     *
     * @return La colonne de la case.
     */
    public int getColonne() {
        return this.colonne;
    }

    /**
     * Retourne la nature du terrain de la case.
     *
     * @return La nature du terrain de la case.
     */
    public natureTerrain getNature() {
        return this.nature;
    }

    /**
     * Dessine une case sur l'interface graphique.
     * L'image affichée dépend de la nature du terrain de la case.
     *
     * @param caseDraw La case à dessiner.
     */
    public static void drawCase(Case caseDraw) {
        int taille = Simulateur.getTailleCase();
        int colonne = caseDraw.getColonne();
        int ligne = caseDraw.getLigne();
        natureTerrain natureCase = caseDraw.getNature();
        String imagePath = "Images/" + natureCase + ".png";
        GUISimulator gui = Simulateur.getGui();
        gui.addGraphicalElement(new ImageElement(colonne * taille, ligne * taille, imagePath, taille, taille, null));
    }

    /**
     * Retourne une représentation textuelle de la case.
     * Format : "(ligne, colonne)".
     *
     * @return Une chaîne de caractères représentant les coordonnées de la case.
     */
    @Override
    public String toString() {
        return "( " + this.getLigne() + " , " + this.getColonne() + " ) ";
    }
}
