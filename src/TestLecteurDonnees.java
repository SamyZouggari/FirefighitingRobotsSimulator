
import io.LecteurDonnees;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class TestLecteurDonnees {

    public static void main(String[] args) {
//        if (args.length < 1) {
//            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
//            System.exit(1);
//        }

        try {
            LecteurDonnees.creeDonnees("cartes/carteSujet.map");
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + "cartes/carteSujet.map" + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + "cartes/carteSujet.map" + " invalide: " + e.getMessage());
        }
    }

}

