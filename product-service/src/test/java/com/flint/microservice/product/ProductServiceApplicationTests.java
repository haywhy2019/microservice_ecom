package com.flint.microservice.product;

import com.flint.microservice.product.dto.ProductRequest;
import com.flint.microservice.product.dto.ProductResponse;
import com.flint.microservice.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductServiceApplicationTests {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

	@Test
	void shouldCreateProduct() {
		ProductRequest productRequest = new ProductRequest(null, "Laptop", "Gaming laptop", new BigDecimal("1299.99"));

		ProductResponse response = productService.createProduct(productRequest);

		assertThat(response).isNotNull();
		assertThat(response.name()).isEqualTo("Laptop");
		assertThat(response.price()).isEqualByComparingTo(new BigDecimal("1299.99"));
		assertThat(productRepository.findAll())
				.anySatisfy(product -> {
					assertThat(product.getName()).isEqualTo("Laptop");
					assertThat(product.getDescription()).isEqualTo("Gaming laptop");
					assertThat(product.getPrice()).isEqualByComparingTo(new BigDecimal("1299.99"));
				});
	}
}
