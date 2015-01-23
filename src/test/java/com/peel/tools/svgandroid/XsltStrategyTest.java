// Copyright (C) 2015 Peel Inc.
package com.peel.tools.svgandroid;

import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.io.StringWriter;

// import org.junit.Test;
import org.w3c.dom.Document;

/**
 * Unit test for {@link XsltStrategy}.
 *
 * @author Inderjeet Singh
 */
public class XsltStrategyTest {

    // @Test
    public void testConversion() throws Exception {
        StringWriter output = new StringWriter();
        InputStream input = XsltStrategyTest.class.getResourceAsStream("/sample.svg");
        XsltStrategy strategy = new XsltStrategy();
        Document document = strategy.read(input);
        strategy.convert(document, output);
        String xml = output.toString();
        System.out.println(xml);
        assertTrue(xml.contains("xmlns:android=\"http://schemas.android.com/apk/res/android\""));
        assertTrue(xml.contains("android:fillColor"));
        assertTrue(xml.contains("android:pathData"));
    }
}
