package PracticeTest;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


class MyTest
{
    // Test 1 does work
    @Test
    void unitTest()
    {
        assertEquals(5, 5);
    }

    //Test 2 doesnt work
    //@Test
   // void unitTest2()
   // {
        //assertEquals(5, 4);
  //  }

    // Test 3 :add delta : the maximum allowed difference between expected and actual values for floating point precision
    @Test
    void unitTest4()
    {
        assertEquals(5.0, 5.01, 0.02);
    }

    // Test 4 : does a = b
    @Test
    void unitTest5()
    {
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};
        assertArrayEquals(a, b);
    }

    // Test 5 ias the number even ?
    @Test
    void testIsEven() {
        int num = 8;
        assertTrue(num % 2 == 0, "Number should be even");
    }

    // Test 6 is testing if number is odd
    @Test
    void testIsOdd() {
        int num = 7;
        assertTrue(num % 2 != 0, "Number should be odd");
    }

    // Test 7
    @Test
    void unitTest8()
    {
        assertNull(null);
    }

    // Test 8 not null( so needs writing )
    @Test
    void unitTest9()
    {
        assertNotNull("Hello");
    }


    // illustrates how to test if a method throws an exception. By default, any exception
    // thrown fails a test if no assertThrows matches.
    @Test
    void unitTest10()
    {
        assertThrows(NullPointerException.class, this::throwsException);
    }

    void throwsException() throws NullPointerException
    {
        throw new NullPointerException();
    }

    // Test 11 is testing if the sentence has a specific word
    @Test
    void testStringContainsWord() {
        String sentence = "JUnit tests are fun";
        assertTrue(sentence.contains("fun"), "Sentence should contain 'fun'");
    }
}