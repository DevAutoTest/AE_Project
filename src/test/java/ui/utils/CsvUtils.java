package ui.utils;

import org.junit.jupiter.params.provider.Arguments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Stream;

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
            throw new RuntimeException("Error during reading the CSV: " + resourcePath, e);
        }
    }

    public static Stream<Arguments> readCategoryUrlPairsFromCsv() {
        return new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(CsvUtils.class.getResourceAsStream("/testData/MenuCategories.csv")),
                        StandardCharsets.UTF_8
                )
        ).lines()
                .skip(0) // titels
                .map(line -> {
                    String[] parts = line.split(",", 2);
                    return Arguments.of(parts[0].trim(), parts[1].trim());
                });
    }
}
