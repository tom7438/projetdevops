import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataFrameTest {
    @Test
    public void testDataFrameCreation() {
        System.out.println("_________________Création d'un DataFrame de test_________________");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList(1, "Bonjour", true),
                Arrays.asList(2, "Monde", false)
        );
        /* La liste de liste est de cette forme
        [
            [1, "Bonjour", true],
            [2, "Monde", false]
        ]
        */

        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        /* Le nom des colonnes et leur type sont stockés dans une LinkedHashMap
        {
            "A": Integer,
            "B": String,
            "C": Boolean
        }
        */

        DataFrame df = new DataFrame(data, columns);

        // Afficher le DataFrame de test
        df.afficherDataFrame();
        System.out.println();
    }

    @Test
    public void testAjouterLigne() {
        System.out.println("_________________Ajout d'une ligne au DataFrame de test_________________");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList(1, "Bonjour", true),
                Arrays.asList(2, "Monde", false)
        );
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        DataFrame df = new DataFrame(data, columns);

        List<Object> ligne = Arrays.asList(3, "Salut", true);
        df.ajouterLigne(ligne);

        // Afficher le DataFrame de test
        df.afficherDataFrame();
        System.out.println();
    }

    @Test
    public void testAjouterLigneInvalide(){
        System.out.println("_________________Ajout d'une ligne (invalide) au DataFrame de test_________________");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList(1, "Bonjour", true),
                Arrays.asList(2, "Monde", false)
        );
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        DataFrame df = new DataFrame(data, columns);

        List<Object> ligne = Arrays.asList(3, "Salut");
        assertThrows(IllegalArgumentException.class, () -> df.ajouterLigne(ligne));
        System.out.println();
    }

    @Test
    public void testSupprimerLigne() {
        System.out.println("_________________Suppression d'une ligne du DataFrame de test_________________");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList(1, "Bonjour", true),
                Arrays.asList(2, "Monde", false)
        );
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        DataFrame df = new DataFrame(data, columns);

        df.supprimerLigne(0);

        // Afficher le DataFrame de test
        df.afficherDataFrame();
        System.out.println();
    }

    @Test
    public void testSupprimerLigneInvalide(){
        System.out.println("_________________Suppression d'une ligne (invalide) du DataFrame de test_________________");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList(1, "Bonjour", true),
                Arrays.asList(2, "Monde", false)
        );
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        DataFrame df = new DataFrame(data, columns);

        assertThrows(IllegalArgumentException.class, () -> df.supprimerLigne(2));
        System.out.println();
    }

    @Test
    public void testSupprimerColonne() {
        System.out.println("_________________Suppression d'une colonne du DataFrame de test_________________");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList(1, "Bonjour", true),
                Arrays.asList(2, "Monde", false)
        );
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        DataFrame df = new DataFrame(data, columns);
        df.supprimerColonne("B");

        // Afficher le DataFrame de test
        df.afficherDataFrame();
        System.out.println();
    }

    @Test
    public void testSupprimerColonneInvalide(){
        System.out.println("_________________Suppression d'une colonne (invalide) du DataFrame de test_________________");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList(1, "Bonjour", true),
                Arrays.asList(2, "Monde", false)
        );
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        DataFrame df = new DataFrame(data, columns);

        assertThrows(IllegalArgumentException.class, () -> df.supprimerColonne("D"));
        System.out.println();
    }

    @Test
    public void testGetters() {
        System.out.println("_________________Test des getters_________________");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList(1, "Bonjour", true),
                Arrays.asList(2, "Monde", false)
        );
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        DataFrame df = new DataFrame(data, columns);

        System.out.println(df.getData());
        System.out.println(df.getColumns());
        System.out.println();
    }

    @Test
    public void testObtenirValeur() {
        System.out.println("_________________Obtenir une valeur du DataFrame de test_________________");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList(1, "Bonjour", true),
                Arrays.asList(2, "Monde", false)
        );
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        DataFrame df = new DataFrame(data, columns);

        String valeur = (String) df.obtenirValeur(0, "B");
        System.out.println("La valeur est : " + valeur);
        System.out.println();
    }

    @Test
    public void testObtenirValeurInvalideColone(){
        System.out.println("_________________Obtenir une valeur (Colone invalide) du DataFrame de test_________________");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList(1, "Bonjour", true),
                Arrays.asList(2, "Monde", false)
        );
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        DataFrame df = new DataFrame(data, columns);

        assertThrows(IllegalArgumentException.class, () -> df.obtenirValeur(0, "D"));
        System.out.println();
    }

    @Test
    public void testObtenirValeurInvalideLigne(){
        System.out.println("_________________Obtenir une valeur (Ligne invalide) du DataFrame de test_________________");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList(1, "Bonjour", true),
                Arrays.asList(2, "Monde", false)
        );
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        DataFrame df = new DataFrame(data, columns);

        assertThrows(IllegalArgumentException.class, () -> df.obtenirValeur(10, "A"));
        System.out.println();
    }

    @Test
    public void testGetIndexesColone() {
        System.out.println("_________________Obtenir l'index d'une colonne du DataFrame de test_________________");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList(1, "Bonjour", true),
                Arrays.asList(2, "Monde", false)
        );
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        DataFrame df = new DataFrame(data, columns);

        int index = df.getIndexesColone("B");
        System.out.println("L'index de la colonne est : " + index);
        System.out.println();
    }

