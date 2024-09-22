package net.cloud.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cloud.request.CartItemRequest;
import net.cloud.service.CartService;
import net.cloud.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("Cart")
@RestController
@RequestMapping("/api/cart/v1")
public class CartController {
    @Autowired
    private CartService cartService;

    @ApiOperation("add product to cart")
    @PostMapping("add")
    public JsonData addToCart(@ApiParam("product item") @RequestBody CartItemRequest cartItemRequest) {
        cartService.addToCart(cartItemRequest);
        return JsonData.buildSuccess();
    }

}
