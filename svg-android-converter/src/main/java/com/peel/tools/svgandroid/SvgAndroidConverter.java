// Copyright (C) 2015 Peel Inc.
package com.peel.tools.svgandroid;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;

import org.w3c.dom.Document;

/**
 * SVG to Android Vector Drawable converter.
 *
 * @author Inderjeet Singh
 */
public class SvgAndroidConverter {
    public static void main(String[] argv) throws Exception {
        if (argv.length != 2) {
            System.err.println("Usage: java App svgfile outputfile");
            System.exit(1);
        }
        InputStream input = new FileInputStream(argv[0]);
        FileWriter output = new FileWriter(argv[1]);
//        SvgToAndroidStrategy strategy = new XsltStrategy();
        SvgToAndroidStrategy strategy = new JaxpStrategy();
        Document document = strategy.read(input);
        strategy.convert(document, output);
        input.close();
        output.close();
    }
}
