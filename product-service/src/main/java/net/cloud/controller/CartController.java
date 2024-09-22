package net.cloud.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cloud.request.CartItemRequest;
import net.cloud.service.CartService;
import net.cloud.util.JsonData;
import net.cloud.vo.CartVO;
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

    @ApiOperation("clear my cart")
    @GetMapping("clear")
    public JsonData clearCart() {
        cartService.clearCart();
        return JsonData.buildSuccess();
    }

    @ApiOperation("List all products in cart")
    @GetMapping("list")
    public JsonData getAllInCart() {
        CartVO cartVO = cartService.listAll();
        return JsonData.buildSuccess(cartVO);
    }

    @ApiOperation("delete product in cart")
    @DeleteMapping("delete/{product_id}")
    public JsonData delete(@ApiParam(value = "product id", required = true) @PathVariable("product_id") long productId) {
        cartService.deleteItem(productId);
        return JsonData.buildSuccess();
    }

    @ApiOperation("update product to cart")
    @PostMapping("update")
    public JsonData updateToCart(@ApiParam("product item") @RequestBody CartItemRequest cartItemRequest) {
        cartService.updateToCart(cartItemRequest);
        return JsonData.buildSuccess();
    }
}
