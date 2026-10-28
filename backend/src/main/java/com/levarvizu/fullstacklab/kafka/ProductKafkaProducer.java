package com.levarvizu.fullstacklab.kafka;

import com.levarvizu.fullstacklab.dto.ProductCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductKafkaProducer {

    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductKafkaProducer(
            KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProductCreated(
            ProductCreatedEvent event
    ) {

        kafkaTemplate.send(
                "product-created",
                event
        );

        System.out.println(
                "Evento enviado a Kafka -> "
                + event
        );
    }
}
