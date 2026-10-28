package com.levarvizu.fullstacklab.service.impl;

import com.levarvizu.fullstacklab.entity.History;
import com.levarvizu.fullstacklab.entity.User;
import com.levarvizu.fullstacklab.repository.HistoryRepository;
import com.levarvizu.fullstacklab.service.HistoryService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public HistoryServiceImpl(HistoryRepository historyRepository, RedisTemplate<String, String> redisTemplate) {
        this.historyRepository = historyRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveAction(User user, String action) {
        // Guardar en PostgreSQL
        History history = new History();
        history.setUser(user);
        history.setAction(action);
        history.setTimestamp(LocalDateTime.now());
        historyRepository.save(history);

        // Guardar en Redis (últimas acciones)
        redisTemplate.opsForList().leftPush("history:" + user.getId(), action);
    }

    @Override
    public List<History> getFullHistory(User user) {
        return historyRepository.findByUser(user);
    }

    @Override
    public List<String> getRecentActions(Long userId) {
        return redisTemplate.opsForList().range("history:" + userId, 0, 10);
    }
}
