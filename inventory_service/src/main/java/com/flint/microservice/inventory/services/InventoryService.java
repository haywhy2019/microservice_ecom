package com.flint.microservice.inventory.services;

public interface InventoryService {

    boolean isInStock(String skuCode, int quantity);
}
