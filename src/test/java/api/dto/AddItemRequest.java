package api.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
public class AddItemRequest {
    private List<Item> items;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        @CsvBindByName(column = "skuId")
        private String skuId;
        @CsvBindByName(column = "quantity")
        private int quantity;
    }
}