import org.example.TqsStack;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class stackTests {

    TqsStack testStack = new TqsStack();

    @Test
    void stackEmptyOnConstruction() {
        assertEquals(0, testStack.size());
    }

    @Test
    void multipleStackPush() {
        testStack.push(1);
        testStack.push(2);
        testStack.push(3);

        assertEquals(false, testStack.isEmpty());
        assertEquals(3, testStack.size());
    }

    @Test
    void popElement() {
        testStack.push(4);
        assertEquals(4, testStack.pop());
    }

    @Test
    void peekElement() {
        testStack.push(5);
        int size = testStack.size();
        assertEquals(5, testStack.peek());
        assertEquals(size, testStack.size());
    }
}
