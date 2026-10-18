package com.flint.microservice.order.controller;


import com.flint.microservice.order.dto.OrderRequest;
import com.flint.microservice.order.service.imp.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor

public class OrderController {

//    private final OrderService orderServiceImpl;

    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
//        orderServiceImpl.placeOrder(orderRequest);
        log.info("Order placed successfully for product");
        return "Order Placed Successfully";
    }
}
