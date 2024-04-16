import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperties;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Stats_DataFrameTest {
    static DataFrame df;

    @BeforeAll
    static void setUp() {
        float f1, f2, f3, f4, f5;
        double d1, d2, d3, d4, d5;
        long l1, l2, l3, l4, l5;
        f1 = f2 = f3 = f4 = f5 = 0.1f;
        d1 = d2 = d3 = d4 = d5 = 0.1;
        l1 = l2 = l3 = l4 = l5 = 1;
        // Création d'un DataFrame
        List<List<Object>> data = new ArrayList<>();
        List<Object> row1 = new ArrayList<>();
        row1.add(1);
        row1.add("Alice");
        row1.add(24);
        row1.add(f1);
        row1.add(d1);
        row1.add(l1);
        data.add(row1);
        List<Object> row2 = new ArrayList<>();
        row2.add(2);
        row2.add("Bob");
        row2.add(48);
        row2.add(f2);
        row2.add(d2);
        row2.add(l2);
        data.add(row2);
        List<Object> row3 = new ArrayList<>();
        row3.add(3);
        row3.add("Charlie");
        row3.add(32);
        row3.add(f3);
        row3.add(d3);
        row3.add(l3);
        data.add(row3);
        List<Object> row4 = new ArrayList<>();
        row4.add(4);
        row4.add("David");
        row4.add(28);
        row4.add(f4);
        row4.add(d4);
        row4.add(l4);
        data.add(row4);
        List<Object> row5 = new ArrayList<>();
        row5.add(5);
        row5.add("Eve");
        row5.add(40);
        row5.add(f5);
        row5.add(d5);
        row5.add(l5);
        data.add(row5);

        // Création HashMap des colonnes
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("id", Integer.class);
        columns.put("name", String.class);
        columns.put("age", Integer.class);
        columns.put("float", Float.class);
        columns.put("double", Double.class);
        columns.put("long", Long.class);

        // Création de la dataFrame
        df = new DataFrame(data, columns);
    }

    @Test
    @DisplayName("Test de la méthode min avec une colonne de type Integer")
    void testMin() {
        assertEquals(24, df.min("age"), "La valeur minimale de la colonne age est 24");
        assertEquals(1, df.min("id"), "La valeur minimale de la colonne id est 1");
    }

    @Test
    @DisplayName("Test de la méthode min avec une colonne dont le nom n'existe pas")
    void testMinOutOfRange() {
        assertEquals(-1, df.min("blabla"), "La colonne blabla n'existe pas");
    }

    @Test
    @DisplayName("Test de la méthode min avec une colonne de type float")
    void testMinFloat() {
        assertEquals(0.1, df.min("float"), 0.1f, "La valeur minimale de la colonne float est 0.1");
    }

    @Test
    @DisplayName("Test de la méthode min avec une colonne de type double")
    void testMinDouble() {
        assertEquals(0.1, df.min("double"), 0.1f, "La valeur minimale de la colonne double est 0.1");
    }

    @Test
    @DisplayName("Test de la méthode min avec une colonne de type long")
    void testMinLong() {
        assertEquals(1, df.min("long"), "La valeur minimale de la colonne long est 1");
    }

    @Test
    @DisplayName("Test de la méthode min avec une colonne de type String")
    void testMinAvecColonneString() {
        assertEquals(-2, df.min("name"), "La valeur minimale d'une colonne de type String renvoie '-2'");
    }

    @Test
    @DisplayName("Test de la méthode min(nombre) avec une colonne de type Integer")
    void testMinNumerique() {
        assertEquals(1, df.min(0), "La valeur minimale de la colonne id est 1");
        assertEquals(24, df.min(2), "La valeur minimale de la colonne age est 24");
    }

    @Test
    @DisplayName("Test de la méthode min(nombre) avec une colonne de type float")
    void testMinNumeriqueFloat() {
        assertEquals(0.1, df.min(3), 0.1f, "La valeur minimale de la colonne float est 0.1");
    }

    @Test
    @DisplayName("Test de la méthode min(nombre) avec une colonne de type double")
    void testMinNumeriqueDouble() {
        assertEquals(0.1, df.min(4), 0.1f, "La valeur minimale de la colonne double est 0.1");
    }

    @Test
    @DisplayName("Test de la méthode min(nombre) avec une colonne de type long")
    void testMinNumeriqueLong() {
        assertEquals(1, df.min(5), "La valeur minimale de la colonne long est 1");
    }

    @Test
    @DisplayName("Test de la méthode min(nombre) avec une colonne de type String")
    void testMinNumeriqueAvecString() {
        assertEquals(-2, df.min(1), "La valeur minimale d'une colonne de type String renvoie '-2'");
    }

    @Test
    @DisplayName("Test de la méthode min(nombre) avec un index de colonne inexistant")
    void testMinNumeriqueOutOfRange() {
        assertEquals(-1, df.min(12), "L'index de colonne 12 n'existe pas");
    }

    @Test
    @DisplayName("Test de la méthode min(nombre) avec un index de colonne négatif")
    void testMinNumeriqueNegatif() {
        assertEquals(-1, df.min(-1), "L'index de colonne -1 n'existe pas");
    }

    @Test
    @DisplayName("Test de la méthode max avec une colonne de type Integer")
    void testMax() {
        assertEquals(48, df.max("age"), 1f, "La valeur maximale de la colonne age est 48");
        assertEquals(5, df.max("id"), 1f, "La valeur maximale de la colonne id est 5");
    }

    @Test
    @DisplayName("Test de la méthode max avec une colonne dont le nom n'existe pas")
    void testMaxOutOfRange() {
        assertEquals(-1, df.max("blabla"), "La colonne blabla n'existe pas");
    }

    @Test
    @DisplayName("Test de la méthode max avec une colonne de type float")
    void testMaxFloat() {
        assertEquals(0.1, df.max("float"), 0.1f, "La valeur maximale de la colonne float est 0.1");
    }

    @Test
    @DisplayName("Test de la méthode max avec une colonne de type double")
    void testMaxDouble() {
        assertEquals(0.1, df.max("double"), 0.1f, "La valeur maximale de la colonne double est 0.1");
    }

    @Test
    @DisplayName("Test de la méthode max avec une colonne de type long")
    void testMaxLong() {
        assertEquals(1, df.max("long"), 1f, "La valeur maximale de la colonne long est 5");
    }

    @Test
    @DisplayName("Test de la méthode max avec une colonne de type String")
    void testMaxAvecColonneString() {
        assertEquals(-2, df.max("name"), "La valeur maximale d'une colonne de type String renvoie '-2'");
    }


    @Test
    @DisplayName("Test de la méthode max(nombre) avec une colonne de type Integer")
    void testMaxNumerique() {
        assertEquals(5, df.max(0), 1f, "La valeur maximale de la colonne id est 5");
        assertEquals(48, df.max(2), 1f, "La valeur maximale de la colonne age est 48");
    }

    @Test
    @DisplayName("Test de la méthode max(nombre) avec une colonne de type float")
    void testMaxNumeriqueFloat() {
        assertEquals(0.1, df.max(3), 0.1f, "La valeur maximale de la colonne float est 0.1");
    }

    @Test
    @DisplayName("Test de la méthode max(nombre) avec une colonne de type double")
    void testMaxNumeriqueDouble() {
        assertEquals(0.1, df.max(4), 0.1f, "La valeur maximale de la colonne double est 0.1");
    }

    @Test
    @DisplayName("Test de la méthode max(nombre) avec une colonne de type long")
    void testMaxNumeriqueLong() {
        assertEquals(1, df.max(5), 1f, "La valeur maximale de la colonne long est 5");
    }

    @Test
    @DisplayName("Test de la méthode max(nombre) avec une colonne de type String")
    void testMaxNumeriqueAvecString() {
        assertEquals(-2, df.max(1), "La valeur maximale d'une colonne de type String renvoie '-2'");
    }

    @Test
    @DisplayName("Test de la méthode max(nombre) avec un index de colonne inexistant")
    void testMaxNumeriqueOutOfRange() {
        assertEquals(-1, df.max(12), "L'index de colonne 12 n'existe pas");
    }

    @Test
    @DisplayName("Test de la méthode max(nombre) avec un index de colonne négatif")
    void testMaxNumeriqueNegatif() {
        assertEquals(-1, df.max(-1), "L'index de colonne -1 n'existe pas");
    }

    @Test
    @DisplayName("Test de la méthode mean avec une colonne de type Integer")
    void testMean() {
        assertEquals(34.4, df.mean("age"), 0.1f, "La moyenne de la colonne age est 34.4");
        assertEquals(3, df.mean("id"), 1f, "La moyenne de la colonne id est 3");
    }

    @Test
    @DisplayName("Test de la méthode mean avec une colonne de type float")
    void testMeanFloat() {
        assertEquals(0.1, df.mean("float"), 0.1f, "La moyenne de la colonne float est 0.1");
    }

    @Test
    @DisplayName("Test de la méthode mean avec une colonne de type double")
    void testMeanDouble() {
        assertEquals(0.1, df.mean("double"), 0.1f, "La moyenne de la colonne double est 0.1");
    }

    @Test
    @DisplayName("Test de la méthode mean avec une colonne de type long")
    void testMeanLong() {
        assertEquals(1, df.mean("long"), 1f, "La moyenne de la colonne long est 1");
    }

    @Test
    @DisplayName("Test de la méthode mean avec une colonne de type String")
    void testMeanAvecColonneString() {
        assertEquals(-2, df.mean("name"), "La moyenne d'une colonne de type String renvoie '-2'");
    }

    @Test
    @DisplayName("Test de la méthode mean(nombre) avec une colonne qui n'existe pas")
    void testMeanOutOfRange() {
        assertEquals(-1, df.mean("blabla"), "La colonne blabla n'existe pas");
    }

    @Test
    @DisplayName("Test de la méthode mean(nombre) avec une colonne de type Integer")
    void testMeanNumerique() {
        assertEquals(3, df.mean(0), 1f, "La moyenne de la colonne id est 3");
        assertEquals(34.4, df.mean(2), 0.1f, "La moyenne de la colonne age est 34.4");
    }

    @Test
    @DisplayName("Test de la méthode mean(nombre) avec une colonne de type float")
    void testMeanNumeriqueFloat() {
        assertEquals(0.1, df.mean(3), 0.1f, "La moyenne de la colonne float est 0.1");
    }

    @Test
    @DisplayName("Test de la méthode mean(nombre) avec une colonne de type double")
    void testMeanNumeriqueDouble() {
        assertEquals(0.1, df.mean(4), 0.1f, "La moyenne de la colonne double est 0.1");
    }

    @Test
    @DisplayName("Test de la méthode mean(nombre) avec une colonne de type long")
    void testMeanNumeriqueLong() {
        assertEquals(1, df.mean(5), 1f, "La moyenne de la colonne long est 1");
    }

    @Test
    @DisplayName("Test de la méthode mean(nombre) avec une colonne de type String")
    void testMeanNumeriqueAvecString() {
        assertEquals(-2, df.mean(1), "La moyenne d'une colonne de type String renvoie '-2'");
    }

    @Test
    @DisplayName("Test de la méthode mean(nombre) avec un index de colonne inexistant")
    void testMeanNumeriqueOutOfRange() {
        assertEquals(-1, df.mean(12), "L'index de colonne 12 n'existe pas");
    }

    @Test
    @DisplayName("Test de la méthode mean(nombre) avec un index de colonne négatif")
    void testMeanNumeriqueNegatif() {
        assertEquals(-1, df.mean(-1), "L'index de colonne -1 n'existe pas");
    }

    @Test
    @DisplayName("Test de la méthode sum avec une colonne de type Integer")
    void testSum() {
        assertEquals(172, df.sum("age"), 1f, "La somme de la colonne age est 172");
        assertEquals(15, df.sum("id"), 1f, "La somme de la colonne id est 15");
    }

    @Test
    @DisplayName("Test de la méthode sum avec une colonne de type float")
    void testSumFloat() {
        assertEquals(0.5, df.sum("float"), 0.1f, "La somme de la colonne float est 0.5");
    }

    @Test
    @DisplayName("Test de la méthode sum avec une colonne de type double")
    void testSumDouble() {
        assertEquals(0.5, df.sum("double"), 0.1f, "La somme de la colonne double est 0.5");
    }

    @Test
    @DisplayName("Test de la méthode sum avec une colonne de type long")
    void testSumLong() {
        assertEquals(5, df.sum("long"), 1f, "La somme de la colonne long est 5");
    }

    @Test
    @DisplayName("Test de la méthode sum avec une colonne de type String")
    void testSumAvecColonneString() {
        assertEquals(-2, df.sum("name"), "La somme d'une colonne de type String renvoie '-2'");
    }

    @Test
    @DisplayName("Test de la méthode sum(nombre) avec une colonne qui n'existe pas")
    void testSumOutOfRange() {
        assertEquals(-1, df.sum("blabla"), "La colonne blabla n'existe pas");
    }

    @Test
    @DisplayName("Test de la méthode sum(nombre) avec une colonne de type Integer")
    void testSumNumerique() {
        assertEquals(15, df.sum(0), 1f, "La somme de la colonne id est 15");
        assertEquals(172, df.sum(2), 1f, "La somme de la colonne age est 172");
    }

    @Test
    @DisplayName("Test de la méthode sum(nombre) avec une colonne de type float")
    void testSumNumeriqueFloat() {
        assertEquals(0.5, df.sum(3), 0.1f, "La somme de la colonne float est 0.5");
    }

    @Test
    @DisplayName("Test de la méthode sum(nombre) avec une colonne de type double")
    void testSumNumeriqueDouble() {
        assertEquals(0.5, df.sum(4), 0.1f, "La somme de la colonne double est 0.5");
    }

    @Test
    @DisplayName("Test de la méthode sum(nombre) avec une colonne de type long")
    void testSumNumeriqueLong() {
        assertEquals(5, df.sum(5), 1f, "La somme de la colonne long est 5");
    }

    @Test
    @DisplayName("Test de la méthode sum(nombre) avec une colonne de type String")
    void testSumNumeriqueAvecString() {
        assertEquals(-2, df.sum(1), "La somme d'une colonne de type String renvoie '-2'");
    }

    @Test
    @DisplayName("Test de la méthode sum(nombre) avec un index de colonne inexistant")
    void testSumNumeriqueOutOfRange() {
        assertEquals(-1, df.sum(12), "L'index de colonne 12 n'existe pas");
    }

    @Test
    @DisplayName("Test de la méthode sum(nombre) avec un index de colonne négatif")
    void testSumNumeriqueNegatif() {
        assertEquals(-1, df.sum(-1), "L'index de colonne -1 n'existe pas");
    }
}