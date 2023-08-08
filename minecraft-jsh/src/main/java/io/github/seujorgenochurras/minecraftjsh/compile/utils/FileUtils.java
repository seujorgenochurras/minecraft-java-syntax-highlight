package io.github.seujorgenochurras.minecraftjsh.compile.utils;

import java.io.*;
import java.util.NoSuchElementException;

public class FileUtils {
    private FileUtils() {
    }

    public static String getFileAsString(File file) {
        StringBuilder fileAsString = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                fileAsString.append(currentLine).append("\n");
            }

        } catch (IOException e) {
            throw new NoSuchElementException(e);
        }
        return fileAsString.toString();
    }

    public static byte[] getFileBytes(File file) {
        byte[] fileBytes;

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            fileBytes = fileInputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileBytes;
    }
}
