package com.levarvizu.fullstacklab.controller;

import com.levarvizu.fullstacklab.dto.OrderCreatedEvent;
import com.levarvizu.fullstacklab.kafka.OrderKafkaProducer;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderKafkaProducer producer;

    public OrderController(
            OrderKafkaProducer producer
    ) {
        this.producer = producer;
    }

    @PostMapping("/test")
    public String createOrder() {

        producer.sendOrderCreated(
                new OrderCreatedEvent(
                        100L,
                        1L,
                        2,
                        new BigDecimal("5000")
                )
        );

        return "Order event created";
    }
}
