package com.toliga.ganjabots.core;

import org.dreambot.api.script.AbstractScript;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SaveManager {
    private FileManager fileManager;

    public SaveManager(String fileName) {
        fileManager = new FileManager(fileName);
    }

    public void saveOptions(HashMap<String, String> pairs) {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            fileManager.openWriter(false);

            fileManager.writeLineToFile("# Do not edit the file.");
            fileManager.writeLineToFile("# It may be unstable if you edit it wrong.\r\n");

            for (Map.Entry<String, String> entry : pairs.entrySet()) {
                AbstractScript.log("Saving: " + entry.getValue());
                fileManager.writeLineToFile(entry.getKey() + "=" + entry.getValue());
            }

            fileManager.closeWriter();
        } else {
            AbstractScript.log("OS is not Windows. Skipping save feature.");
        }
    }

    public void saveOption(String key, String value) {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            fileManager.openWriter(true);

            fileManager.writeLineToFile(key + "=" + value);

            fileManager.closeWriter();
        } else {
            AbstractScript.log("OS is not Windows. Skipping save feature.");
        }
    }

    public LinkedHashMap<String, String> loadAll() {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            LinkedHashMap<String, String> result = new LinkedHashMap<>();

            if (fileManager.isFileExists()) {
                fileManager.openReader();

                String line;
                while ((line = fileManager.readLineFromFile()) != null) {
                    if (line.startsWith("#") || line.isEmpty()) continue;

                    String[] keyValue = line.split("=");
                    if (keyValue.length == 2) {
                        result.put(keyValue[0], keyValue[1]);
                    }
                    AbstractScript.log("Loading: " + keyValue[0]);
                }

                fileManager.closeReader();
            } else {
                AbstractScript.log("File is not exist.");
            }

            return result;
        } else {
            AbstractScript.log("OS is not Windows. Skipping save feature.");
            return null;
        }
    }
}
