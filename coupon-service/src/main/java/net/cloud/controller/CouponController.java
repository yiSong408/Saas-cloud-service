package net.cloud.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.cloud.enums.CouponCategoryEnum;
import net.cloud.service.CouponService;
import net.cloud.util.JsonData;
import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Rongyi
 * @since 2024-09-12
 */
@Api(tags = "coupon module")
@RestController
@RequestMapping("api/coupon/v1")
public class CouponController {
    @Autowired
    private CouponService couponService;
    @ApiOperation("get pagination coupon data")
    @GetMapping("page_coupon")
    public JsonData pageCouponData(
            @ApiParam("current page number") @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam("size of each page") @RequestParam(value = "size", defaultValue = "5") int size
    ){
        Map<String, Object> map = couponService.getCouponPageData(page,size);
        return JsonData.buildSuccess(map);
    }
    @ApiOperation("get a coupon")
    @GetMapping("add/{coupon_id}")
    public JsonData addCouponRecord(@ApiParam("coupon id") @PathVariable("coupon_id") int couponId){
        return couponService.addCoupon(couponId, CouponCategoryEnum.PROMOTION);
    }
}

