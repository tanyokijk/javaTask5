package com.aimiko.task5.persistence.repository;

import java.nio.file.Path;

public enum TxtPathFactory {
    USERS("users.txt");

    private static final String DATA_DIRECTORY = "data";
    private final String fileName;

    TxtPathFactory(String fileName) {
        this.fileName = fileName;
    }

    public Path getPath() {
        return Path.of(DATA_DIRECTORY, this.fileName);
    }
}
