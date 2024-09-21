package net.cloud.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cloud.service.BannerService;
import net.cloud.util.JsonData;
import net.cloud.vo.BannerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Rongyi
 * @since 2024-09-21
 */
@Api("Banner module")
@RestController
@RequestMapping("/api/banner/v1")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @ApiOperation("Carousel present api")
    @GetMapping("list")
    public JsonData list(){
        List<BannerVO> list = bannerService.list();
        return JsonData.buildSuccess(list);
    }
}

