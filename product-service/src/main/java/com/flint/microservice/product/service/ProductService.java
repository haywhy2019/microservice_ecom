package com.flint.microservice.product.service;

import com.flint.microservice.product.dto.ProductRequest;
import com.flint.microservice.product.dto.ProductResponse;

public interface ProductService {
    public ProductResponse createProduct(ProductRequest productRequest);
    public java.util.List<ProductResponse> getProducts();
}
