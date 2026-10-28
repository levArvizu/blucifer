package com.levarvizu.fullstacklab.service;

import java.util.List;

import com.levarvizu.fullstacklab.entity.History;
import com.levarvizu.fullstacklab.entity.User;

public interface HistoryService {
    void saveAction(User user, String action);

    List<History> getFullHistory(User user);

    List<String> getRecentActions(Long userId);
}
