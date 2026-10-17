package com.flint.microservice.inventory.dto;

public record InventoryRequest(
        Long id, String skuCode
) {
}
