package com.levarvizu.fullstacklab.kafka;

import com.levarvizu.fullstacklab.dto.OrderCreatedEvent;
import com.levarvizu.fullstacklab.dto.PaymentCompletedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventConsumer {

    private final PaymentKafkaProducer producer;

    public PaymentEventConsumer(
            PaymentKafkaProducer producer
    ) {
        this.producer = producer;
    }

    @KafkaListener(
            topics = "order-created",
            groupId = "payment-group"
    )
    public void consume(
            OrderCreatedEvent event
    ) {

        System.out.println("""

                =====================
                PAYMENT SERVICE
                =====================

                Procesando pago:

                %s

                """.formatted(event));

        producer.sendPaymentCompleted(
                new PaymentCompletedEvent(
                        event.orderId(),
                        event.total()
                )
        );
    }
}
