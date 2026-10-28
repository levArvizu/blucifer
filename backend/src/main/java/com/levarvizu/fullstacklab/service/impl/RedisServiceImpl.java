package com.levarvizu.fullstacklab.service.impl;

import com.levarvizu.fullstacklab.entity.History;
import com.levarvizu.fullstacklab.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void clearHistory(Long userId) {
        redisTemplate.delete("history:" + userId);
    }

    @Override
    public History getHistory(Long userId) {
        return (History) redisTemplate.opsForValue().get("history:" + userId);
    }

    @Override
    public void saveHistory(Long userId, History history) {
        redisTemplate.opsForValue().set("history:" + userId, history);
    }

}
