package net.cloud.service;

import net.cloud.request.CartItemRequest;
import net.cloud.vo.CartVO;

public interface CartService {
    void addToCart(CartItemRequest cartItemRequest);

    void clearCart();

    CartVO listAll();
}
