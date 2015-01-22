// Copyright (C) 2015 Peel Inc.
package com.peel.tools.svgandroid;

import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.io.StringWriter;

import org.junit.Test;

/**
 * Unit test for {@link SvgAndroidConverter}.
 *
 * @author Inderjeet Singh
 */
public class SvgAndroidConverterTest {

    @Test
    public void testConversion() throws Exception {
        StringWriter output = new StringWriter();
        InputStream input = SvgAndroidConverterTest.class.getResourceAsStream("/sample.svg");
        SvgAndroidConverter.stylize(input, output);
        String xml = output.toString();
        System.out.println(xml);
        assertTrue(xml.contains("android:fillColor"));
        assertTrue(xml.contains("android:pathData"));
    }
}
