package com.levarvizu.fullstacklab.kafka;

import com.levarvizu.fullstacklab.dto.ProductCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventConsumer {

    @KafkaListener(
            topics = "product-created",
            groupId = "notification-group"
    )
    public void consume(
            ProductCreatedEvent event
    ) {

        System.out.println("""
                
                =========================
                NOTIFICATION SERVICE
                =========================
                Producto creado:
                %s
                
                Simulando envío de email...
                
                """.formatted(event));
    }
}
