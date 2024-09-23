package net.cloud.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ConfirmOrderRequest {
    @JsonProperty("coupon_record_id")
    private Long couponRecordId;
    @JsonProperty("product_ids")
    private List<Long> productIdList;
    @JsonProperty("pay_type")
    private String payType;
    @JsonProperty("client_type")
    private String client_type;
    @JsonProperty("address_id")
    private long addressId;
    @JsonProperty("total_price")
    private BigDecimal totalPrice;
    @JsonProperty("real_price")
    private BigDecimal realPrice;
    /**
     * anti-repeat
     */
    private String token;
}
