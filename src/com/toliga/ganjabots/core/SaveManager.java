package com.toliga.ganjabots.core;

import com.toliga.ganjabots.path.ActionElement;
import com.toliga.ganjabots.path.Element;
import com.toliga.ganjabots.path.PathElement;
import com.toliga.ganjabots.path.PathProfile;
import org.dreambot.api.script.AbstractScript;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveManager {
    private File file;
    private Document document;

    public SaveManager(String fileName) {
        try {
            file = new File(fileName);
            if (!file.exists()) {
                if (!new File(file.getParent()).mkdirs()) {
                    AbstractScript.log("Could not create directory to save settings.");
                } else {
                    String xmlContent = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>\r\n" +
                                        "<settings>\r\n" +
                                        "   <genericSettings>\r\n" +
                                        "   </genericSettings>\r\n" +
                                        "   <profiles>\r\n" +
                                        "   </profiles>\r\n" +
                                        "</settings>";
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        writer.write(xmlContent);
                    }
                }
            }
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = builder.parse(file);
            document.getDocumentElement().normalize();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            AbstractScript.log("Error while parsing xml: " + e.getMessage() + "File: " + file.getAbsolutePath());
        }
    }

    public HashMap<String, String> loadSettings() {
        NodeList settingsList = document.getElementsByTagName("genericSettings");

        if (settingsList.getLength() == 1) {
            NodeList genericSettings = settingsList.item(0).getChildNodes();
            HashMap<String, String> settingsMap = new HashMap<>();

            for (int i = 0; i < genericSettings.getLength(); i++) {
                Node settingNode = genericSettings.item(i);

                if (settingNode.getNodeType() == Node.ELEMENT_NODE) {
                    settingsMap.put(settingNode.getNodeName(), settingNode.getTextContent());
                }
            }

            return settingsMap;
        }
        return null;
    }

    public List<PathProfile> loadProfiles() {
        List<PathProfile> result = new ArrayList<>();
        NodeList tagList = document.getElementsByTagName("profile");

        if (tagList.getLength() > 0) {
            for (int i = 0; i < tagList.getLength(); i++) {
                PathProfile pathProfile = new PathProfile(tagList.item(i).getAttributes().getNamedItem("name").getNodeValue());

                NodeList profileChildren = tagList.item(i).getChildNodes();

                for (int j = 0; j < profileChildren.getLength(); j++) {
                    Node profileNode = profileChildren.item(j);
                    if (profileNode.getNodeType() == Node.ELEMENT_NODE) {
                        String[] content = profileNode.getTextContent().split(",");

                        if (profileNode.getNodeName().equals("tile")) {
                            pathProfile.addElement(new PathElement(Integer.parseInt(content[0]), Integer.parseInt(content[1])));
                        } else {
                            pathProfile.addElement(new ActionElement(Integer.parseInt(content[0]), content[1]));
                        }
                    }
                }

                result.add(pathProfile);
            }
            return result;
        }
        return null;
    }

    public void saveSettings(HashMap<String, String> pairs) {
        NodeList settingsList = document.getElementsByTagName("genericSettings");

        if (settingsList.getLength() == 1) {
            Node genericSettings = settingsList.item(0);

            for (Map.Entry<String, String> entry : pairs.entrySet()) {
                Node item;
                if ((item = containsNode(genericSettings.getChildNodes(), entry.getKey())) != null) {
                    item.setTextContent(entry.getValue());
                } else {
                    genericSettings.appendChild(document.createElement(entry.getKey())).setTextContent(entry.getValue());
                }
            }

            try {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveSetting(String key, String value) {
        NodeList settingsList = document.getElementsByTagName("genericSettings");

        if (settingsList.getLength() == 1) {
            Node genericSettings = settingsList.item(0);

            Node item;
            if ((item = containsNode(genericSettings.getChildNodes(), key)) != null) {
                item.setTextContent(value);
            } else {
                genericSettings.appendChild(document.createElement(key)).setTextContent(value);
            }
        }
    }

    public void saveProfile(PathProfile profile) {
        NodeList profilesList = document.getElementsByTagName("profiles");

        if (profilesList.getLength() == 1) {
            Node profiles = profilesList.item(0);

            org.w3c.dom.Element profileElement = document.createElement("profile");
            profileElement.setAttribute("name", profile.getName());

            for (Element element : profile.getPathsAndActions()) {
                String elementName;
                String textContent;
                if (element instanceof PathElement) {
                    elementName = "tile";
                    textContent = ((PathElement)element).x + "," + ((PathElement)element).y;
                } else {
                    elementName = "action";
                    textContent = ((ActionElement)element).objectID + "," + ((ActionElement)element).actionName;
                }

                org.w3c.dom.Element internalElement = document.createElement(elementName);
                internalElement.setTextContent(textContent);

                profileElement.appendChild(internalElement);
            }

            profiles.appendChild(profileElement);

            try {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
    }

    private Node containsNode(NodeList nodeList, String nodeName) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeName().equals(nodeName)) {
                return nodeList.item(i);
            }
        }
        return null;
    }
}
