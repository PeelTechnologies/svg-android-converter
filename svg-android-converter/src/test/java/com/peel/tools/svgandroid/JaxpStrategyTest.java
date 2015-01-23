// Copyright (C) 2015 Peel Inc.
package com.peel.tools.svgandroid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.io.StringWriter;

import org.junit.Test;
import org.w3c.dom.Document;

/**
 * Unit test for {@link JaxpStrategy}.
 *
 * @author Inderjeet Singh
 */
public class JaxpStrategyTest {

    @Test
    public void testConversion() throws Exception {
        StringWriter output = new StringWriter();
        InputStream input = JaxpStrategyTest.class.getResourceAsStream("/sample.svg");
        JaxpStrategy strategy = new JaxpStrategy();
        Document document = strategy.read(input);
        strategy.convert(document, output);
        String xml = output.toString();
        System.out.println(xml);
        assertTrue(xml.contains("vector"));
        assertTrue(xml.contains("xmlns=\"http://schemas.android.com/apk/res/android\""));
        assertTrue(xml.contains("fillColor"));
        assertTrue(xml.contains("pathData"));
    }

    @Test
    public void testPtToDp() throws Exception {
        assertEquals("866dp", JaxpStrategy.ptToDp("866pt"));
    }
}
