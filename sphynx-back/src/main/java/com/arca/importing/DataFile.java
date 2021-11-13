package com.arca.importing;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Classe qui représente un fichier.
 */
public class DataFile {
    // Chemin du fichier
    private final Path path;

    // Constructeurs
    public DataFile(Path path) {
        this.path = path;
    }

    public DataFile(String path) {
        this.path = path != null ? Paths.get(path) : null;
    }

    // Vérifie si le fichier existe
    public boolean exist() {
        Path filePath = getPath();
        return this.path != null && Files.exists(filePath);
    }

    // Retourne le chemin du fichier
    public Path getPath() {
        return path;
    }
}
