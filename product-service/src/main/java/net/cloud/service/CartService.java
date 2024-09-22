package net.cloud.service;

import net.cloud.request.CartItemRequest;

public interface CartService {
    void addToCart(CartItemRequest cartItemRequest);
}