//    @Test
//    public void testGetIndexesColoneInvalide(){
//        System.out.println("_________________Obtenir l'index d'une colonne (invalide) du DataFrame de test_________________");
//        List<List<Object>> data = Arrays.asList(
//                Arrays.asList(1, "Bonjour", true),
//                Arrays.asList(2, "Monde", false)
//        );
//        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
//        columns.put("A", Integer.class);
//        columns.put("B", String.class);
//        columns.put("C", Boolean.class);
//        DataFrame df = new DataFrame(data, columns);
//
//        assertThrows(IllegalArgumentException.class, () -> df.getIndexesColone("D"));
//        System.out.println();
//    }

    @Test
    public void testGetIndexesColoneInvalide(){
        System.out.println("_________________Obtenir l'index d'une colonne (invalide) du DataFrame de test_________________");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList(1, "Bonjour", true),
                Arrays.asList(2, "Monde", false)
        );
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        DataFrame df = new DataFrame(data, columns);

        int index = df.getIndexesColone("E");
        System.out.println("L'index de la colonne est : " + index);
        System.out.println();
    }

    // Test de robustesse
    @Test
    public void testRobustesseIndexObtenirValeur(){
        System.out.println("_________________Test de robustesse (Index négatif)_________________");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList(1, "Bonjour", true),
                Arrays.asList(2, "Monde", false)
        );
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        DataFrame df = new DataFrame(data, columns);

        assertThrows(IllegalArgumentException.class, () -> df.obtenirValeur(-1, "A"));

        System.out.println();
    }

    @Test
    public void testRobustesseIndexSupprimerLigneMore(){
        System.out.println("_________________Test de robustesse (Index invalide)_________________");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList(1, "Bonjour", true),
                Arrays.asList(2, "Monde", false)
        );
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        DataFrame df = new DataFrame(data, columns);

        assertThrows(IllegalArgumentException.class, () -> df.supprimerLigne(10));
        System.out.println();
    }


    @Test
    public void testRobustesseIndexSupprimerLigneNegative(){
        System.out.println("_________________Test de robustesse (Index invalide)_________________");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList(1, "Bonjour", true),
                Arrays.asList(2, "Monde", false)
        );
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        DataFrame df = new DataFrame(data, columns);

        assertThrows(IllegalArgumentException.class, () -> df.supprimerLigne(-1));
        System.out.println();
    }

    @Test
    public void testRobustesseIndexSupprimerColonne(){
        System.out.println("_________________Test de robustesse (Index invalide)_________________");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList(1, "Bonjour", true),
                Arrays.asList(2, "Monde", false)
        );
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        DataFrame df = new DataFrame(data, columns);

        assertThrows(IllegalArgumentException.class, () -> df.supprimerColonne("D"));
        System.out.println();
    }
}