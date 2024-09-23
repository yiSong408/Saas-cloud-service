package net.cloud.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.cloud.enums.ClientType;
import net.cloud.enums.ProductOrderPayTypeEnum;
import net.cloud.request.ConfirmOrderRequest;
import net.cloud.service.ProductOrderService;
import net.cloud.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Rongyi
 * @since 2024-09-23
 */
@Api("Order module")
@RestController
@RequestMapping("/productOrderDO")
@Slf4j
public class ProductOrderController {
    @Autowired
    private ProductOrderService productOrderService;

    @ApiOperation("submit order")
    @PostMapping("confirm")
    public void confirmOrder(@ApiParam("Order Object") @RequestBody ConfirmOrderRequest orderRequest, HttpServletResponse response) {
        JsonData jsonData = productOrderService.confirmOrder(orderRequest);
        if (jsonData.getCode() == 0) {
            String clientType = orderRequest.getClient_type();
            String payType = orderRequest.getPayType();
            if (payType.equalsIgnoreCase(ProductOrderPayTypeEnum.ALIPAY.name())) {
                log.info("create success {}", orderRequest);
                if (clientType.equalsIgnoreCase(ClientType.H5.name())) {
                    writeData(response, jsonData);
                } else if (clientType.equalsIgnoreCase(ClientType.APP.name())) {
                    // TODO
                }
            } else if (payType.equalsIgnoreCase(ProductOrderPayTypeEnum.WECHAT.name())) {
                // TODO
            }
        } else {
            log.error("create order failed {}", jsonData.toString());
        }
    }

    private void writeData(HttpServletResponse response, JsonData jsonData) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonData.getData().toString());
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            log.error("Output html exception {}", e.toString());
        }
    }

}

