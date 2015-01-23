// Copyright (C) 2015 Peel Inc.
package com.peel.tools.svgandroid;

import java.io.InputStream;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

/**
 * Interface to convert SVG to Android.
 *
 * @author Inderjeet Singh
 */
public class SvgToAndroidStrategy {
    public Document read(InputStream input) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(false);  // factory.setNamespaceAware(true);
        factory.setValidating(false);  // factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(input);
    }

    public void convert(Document input, Writer output) throws Exception {
    }
}
