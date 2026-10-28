package com.levarvizu.fullstacklab.kafka;

import com.levarvizu.fullstacklab.dto.PaymentCompletedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentKafkaProducer {

    private final KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate;

    public PaymentKafkaProducer(
            KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentCompleted(
            PaymentCompletedEvent event
    ) {

        kafkaTemplate.send(
                "payment-completed",
                event
        );

        System.out.println(
                "Payment event sent -> " + event
        );
    }
}
