package com.levarvizu.fullstacklab.service;

import java.util.List;

import com.levarvizu.fullstacklab.entity.History;

public interface RedisService {
    History getHistory(Long userId);

    void saveHistory(Long userId, History history);

    void clearHistory(Long userId);

}
