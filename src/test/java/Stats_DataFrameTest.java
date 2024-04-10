import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Stats_DataFrameTest {
    static DataFrame df;

    @BeforeAll
    static void setUp() {
        // Création d'un DataFrame
        List<List<Object>> data = new ArrayList<>();
        List<Object> row1 = new ArrayList<>();
        row1.add(1);
        row1.add("Alice");
        row1.add(24);
        data.add(row1);
        List<Object> row2 = new ArrayList<>();
        row2.add(2);
        row2.add("Bob");
        row2.add(48);
        data.add(row2);
        List<Object> row3 = new ArrayList<>();
        row3.add(3);
        row3.add("Charlie");
        row3.add(32);
        data.add(row3);
        List<Object> row4 = new ArrayList<>();
        row4.add(4);
        row4.add("David");
        row4.add(28);
        data.add(row4);
        List<Object> row5 = new ArrayList<>();
        row5.add(5);
        row5.add("Eve");
        row5.add(40);
        data.add(row5);

        // Création HashMap des colonnes
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("id", Integer.class);
        columns.put("name", String.class);
        columns.put("age", Integer.class);

        // Création de la dataFrame
        df = new DataFrame(data, columns);
    }

    @Test
    void testMin() {
        float res = df.min("age");
        assertEquals(24, res);
        res = df.min("id");
        assertEquals(1, res);
    }

    @Test
    void testMinNumerique() {
        float res = df.min(0);
        assertEquals(1, res);
        res = df.min(2);
        assertEquals(24, res);
    }

    @Test
    void testMax() {
        float res = df.max("age");
        assertEquals(48.0, res);
        res = df.max("id");
        assertEquals(5, res);
    }

    @Test
    void testMaxNumerique() {
        float res = df.max(0);
        assertEquals(5, res);
        res = df.max(2);
        assertEquals(48, res);
    }

    @Test
    void testMean() {
        float res = df.mean("age");
        assertEquals(34.4, res, 0.1f);
        res = df.mean("id");
        assertEquals(3, res);
    }

    @Test
    void testMeanNumerique() {
        float res = df.mean(0);
        assertEquals(3, res);
        res = df.mean(2);
        assertEquals(34.4, res, 0.1f);
    }

    @Test
    void testSum() {
        float res = df.sum("age");
        assertEquals(172, res);
        res = df.sum("id");
        assertEquals(15, res);
    }

    @Test
    void testSumNumerique() {
        float res = df.sum(0);
        assertEquals(15, res);
        res = df.sum(2);
        assertEquals(172, res);
    }
}