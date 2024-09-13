package net.cloud.service;

import net.cloud.enums.CouponCategoryEnum;
import net.cloud.util.JsonData;

import java.util.Map;

public interface CouponService {
    Map<String, Object> getCouponPageData(int page, int size);


    JsonData addCoupon(long couponId, CouponCategoryEnum category);
}
