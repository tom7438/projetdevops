import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Sel_DataFrameTest {
    @Test
    public void testSelectLine() {
        DataFrame df = new DataFrame(
            Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
            ),
            new LinkedHashMap<>() {{
                put("A", Integer.class);
                put("B", Integer.class);
                put("C", Integer.class);
            }}
        );
        DataFrame new_df = df.select_line(new int[] {0, 2});
        assertEquals(
            new_df.getData(),
            Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(7, 8, 9)
            )
        );
    }
    @Test
    public void testSelectColumn() {
        DataFrame df = new DataFrame(
            Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
            ),
            new LinkedHashMap<>() {{
                put("A", Integer.class);
                put("B", Integer.class);
                put("C", Integer.class);
            }}
        );
        DataFrame new_df = df.select_column(new String[] {"A", "C"});

        assertEquals(
            new_df.getData(),
            Arrays.asList(
                Arrays.asList(1, 3),
                Arrays.asList(4, 6),
                Arrays.asList(7, 9)
            )
        );
    }
    @Test
    public void testSelectColumnNotFound() {

        DataFrame df = new DataFrame(
            Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
            ),
            new LinkedHashMap<>() {{
                put("A", Integer.class);
                put("B", Integer.class);
                put("C", Integer.class);
            }}
        );
        Exception exception = assertThrows(IllegalArgumentException.class, () -> df.select_column(new String[] {"A", "D"}));
        assertEquals("Column not found", exception.getMessage());
    }
    @Test
    public void testSelect_Inferior() {
        DataFrame df = new DataFrame(
            Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
            ),
            new LinkedHashMap<>() {{
                put("A", Integer.class);
                put("B", Integer.class);
                put("C", Integer.class);
            }}
        );
        DataFrame new_df = df.select("A > 2");
        assertEquals(
            new_df.getData(),
            Arrays.asList(
                    List.of(4),
                    List.of(7)
            )
        );
    }
    @Test
    public void testSelect_Superior() {
        DataFrame df = new DataFrame(
            Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
            ),
            new LinkedHashMap<>() {{
                put("A", Integer.class);
                put("B", Integer.class);
                put("C", Integer.class);
            }}
        );
        DataFrame new_df = df.select("A < 4");
        assertEquals(
            new_df.getData(),
                List.of(
                        List.of(1)
                )
        );
    }
    @Test
    public void testSelect_Equal() {
        DataFrame df = new DataFrame(
            Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
            ),
            new LinkedHashMap<>() {{
                put("A", Integer.class);
                put("B", Integer.class);
                put("C", Integer.class);
            }}
        );
        DataFrame new_df = df.select("B == 5");
        assertEquals(
            new_df.getData(),
                List.of(
                        List.of(5)
                )
        );
    }
    @Test
    public void testSelect_NotEqual() {
        DataFrame df = new DataFrame(
            Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
            ),
            new LinkedHashMap<>() {{
                put("A", Integer.class);
                put("B", Integer.class);
                put("C", Integer.class);
            }}
        );
        DataFrame new_df = df.select("B != 5");
        assertEquals(
            new_df.getData(),
            Arrays.asList(
                    List.of(2),
                    List.of(8)
            )
        );
    }
    @Test
    public void testSelect_InferiorEqual() {
        DataFrame df = new DataFrame(
            Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
            ),
            new LinkedHashMap<>() {{
                put("A", Integer.class);
                put("B", Integer.class);
                put("C", Integer.class);
            }}
        );
        DataFrame new_df = df.select("A <= 4");
        assertEquals(
            new_df.getData(),
            Arrays.asList(
                    List.of(1),
                    List.of(4)
            )
        );
    }
    @Test
    public void testSelect_SuperiorEqual() {
        DataFrame df = new DataFrame(
            Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
            ),
            new LinkedHashMap<>() {{
                put("A", Integer.class);
                put("B", Integer.class);
                put("C", Integer.class);
            }}
        );
        DataFrame new_df = df.select("A >= 4");
        assertEquals(
            new_df.getData(),
            Arrays.asList(
                    List.of(4),
                    List.of(7)
            )
        );
    }
    @Test
    public void testSelect_Notfound(){
        DataFrame df = new DataFrame(
            Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
            ),
            new LinkedHashMap<>() {{
                put("A", Integer.class);
                put("B", Integer.class);
                put("C", Integer.class);
            }}
        );
        Exception exception = assertThrows(IllegalArgumentException.class, () -> df.select("A << 2"));
        assertEquals("Operator not found", exception.getMessage());
    }

}
