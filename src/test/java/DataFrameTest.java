import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class DataFrameTest {
    static DataFrame df;
    static DataFrame df2;
    @BeforeEach
    public void testDataFrameCreationList() {
        System.out.println("_________________Création d'un DataFrame de test_________________");
        List<List<Object>> data = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(1, "Bonjour", true)),
                new ArrayList<>(Arrays.asList(2, "Monde", false))
        ));
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        df = new DataFrame(data, columns);
        assertNotEquals(null, df);
    }

    @Test
    public void testDataFrameCreationArray() {
        System.out.println("_________________Création d'un DataFrame de test_________________");
        Object[][] data = new Object[][] {
            {1, "Bonjour", true},
            {2, "Monde", false}
        };
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("A", Integer.class);
        columns.put("B", String.class);
        columns.put("C", Boolean.class);
        df2 = new DataFrame(data, columns);
        assertNotEquals(null, df2);
    }

    @Test
    public void testAjouterLigne() {
        System.out.println("_________________Ajout d'une ligne au DataFrame de test_________________");
        List<Object> ligne = Arrays.asList(3, "Salut", true);
        df.ajouterLigne(ligne);
        System.out.println();
    }

    @Test
    public void testAjouterLigneInvalide() {
        System.out.println("_________________Ajout d'une ligne (invalide) au DataFrame de test_________________");
        List<Object> ligne = Arrays.asList(3, "Salut");
        assertThrows(IllegalArgumentException.class, () -> df.ajouterLigne(ligne));
        System.out.println();
    }

    @Test
    public void testAjouterLigneInvalide2() {
        System.out.println("_________________Ajout d'une ligne (invalide) au DataFrame de test_________________");
        assertThrows(IllegalArgumentException.class, () -> df.ajouterLigne(null));
        System.out.println();
    }
    @Test
    public void testSupprimerLigne() {
        System.out.println("_________________Suppression d'une ligne du DataFrame de test_________________");
        df.supprimerLigne(0);
        System.out.println();
    }

    @Test
    public void testSupprimerLigneInvalide() {
        System.out.println("_________________Suppression d'une ligne (invalide) du DataFrame de test_________________");
        assertThrows(IllegalArgumentException.class, () -> df.supprimerLigne(2));
        System.out.println();
    }

    @Test
    public void testSupprimerColonne() {
        System.out.println("_________________Suppression d'une colonne du DataFrame de test_________________");
        df.supprimerColonne("B");
        System.out.println();
    }

    @Test
    public void testSupprimerColonneInvalide() {
        System.out.println("_________________Suppression d'une colonne (invalide) du DataFrame de test_________________");
        assertThrows(IllegalArgumentException.class, () -> df.supprimerColonne("D"));
        System.out.println();
    }

    @Test
    public void testGetters() {
        System.out.println("_________________Test des getters_________________");
        System.out.println(df.getData());
        System.out.println(df.getColumns());
        System.out.println();
    }

    @Test
    public void testObtenirValeur() {
        System.out.println("_________________Obtenir une valeur du DataFrame de test_________________");
        String valeur = (String) df.obtenirValeur(0, "B");
        System.out.println("La valeur est : " + valeur);
        System.out.println();
    }

    @Test
    public void testObtenirValeurInvalideColonne() {
        System.out.println("_________________Obtenir une valeur (Colonne invalide) du DataFrame de test_________________");
        assertThrows(IllegalArgumentException.class, () -> df.obtenirValeur(0, "D"));
        System.out.println();
    }

    @Test
    public void testObtenirValeurInvalideLigne() {
        System.out.println("_________________Obtenir une valeur (Ligne invalide) du DataFrame de test_________________");
        assertThrows(IllegalArgumentException.class, () -> df.obtenirValeur(10, "A"));
        System.out.println();
    }

    @Test
    public void testGetIndexesColonne() {
        System.out.println("_________________Obtenir l'index d'une colonne du DataFrame de test_________________");
        int index = df.getIndexesColone("B");
        System.out.println("L'index de la colonne est : " + index);
        System.out.println();
    }

    @Test
    public void testGetIndexesColonneInvalide() {
        System.out.println("_________________Obtenir l'index d'une colonne (invalide) du DataFrame de test_________________");
        assertThrows(IllegalArgumentException.class, () -> df.getIndexesColone("E"));
        System.out.println();
    }

    // Test de robustesse
    @Test
    public void testRobustesseIndexObtenirValeur() {
        System.out.println("_________________Test de robustesse (Index négatif)_________________");
        assertThrows(IllegalArgumentException.class, () -> df.obtenirValeur(-1, "A"));
        System.out.println();
    }

    @Test
    public void testRobustesseIndexSupprimerLigneMore() {
        System.out.println("_________________Test de robustesse (Index invalide)_________________");
        assertThrows(IllegalArgumentException.class, () -> df.supprimerLigne(10));
        System.out.println();
    }

    @Test
    public void testRobustesseIndexSupprimerLigneNegative() {
        System.out.println("_________________Test de robustesse (Index invalide)_________________");
        assertThrows(IllegalArgumentException.class, () -> df.supprimerLigne(-1));
        System.out.println();
    }

    @Test
    public void testRobustesseIndexSupprimerColonne() {
        System.out.println("_________________Test de robustesse (Index invalide)_________________");
        assertThrows(IllegalArgumentException.class, () -> df.supprimerColonne("D"));
        System.out.println();
    }
}