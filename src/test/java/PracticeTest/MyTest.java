package PracticeTest;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A collection of example unit tests demonstrating various JUnit assertions.
 */
class MyTest
{
    /**
     * Test 1: An equality check.
     */
    @Test
    void unitTest()
    {
        assertEquals(5, 5);
    }

    /**
    // Test 2: This test would fail because 5 != 4 (on purpose for testing)

    @Test
    void unitTest2()
    {
        assertEquals(5, 4);
    }
    */


    /**
     * Test 3: Uses a delta for floating point comparison.
     */
    @Test
    void unitTest4()
    {
        assertEquals(5.0, 5.01, 0.02);
    }

    /**
     * Test 4: Compares two integer arrays for equality.
     */
    @Test
    void unitTest5()
    {
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};
        assertArrayEquals(a, b);
    }

    /**
     * Test 5: Checks if a number is even.
     */
    @Test
    void testIsEven()
    {
        int num = 8;
        assertTrue(num % 2 == 0, "Number should be even");
    }

    /**
     * Test 6: Checks if a number is odd.
     */
    @Test
    void testIsOdd()
    {
        int num = 7;
        assertTrue(num % 2 != 0, "Number should be odd");
    }

    /**
     * Test 7: Verifies that a value is null.
     */
    @Test
    void unitTest8()
    {
        assertNull(null);
    }

    /**
     * Test 8: Verifies that a value is not null.
     */
    @Test
    void unitTest9()
    {
        assertNotNull("Hello");
    }

    /**
     * Test 9: Checks that a method throws a NullPointerException.
     */
    @Test
    void unitTest10()
    {
        assertThrows(NullPointerException.class, this::throwsException);
    }

    /**
     * Helper method for unitTest10 that always throws a NullPointerException.
     */
    void throwsException() throws NullPointerException
    {
        throw new NullPointerException();
    }

    /**
     * Test 10: Verifies that a sentence contains a specific word.
     */
    @Test
    void testStringContainsWord()
    {
        String sentence = "JUnit tests are fun";
        assertTrue(sentence.contains("fun"), "Sentence should contain 'fun'");
    }
}