package ui.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CsvUtils {

    public int getCsvRowCount(String resourcePath) {
        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                return (int) reader.lines().count();
            }

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении CSV: " + resourcePath, e);
        }
    }
}
