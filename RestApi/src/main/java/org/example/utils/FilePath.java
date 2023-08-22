package org.example.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePath {

    public static Path getPath(String resourceName) {
        URI uri = null;

        try {
            uri = ClassLoader.getSystemResource(resourceName).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String mainPath = Paths.get(uri).toString();
        Path path = Paths.get(mainPath);

        return path;
    }
}
