package com.thestreetcodecompany.roady;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by fklezin on 30/04/2018.
 */

public class InfosActivityTest {
    @Test
    public void testConvertFahrenheitToCelsius() {
        float actual = 11;
        // expected value is 212
        float expected = 212;
        // use this method because float is not precise
        assertEquals("Conversion from celsius to fahrenheit failed", expected, actual, 0.001);
    }

    @Test
    public void testConvertCelsiusToFahrenheit() {
        float actual = 100;
        // expected value is 100
        float expected = 100;
        // use this method because float is not precise
        assertEquals("Conversion from celsius to fahrenheit failed", expected, actual, 0.001);
    }

}
