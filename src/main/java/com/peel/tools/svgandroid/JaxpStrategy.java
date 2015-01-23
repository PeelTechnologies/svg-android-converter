// Copyright (C) 2014 Peel Inc.
package com.peel.tools.svgandroid;

import java.io.StringWriter;
import java.io.Writer;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Uses JAXP DOM APIs directly to convert the SVG document to a vector drawable.
 *
 * @author Inderjeet Singh
 */
public final class JaxpStrategy extends SvgToAndroidStrategy {

    @Override
    public void convert(Document document, Writer output) throws Exception {
        StringWriter writer = new StringWriter();
        xmlConvert(document, writer);
        // do remaining changes by string substitution
        // TODO: figure out XML way of doing this
        String xml = writer.toString();
        xml = xml.replace("height", "android:height");
        xml = xml.replace("width", "android:width");
        xml = xml.replace("viewportHeight", "android:viewportHeight");
        xml = xml.replace("viewportWidth", "android:viewportWidth");
        xml = xml.replace(" standalone=\"no\"", "");
        xml = xml.replace(" xmlns=\"http://www.w3.org/2000/svg\"", "");
        xml = xml.replaceAll("pathData", "android:pathData");
        xml = xml.replaceAll("fillColor", "android:fillColor");
        output.append(xml);
    }

    private void xmlConvert(Document document, Writer output) throws Exception {
        transformSvgNode(document);
        addAndroidNamespaceAsDefault(document);
        replacePathDataAndFillColor(document);
        writeDomToXmlFile(document, output);
    }

    private void addAndroidNamespaceAsDefault(Document document) {
        Element svgElement = document.getDocumentElement();
        Element revisedSvgElement = document.createElementNS(
                "http://schemas.android.com/apk/res/android", svgElement.getNodeName());
        revisedSvgElement.setPrefix("android");
        NamedNodeMap attributes = svgElement.getAttributes();
        for (int i = 0; i < attributes.getLength(); ++i) {
            Node attribute = attributes.item(i);
            revisedSvgElement.setAttribute(attribute.getNodeName(), attribute.getTextContent());
        }
        NodeList nodes = svgElement.getChildNodes();
        while(nodes.getLength() != 0) {
            revisedSvgElement.appendChild(nodes.item(0));
        }
        document.replaceChild(revisedSvgElement, svgElement); // Replace the original element
    }

    private void transformSvgNode(Document document) {
        NodeList nodes = document.getElementsByTagName("svg");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            document.renameNode(node, null, "vector");
            NamedNodeMap attributes = node.getAttributes();
            Node width = attributes.getNamedItem("width");
            width.setTextContent(ptToDp(width.getTextContent()));
            Node height = attributes.getNamedItem("height");
            height.setTextContent(ptToDp(height.getTextContent()));
            attributes.removeNamedItem("version");
            String viewBox = attributes.getNamedItem("viewBox").getTextContent();
            attributes.removeNamedItem("viewBox");
            String[] parts = viewBox.split(" ");
            ((Element)node).setAttribute("viewportWidth", parts[2]);
            ((Element)node).setAttribute("viewportHeight", parts[3]);
        }
    }

    /** converts pt to dp. For example, 480pt to 480dp */
    static String ptToDp(String value) {
        return value.substring(0, value.length() - 2) + "dp";
    }

    private void replacePathDataAndFillColor(Document document) {
        NodeList nodes = document.getElementsByTagName("path");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            NamedNodeMap attributes = node.getAttributes();
            document.renameNode(attributes.getNamedItem("d"), null, "pathData");
            document.renameNode(attributes.getNamedItem("fill"), null, "fillColor");
        }
    }

    private void writeDomToXmlFile(Document document, Writer output)
            throws TransformerFactoryConfigurationError, TransformerConfigurationException,
            TransformerException {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);
    }
}
