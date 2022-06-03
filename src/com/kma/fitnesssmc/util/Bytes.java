package com.kma.fitnesssmc.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Bytes {

    public static byte @Nullable [] fromFile(@NotNull File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];

            fileInputStream.read(buffer);
            fileInputStream.close();
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
