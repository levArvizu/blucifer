package com.levarvizu.fullstacklab.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levarvizu.fullstacklab.dto.UserDetailsResponse;
import com.levarvizu.fullstacklab.entity.History;
import com.levarvizu.fullstacklab.entity.User;
import com.levarvizu.fullstacklab.kafka.KafkaProducer;
import com.levarvizu.fullstacklab.repository.UserRepository;
import com.levarvizu.fullstacklab.service.HistoryService;
import com.levarvizu.fullstacklab.service.RedisService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final RedisService redisService;
    private final KafkaProducer kafkaProducer;
    private final HistoryService historyService;

    public UserController(UserRepository userRepository,
            RedisService redisService,
            KafkaProducer kafkaProducer,
            HistoryService historyService) {
        this.userRepository = userRepository;
        this.redisService = redisService;
        this.kafkaProducer = kafkaProducer;
        this.historyService = historyService;
    }

    @GetMapping("/{id}/details")
    public CompletableFuture<UserDetailsResponse> getUserDetails(@PathVariable Long id) {

        // Consulta en PostgreSQL
        CompletableFuture<User> userFuture = CompletableFuture.supplyAsync(
                () -> userRepository.findById(id).orElseThrow());

        // Consulta en historial (PostgreSQL)
        CompletableFuture<List<History>> historyFuture = userFuture
                .thenApplyAsync(user -> historyService.getFullHistory(user));

        // Enviar evento a Kafka
        CompletableFuture<Void> kafkaFuture = CompletableFuture.runAsync(
                () -> kafkaProducer.sendEvent("user-details-requested", id));

        // Esperar a que terminen todas las tareas
        return CompletableFuture.allOf(userFuture, historyFuture, kafkaFuture)
                .thenApply(v -> new UserDetailsResponse(
                        userFuture.join().getId(),
                        userFuture.join().getEmail(),
                        historyFuture.join().stream()
                                .map(History::getAction)
                                .toList()));
    }
}
