import java.io.File;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DockerScenario {

    public static void main(String[] args) {
        System.out.println("Dans cet exemple on crée un dataframe à partir d'un fichier CSV");
        try {
            DataFrame frame = new DataFrame(Paths.get("src", "main", "resources", "exemple_docker.csv").toAbsolutePath().toString());
            System.out.println(frame);

            List<Object> line = Arrays.asList("Tom", "Hanks", "M", 1964, "Americaine");
            System.out.println("On ajoute Tom Hanks");
            frame.ajouterLigne(line);
            System.out.println(frame);


            System.out.println("Extraction des deux premières colonnes");
            System.out.println(frame.select_column(new String[]{ "Nom", "Prenom" }));

            System.out.println("On ne garde que les femmes");
            System.out.println(frame.select("Sexe == F"));

            System.out.println("On ne garde que les personnes nées après 1970");
            System.out.println(frame.select("Annee de naissance > 1970"));

            System.out.println("On sélectionne les colonnes des noms et des années de naissance");
            System.out.println(frame.select_column(new String[]{ "Nom", "Annee de naissance" }));

            for(int i = 0; i < frame.getData().size(); i += 2) {
                frame.supprimerLigne(i);
            }
            System.out.println("On supprime les lignes impaires");
            System.out.println(frame);

        } catch (NoSuchFileException ignored) {
        }
    }
}
