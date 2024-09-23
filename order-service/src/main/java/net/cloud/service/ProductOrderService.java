package net.cloud.service;

import net.cloud.request.ConfirmOrderRequest;
import net.cloud.util.JsonData;

public interface ProductOrderService {
    JsonData confirmOrder(ConfirmOrderRequest orderRequest);
}
