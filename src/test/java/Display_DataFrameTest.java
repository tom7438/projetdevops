import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

class Display_DataFrameTest {
    static DataFrame df;
    static DataFrame df2;

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

        // Création HashMap des colonnes
        LinkedHashMap<String, Class<?>> columns = new LinkedHashMap<>();
        columns.put("id", Integer.class);
        columns.put("name", String.class);
        columns.put("age", Integer.class);

        // Création de la dataFrame
        df = new DataFrame(data, columns);

        // DataFrame vide
        df2 = new DataFrame(new ArrayList<>(), new LinkedHashMap<>());
    }

    @Test
    void toStringTest() {
        System.out.println("\033[32mTest de la méthode toString\033[0m\n");
        df.display();
    }

    @Test
    void displayFirstLines() {
        System.out.println("\033[32mTest de la méthode displayFirstLines avec n=1\033[0m\n");
        df.displayFirstLines(1);
    }

    @Test
    void displayFirstLines2() {
        System.out.println("\033[32mTest de la méthode displayFirstLines avec n=10\033[0m\n");
        df.displayFirstLines(10);
    }

    @Test
    void displayLastLines() {
        System.out.println("\033[32mTest de la méthode displayLastLines avec n=1\033[0m\n");
        df.displayLastLines(1);
    }

    @Test
    void displayLastLines2() {
        System.out.println("\033[32mTest de la méthode displayLastLines avec n=10\033[0m\n");
        df.displayLastLines(10);
    }

    @Test
    void displayDFEmpty() {
        System.out.println("\033[32mTest de la méthode displayFirstLines avec un DataFrame vide et n = 1\033[0m\n");
        df2.displayFirstLines(1);
    }

}