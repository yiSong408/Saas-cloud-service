package net.cloud.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.cloud.service.ProductService;
import net.cloud.util.JsonData;
import net.cloud.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Rongyi
 * @since 2024-09-21
 */
@Api("Product module")
@RestController
@RequestMapping("/api/product/v1")
public class ProductController {
    @Autowired
    private ProductService productService;

    @ApiOperation("Page data for product")
    @GetMapping("page")
    public JsonData page_product(@ApiParam("page number") @RequestParam(value = "page", defaultValue = "1") int page
            , @ApiParam("size of page") @RequestParam(value = "size", defaultValue = "5") int size) {
        Map<String, Object> map = productService.pageList(page, size);
        return JsonData.buildSuccess(map);
    }

    @ApiOperation("Get product detail data")
    @GetMapping("detail/{product_id}")
    public JsonData detail(@ApiParam(value = "product id", required = true) @PathVariable("product_id") long productId) {
        ProductVO productVO = productService.getProductDetail(productId);
        return JsonData.buildSuccess(productVO);
    }
}

