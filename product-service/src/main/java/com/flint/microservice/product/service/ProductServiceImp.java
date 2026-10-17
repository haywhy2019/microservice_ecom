package com.flint.microservice.product.service;

import com.flint.microservice.product.ProductRepository;
import com.flint.microservice.product.dto.ProductRequest;
import com.flint.microservice.product.dto.ProductResponse;
import com.flint.microservice.product.model.Product;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
        return new ProductResponse(
               String.valueOf(product.getId()),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

   public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream()
                .map(product -> new ProductResponse(
                       String.valueOf(product.getId()),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()
                ))
                .collect(Collectors.toList());
    }

}
