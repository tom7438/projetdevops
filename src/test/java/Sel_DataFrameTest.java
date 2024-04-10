import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        try {
            df.select_column(new String[] {"A", "D"});
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Column not found");
        }
    }

}
