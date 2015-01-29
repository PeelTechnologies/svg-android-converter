/*
 * Copyright (C) 2015 Peel Inc.
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
            System.err.println("Usage: svg-android-converter <svg-file> <vector-drawable-file>");
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
