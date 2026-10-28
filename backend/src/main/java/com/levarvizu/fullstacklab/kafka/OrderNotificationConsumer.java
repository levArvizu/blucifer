package com.levarvizu.fullstacklab.kafka;

import com.levarvizu.fullstacklab.dto.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderNotificationConsumer {

    @KafkaListener(
            topics = "order-created",
            groupId = "order-notification-group"
    )
    public void consume(
            OrderCreatedEvent event
    ) {

        System.out.println("""

                ========================
                ORDER NOTIFICATION
                ========================
                Notificando al cliente:

                %s

                Correo enviado.
                """.formatted(event));
    }
}
