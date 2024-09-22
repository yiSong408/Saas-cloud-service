package net.cloud.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class CartVO {
    @JsonProperty("cart_items")
    private List<CartItemVO> cartItems;
    @JsonProperty("total_amount")
    private Integer totalAmount;
    @JsonProperty("total_price")
    private BigDecimal totalPrice;
    @JsonProperty("real_price")
    private BigDecimal realPrice;

    public List<CartItemVO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemVO> cartItems) {
        this.cartItems = cartItems;
    }

    public Integer getTotalAmount() {
        if (this.cartItems != null) {
            return cartItems.stream().mapToInt(CartItemVO::getAmount).sum();
        }
        return 0;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal amount = new BigDecimal(0);
        if (this.cartItems != null) {
            for (CartItemVO item : cartItems) {
                BigDecimal singleTotalPrice = item.getTotalPrice();
                amount = amount.add(singleTotalPrice);
            }
        }
        return amount;
    }

    public BigDecimal getRealPrice() {
        BigDecimal amount = new BigDecimal(0);
        if (this.cartItems != null) {
            for (CartItemVO item : cartItems) {
                BigDecimal singleTotalPrice = item.getTotalPrice();
                amount = amount.add(singleTotalPrice);
            }
        }
        return amount;
    }
}
