package com.levarvizu.fullstacklab.kafka;

import com.levarvizu.fullstacklab.dto.PaymentCompletedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusConsumer {

    @KafkaListener(
            topics = "payment-completed",
            groupId = "order-group"
    )
    public void consume(
            PaymentCompletedEvent event
    ) {

        System.out.println("""

                =====================
                ORDER SERVICE
                =====================

                Orden actualizada a:

                PAID

                %s

                """.formatted(event));
    }
}
