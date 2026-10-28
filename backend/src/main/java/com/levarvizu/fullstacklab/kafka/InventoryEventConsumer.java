package com.levarvizu.fullstacklab.kafka;

import com.levarvizu.fullstacklab.dto.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventConsumer {

    @KafkaListener(
            topics = "order-created",
            groupId = "inventory-group"
    )
    public void consume(
            OrderCreatedEvent event
    ) {

        System.out.println("""

                ========================
                INVENTORY SERVICE
                ========================
                Reservando inventario:

                %s

                Stock reservado.
                """.formatted(event));
    }
}
