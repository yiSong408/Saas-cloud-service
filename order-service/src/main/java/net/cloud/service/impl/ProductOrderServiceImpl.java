package net.cloud.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.cloud.request.ConfirmOrderRequest;
import net.cloud.service.ProductOrderService;
import net.cloud.util.JsonData;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductOrderServiceImpl implements ProductOrderService {
    @Override
    public JsonData confirmOrder(ConfirmOrderRequest orderRequest) {
        return null;
    }
}
