package net.cloud.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CartItemVO {
    @JsonProperty("product_id")
    private long productId;
    private Integer amount;
    @JsonProperty("product_title")
    private String productTitle;
    @JsonProperty("product_img")
    private String productImg;
    private BigDecimal singlePrice;
    @JsonProperty("total_price")
    private BigDecimal totalPrice;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public BigDecimal getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(BigDecimal singlePrice) {
        this.singlePrice = singlePrice;
    }

    public BigDecimal getTotalPrice() {
        return this.singlePrice.multiply(new BigDecimal(this.amount));
    }
}
