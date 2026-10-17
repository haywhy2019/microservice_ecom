package com.flint.microservice.order;

import com.flint.microservice.order.dto.OrderRequest;
import com.flint.microservice.order.model.Order;
import com.flint.microservice.order.repository.OrderRepository;
import com.flint.microservice.order.service.imp.OrderServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceimplApplicationTests {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private com.flint.microservice.order.client.InventoryClient inventoryClient;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

	@Test
	void shouldSubmitOrder() {
        OrderRequest request = new OrderRequest(
                null,
                null,
                "iphone_15",
                new BigDecimal("1000.00"),
                1
        );

        // inventory client should report product is in stock
        org.mockito.Mockito.when(inventoryClient.isInStock("iphone_15", 1)).thenReturn(true);

        orderServiceImpl.placeOrder(request);

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captor.capture());

        Order savedOrder = captor.getValue();

        assertThat(savedOrder.getSkuCode()).isEqualTo("iphone_15");
        assertThat(savedOrder.getPrice()).isEqualByComparingTo("1000.00");
        assertThat(savedOrder.getQuantity()).isEqualTo(1);
        assertThat(savedOrder.getOrderNumber()).isNotBlank();

        // You can use MockMvc or TestRestTemplate to send a POST request to the order
    }

}
