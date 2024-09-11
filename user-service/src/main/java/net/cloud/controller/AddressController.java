package net.cloud.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.cloud.enums.BizCodeEnum;
import net.cloud.interceptor.LoginInterceptor;
import net.cloud.model.LoginUser;
import net.cloud.request.AddressAddRequest;
import net.cloud.service.AddressService;
import net.cloud.util.JsonData;
import net.cloud.vo.AddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Rongyi
 * @since 2024-08-13
 */
@Api(tags = "Address module")
@RestController
@RequestMapping("/api/address/v1")
public class AddressController {
    @Autowired
    AddressService addressService;

    @ApiOperation("add an address")
    @PostMapping("add")
    public JsonData add(@ApiParam("request body for add address") @RequestBody AddressAddRequest addressAddRequest) {
        addressService.add(addressAddRequest);
        return JsonData.buildSuccess();
    }

    @ApiOperation("find an address")
    @GetMapping("get/{address_id}")
    public JsonData get(@ApiParam(value = "address id", required = true) @PathVariable("address_id") int addressId) {
        AddressVO addressVO = addressService.get(addressId);
        return addressVO == null ? JsonData.buildResult(BizCodeEnum.ADDRESS_NOT_EXIST) : JsonData.buildSuccess(addressVO);
    }

    @ApiOperation("delete an address")
    @GetMapping("delete/{address_id}")
    public JsonData delete(@ApiParam(value = "address id", required = true) @PathVariable("address_id") int addressId) {
        int row = addressService.delete(addressId);
        return row == 0 ? JsonData.buildResult(BizCodeEnum.ADDRESS_NOT_EXIST) : JsonData.buildSuccess();
    }

    @ApiOperation("Find all addresses for user")
    @GetMapping("get_all")
    public JsonData findAll() {
        List<AddressVO> list = addressService.findAllUserAddress();
        return JsonData.buildSuccess(list);
    }
}

