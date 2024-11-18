package com.pbo.ipcalculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OktetTest {
    
    public OktetTest() {
    }

    @Test
    public void test1() {
        try {
            Oktet test = new Oktet("127");
            assertEquals(127, test.getValue());
        } catch (Exception ex) {
            assertNotEquals("value is out of range", ex.getMessage());
        }
    }

    @Test
    public void test2() {
        try {
            Oktet test = new Oktet("256");
        } catch (Exception ex) {
            assertEquals("value is out of range", ex.getMessage());
        }
    }

    // New unit tests for remaining methods

    //testConstructorWithIntValue
    @Test
    public void test3() {
        try {
            Oktet test = new Oktet(200);
            assertEquals(200, test.getValue());
        } catch (Exception ex) {
            fail("Exception should not be thrown for value in range: " + ex.getMessage());
        }
    }

    //testConstructorWithOutOfRangeIntValue
    @Test
    public void test4() {
        Exception exception = assertThrows(Exception.class, () -> {
            new Oktet(300);
        });
        assertEquals("value is out of range", exception.getMessage());
    }

    //testSetValueWithValidInt
    @Test
    public void test5() {
        try {
            Oktet test = new Oktet();
            test.setValue(150);
            assertEquals(150, test.getValue());
        } catch (Exception ex) {
            fail("Exception should not be thrown for value in range: " + ex.getMessage());
        }
    }

    //testSetValueWithInvalidInt
    @Test
    public void test6() {
        Oktet test = new Oktet();
        Exception exception = assertThrows(Exception.class, () -> {
            test.setValue(300);
        });
        assertEquals("value is out of range", exception.getMessage());
    }

    //testSetValueWithValidString
    @Test
    public void test7() {
        try {
            Oktet test = new Oktet();
            test.setValue("100");
            assertEquals(100, test.getValue());
        } catch (Exception ex) {
            fail("Exception should not be thrown for valid string value: " + ex.getMessage());
        }
    }

    //testSetValueWithInvalidStringFormat
    @Test
    public void test8() {
        Oktet test = new Oktet();
        Exception exception = assertThrows(Exception.class, () -> {
            test.setValue("invalid");
        });
        assertEquals("Invalid integer value: invalid", exception.getMessage());
    }

    //testSetValueWithOutOfRangeStringValue
    @Test
    public void test9() {
        Oktet test = new Oktet();
        Exception exception = assertThrows(Exception.class, () -> {
            test.setValue("300");
        });
        assertEquals("value is out of range", exception.getMessage());
    }
}
