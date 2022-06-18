package com.kma.fitnesssmc.data.manager;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class FileManager {
    public static final String FILE_DATA_STORAGE = "data.txt";

    private static FileManager instance = null;

    private FileManager() {

    }

    public File getDataStorage() {
        return new File(FILE_DATA_STORAGE);
    }

    public static @NotNull FileManager getInstance() {
        return instance == null ? instance = new FileManager() : instance;
    }
}
