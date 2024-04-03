import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuCodeTest {

    @Test
    void code() {
        DuCode c = new DuCode();

        int v = 12;
        int a = c.code(v);
        assertEquals(a, v);
    }
}