package net.cloud.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.cloud.enums.BizCodeEnum;
import net.cloud.service.CouponRecordService;
import net.cloud.util.JsonData;
import net.cloud.vo.CouponRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Rongyi
 * @since 2024-09-12
 */
@RestController
@Api("Coupon record module")
@RequestMapping("api//coupon_record/v1")
public class CouponRecordController {
    @Autowired
    private CouponRecordService couponRecordService;

    @ApiOperation("record data query in page")
    @GetMapping("page")
    public JsonData recordPage(@ApiParam("page number") @RequestParam(value = "page", defaultValue = "1") int page
            , @ApiParam("size of page") @RequestParam(value = "size", defaultValue = "5") int size) {
        Map<String, Object> map = couponRecordService.pageData(page, size);
        return JsonData.buildSuccess(map);
    }

    @ApiOperation("Get coupon record detailed data")
    @GetMapping("detail/{record_id}")
    public JsonData couponDetail(@ApiParam("record id") @PathVariable("record_id") long recordId) {
        CouponRecordVO recordVO = couponRecordService.findById(recordId);
        return recordVO == null ? JsonData.buildResult(BizCodeEnum.COUPON_NOT_EXIST) : JsonData.buildSuccess(recordVO);
    }

}

