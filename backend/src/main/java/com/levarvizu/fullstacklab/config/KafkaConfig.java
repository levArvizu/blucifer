package com.levarvizu.fullstacklab.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic productCreatedTopic() {
        return new NewTopic(
                "product-created",
                1,
                (short) 1
        );
    }
    
    @Bean
    public NewTopic orderCreatedTopic() {

        return new NewTopic(
                "order-created",
                1,
                (short) 1
        );
    }
    
    @Bean
    public NewTopic paymentCompletedTopic() {

        return new NewTopic(
                "payment-completed",
                1,
                (short) 1
        );
    }
}