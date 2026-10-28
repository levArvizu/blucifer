package com.levarvizu.fullstacklab.kafka;

import com.levarvizu.fullstacklab.dto.ProductCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AuditEventConsumer {

    @KafkaListener(
            topics = "product-created",
            groupId = "audit-group"
    )
    public void consume(
            ProductCreatedEvent event
    ) {

        System.out.println("""
                
                =========================
                AUDIT SERVICE
                =========================
                Registrando auditoría:
                %s
                
                """.formatted(event));
    }
}
