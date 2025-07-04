package api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CartResponse {
    private CartData data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class CartData {
        public String id;
        public String currencyCode;
        public Summary summary;
        public List<ItemResponse> items;
        public List<Object> warnings;
        public String userType;
        public String profileId;
        public int itemCount;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ItemResponse {
        private String itemId;
        private String productId;
        private String productName;
        private String size;
        private String color;
        private String sku;
        private int quantity;
        private double price;
        private double originalPrice;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Summary {
        public String id;
        public float shipping;
        public float shippingTax;
        public float subtotal;
        public float discount;
        public float donation;
        public float subtotalMinusDiscount;
        public float tax;
        public float shippingItemsCost;
        public float pickupItemsCost;
        public float total;
        public float amountUntilFreeShipping;
        public float freeShippingThreshold;
        public float giftCardTotal;
        public float giftCardStandardTotal;
        public float giftCardInstantCreditTotal;
        public List<Object> appliedCoupons;
        public float creditSavingsAmount;
        public float netTotal;
    }

}
