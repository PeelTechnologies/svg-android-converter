/*
 * Copyright (C) 2015 Peel Technologies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peel.tools.svgandroid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
    public void testJaxpConversion() throws Exception {
        StringWriter output = new StringWriter();
        InputStream input = JaxpStrategyTest.class.getResourceAsStream("/sample.svg");
        JaxpStrategy strategy = new JaxpStrategy();
        Document document = strategy.read(input);
        strategy.convert(document, output);
        String xml = output.toString();
        System.out.println(xml);
        assertTrue(xml.contains("android:vector"));
        assertTrue(xml.contains("xmlns:android=\"http://schemas.android.com/apk/res/android\""));
        assertTrue(xml.contains("android:fillColor"));
        assertTrue(xml.contains("android:pathData"));
        assertFalse(xml.contains("xmlns=\"http://www.w3.org/2000/svg\""));
    }

    @Test
    public void testJaxp2() throws Exception {
        StringWriter output = new StringWriter();
        InputStream input = JaxpStrategyTest.class.getResourceAsStream("/sample2.svg");
        JaxpStrategy strategy = new JaxpStrategy();
        Document document = strategy.read(input);
        strategy.convert(document, output);
        String xml = output.toString();
        System.out.println(xml);
        assertFalse(xml.contains("none"));
    }

    @Test
    public void testPtToDp() throws Exception {
        assertEquals("866dp", JaxpStrategy.ptToDp("866pt"));
    }
}
