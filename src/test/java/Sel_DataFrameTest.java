import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;


public class Sel_DataFrameTest {
    static DataFrame df;

    // Avant chaque test, on crée un DataFrame de test :
    @BeforeEach
    public void testDataFrameCreationList() {
        df = new DataFrame(
            Arrays.asList(
                    Arrays.asList("Alves", "Jean-pierre", 3, 1.0),
                    Arrays.asList("Bossy", "Jean Patrick", 6, 2.0),
                    Arrays.asList("De Moulin", "Catapulte", 9, 3.0),
                    Arrays.asList("Aled Plus", "D Inspiration", 12, 4.0)
            ),
            new LinkedHashMap<>() {{
                put("Nom", String.class);
                put("Prenom", String.class);
                put("Age", Integer.class);
                put("Je sais pas", Double.class);
            }}
        );
        df.display();
        assertNotEquals(null, df);
    }

    @Test
    public void testSelectLine() {
        System.out.println("_________________Sélection de lignes_________________");
        DataFrame new_df = df.select_line(new int[] {0, 2});
        new_df.display();
        assertEquals(
            new_df.getData(),
            Arrays.asList(
                Arrays.asList("Alves", "Jean-pierre", 3, 1.0),
                Arrays.asList("De Moulin", "Catapulte", 9, 3.0)
            )
        );
    }
    @Test
    public void testSelectColumn() {
        System.out.println("_________________Sélection de colonnes_________________");
        String[] columns = new String[] {"Nom", "Age"};
        DataFrame new_df = df.select_column(columns);
        assertEquals(
            new_df.getData(),
            Arrays.asList(
                Arrays.asList("Alves", 3),
                Arrays.asList("Bossy", 6),
                Arrays.asList("De Moulin", 9),
                Arrays.asList("Aled Plus", 12)
            )
        );
    }
    @Test
    public void testSelectColumnNotFound() {
        System.out.println("_________________Sélection de colonnes2_________________");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> df.select_column(new String[] {"A", "D"}));
        assertEquals("Column not found", exception.getMessage());
    }
    @Test
    public void testSelect_Inferior() {
        System.out.println("_________________Sélection de lignes2_________________");
        DataFrame new_df = df.select("Age < 4");
        assertEquals(
            new_df.getData(),
                List.of(
                        Arrays.asList("Alves", "Jean-pierre", 3, 1.0)
                )
        );
    }
    @Test
    public void testSelect_Superior() {
        System.out.println("_________________Sélection de lignes3_________________");
        DataFrame new_df = df.select("Age > 4");
        assertEquals(
            new_df.getData(),
                List.of(
                        List.of("Bossy", "Jean Patrick", 6, 2.0),
                        List.of("De Moulin", "Catapulte", 9, 3.0),
                        List.of("Aled Plus", "D Inspiration", 12, 4.0)
                )
        );
    }
    @Test
    public void testSelect_Equal() {
        System.out.println("_________________Sélection de lignes4_________________");
        DataFrame new_df = df.select("Age == 6");
        assertEquals(
            new_df.getData(),
                List.of(
                        List.of("Bossy", "Jean Patrick", 6, 2.0)
                )
        );
    }
    @Test
    public void testSelect_NotEqual() {
        System.out.println("_________________Sélection de lignes5_________________");
        DataFrame new_df = df.select("Age != 6");
        assertEquals(
            new_df.getData(),
            Arrays.asList(
                    Arrays.asList("Alves", "Jean-pierre", 3, 1.0),
                    Arrays.asList("De Moulin", "Catapulte", 9, 3.0),
                    Arrays.asList("Aled Plus", "D Inspiration", 12, 4.0)
            )
        );
    }
    @Test
    public void testSelect_InferiorEqual() {
        System.out.println("_________________Sélection de lignes6_________________");
        DataFrame new_df = df.select("Age <= 6");
        assertEquals(
            new_df.getData(),
            Arrays.asList(
                    Arrays.asList("Alves", "Jean-pierre", 3, 1.0),
                    Arrays.asList("Bossy", "Jean Patrick", 6, 2.0)
            )
        );
    }
    @Test
    public void testSelect_SuperiorEqual() {
        System.out.println("_________________Sélection de lignes7_________________");
        DataFrame new_df = df.select("Age >= 4");
        assertEquals(
            new_df.getData(),
            Arrays.asList(
                    Arrays.asList("Bossy", "Jean Patrick", 6, 2.0),
                    Arrays.asList("De Moulin", "Catapulte", 9, 3.0),
                    Arrays.asList("Aled Plus", "D Inspiration", 12, 4.0)
            )
        );
    }
    //Test de la méthode select avec des float
    @Test
    public void testSelect_Float_Superior(){
        System.out.println("_________________Sélection de lignes8_________________");
        DataFrame new_df = df.select("Je sais pas > 2.0");
        assertEquals(
            new_df.getData(),
            Arrays.asList(
                Arrays.asList("De Moulin", "Catapulte", 9, 3.0),
                Arrays.asList("Aled Plus", "D Inspiration", 12, 4.0)
            )
        );
    }
    @Test
    public void testSelect_Float_SuperiorEqual(){
        System.out.println("_________________Sélection de lignes9_________________");
        DataFrame new_df = df.select("Je sais pas >= 2.0");
        assertEquals(
            new_df.getData(),
            Arrays.asList(
                Arrays.asList("Bossy", "Jean Patrick", 6, 2.0),
                Arrays.asList("De Moulin", "Catapulte", 9, 3.0),
                Arrays.asList("Aled Plus", "D Inspiration", 12, 4.0)
            )
        );
    }
    @Test
    public void testSelect_Float_Inferior(){
        System.out.println("_________________Sélection de lignes10_________________");
        DataFrame new_df = df.select("Je sais pas < 2.0");
        assertEquals(
            new_df.getData(),
                List.of(
                        Arrays.asList("Alves", "Jean-pierre", 3, 1.0)
                )
        );
    }
    @Test
    public void testSelect_Float_InferiorEqual(){
        System.out.println("_________________Sélection de lignes11_________________");
        DataFrame new_df = df.select("Je sais pas <= 2.0");
        assertEquals(
            new_df.getData(),
            Arrays.asList(
                Arrays.asList("Alves", "Jean-pierre", 3, 1.0),
                Arrays.asList("Bossy", "Jean Patrick", 6, 2.0)
            )
        );
    }
    @Test
    public void testSelect_Float_Equal(){
        System.out.println("_________________Sélection de lignes12_________________");
        DataFrame new_df = df.select("Je sais pas == 2.0");
        assertEquals(
            new_df.getData(),
                List.of(
                        Arrays.asList("Bossy", "Jean Patrick", 6, 2.0)
                )
        );
    }
    @Test
    public void testSelect_Float_NotEqual(){
        System.out.println("_________________Sélection de lignes13_________________");
        DataFrame new_df = df.select("Je sais pas != 2.0");
        assertEquals(
            new_df.getData(),
            Arrays.asList(
                Arrays.asList("Alves", "Jean-pierre", 3, 1.0),
                Arrays.asList("De Moulin", "Catapulte", 9, 3.0),
                Arrays.asList("Aled Plus", "D Inspiration", 12, 4.0)
            )
        );
    }
    //Test de la méthode select avec des String
    @Test
    public void testSelect_String_Equal(){
        System.out.println("_________________Sélection de lignes14_________________");
        DataFrame new_df = df.select("Nom == Alves");
        assertEquals(
            new_df.getData(),
                List.of(
                        Arrays.asList("Alves", "Jean-pierre", 3, 1.0)
                )
        );
    }
    @Test
    public void testSelect_String_NotEqual(){
        System.out.println("_________________Sélection de lignes15_________________");
        DataFrame new_df = df.select("Nom != Alves");
        assertEquals(
            new_df.getData(),
            Arrays.asList(
                Arrays.asList("Bossy", "Jean Patrick", 6, 2.0),
                Arrays.asList("De Moulin", "Catapulte", 9, 3.0),
                Arrays.asList("Aled Plus", "D Inspiration", 12, 4.0)
            )
        );
    }
    //Test de la méthode select avec des erreurs
    @Test
    public void testSelect_Notfound(){
        System.out.println("_________________Sélection de lignes8_________________");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> df.select("Nom << Alves"));
        assertEquals("String Operator not found", exception.getMessage());
    }
    @Test
    public void testSelect_Notfound2(){
        System.out.println("_________________Sélection de lignes9_________________");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> df.select("Je sais pas >< 2.0"));
        assertEquals("Float Operator not found", exception.getMessage());
    }
    @Test
    public void testSelect_Notfound3(){
        System.out.println("_________________Sélection de lignes10_________________");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> df.select("Age <> 2"));
        assertEquals("Int Operator not found", exception.getMessage());
    }
}
