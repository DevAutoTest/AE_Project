package api.utils;

import api.dto.AddItemRequest;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


public class CsvReader {
    public static List<AddItemRequest.Item> readItemsFromCsv(String fileName) {
        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new RuntimeException("CSV file not found: " + fileName);
        }

        return new CsvToBeanBuilder<AddItemRequest.Item>(new InputStreamReader(inputStream))
                .withType(AddItemRequest.Item.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
                .parse();
    }
}
