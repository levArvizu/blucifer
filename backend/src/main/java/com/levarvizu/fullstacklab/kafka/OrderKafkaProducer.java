package com.levarvizu.fullstacklab.kafka;

import com.levarvizu.fullstacklab.dto.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderKafkaProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderKafkaProducer(
            KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreated(
            OrderCreatedEvent event
    ) {

        kafkaTemplate.send(
                "order-created",
                event
        );

        System.out.println(
                "Order event sent -> " + event
        );
    }
}
