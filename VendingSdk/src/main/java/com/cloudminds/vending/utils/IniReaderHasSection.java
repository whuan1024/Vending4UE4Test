package com.cloudminds.vending.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * https://blog.csdn.net/caoguangguang/article/details/8333263
 */
public class IniReaderHasSection {

    private Map sections;
    private String secion;
    private Properties properties;

    public IniReaderHasSection(String filename) throws IOException {
        sections = new HashMap();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        read(reader);
        reader.close();
    }

    private void read(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            parseLine(line);
        }
    }

    private void parseLine(String line) {
        line = line.trim();
        if (line.matches("\\[.*\\]")) {
            secion = line.replaceFirst("\\[(.*)\\]", "$1");
            properties = new Properties();
            sections.put(secion, properties);
        } else if (line.matches(".*=.*")) {
            if (properties != null) {
                int i = line.indexOf('=');
                String name = line.substring(0, i);
                String value = line.substring(i + 1);
                properties.setProperty(name, value);
            }
        }
    }

    public String getValue(String section, String name) {
        String value = "";
        Properties p = (Properties) sections.get(section);
        if (p != null) {
            value = p.getProperty(name);
        }
        return value;
    }
}
