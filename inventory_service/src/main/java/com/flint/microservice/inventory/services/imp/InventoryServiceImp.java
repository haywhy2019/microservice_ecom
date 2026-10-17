package com.flint.microservice.inventory.services.imp;

import com.flint.microservice.inventory.model.Inventory;
import com.flint.microservice.inventory.repository.InventoryRepository;
import com.flint.microservice.inventory.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImp implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode, int quantity) {
        return inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode, quantity);
    }
}
