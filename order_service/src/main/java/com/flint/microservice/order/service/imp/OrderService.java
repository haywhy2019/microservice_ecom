package com.flint.microservice.order.service.imp;

import com.flint.microservice.order.dto.OrderRequest;

public interface OrderService {
     void placeOrder(OrderRequest orderRequest);
}
