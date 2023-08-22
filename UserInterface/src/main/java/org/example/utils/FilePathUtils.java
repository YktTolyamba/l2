package org.example.utils;

import aquality.selenium.logging.LocalizedLoggerUtility;
import aquality.selenium.logging.LogLevel;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class FilePathUtils {
    public static String getStringPath(String resourceName) {
        URI uri = null;

        try {
            uri = ClassLoader.getSystemResource(resourceName).toURI();
        } catch (URISyntaxException e) {
            LocalizedLoggerUtility.logByLevel(LogLevel.ERROR, "Error occurred while getting string path of a file!");
            throw new RuntimeException(e);
        }

        return Paths.get(uri).toString();
    }
}
