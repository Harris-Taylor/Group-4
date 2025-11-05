import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class MyTest {
    @Test
    void stringIsCorrect() {
        String message = "Hello";
        assertEquals("Hello", message);
    }
    @Test
    void additionWorks() {
        assertEquals(10, 5 + 5);
    }
}
