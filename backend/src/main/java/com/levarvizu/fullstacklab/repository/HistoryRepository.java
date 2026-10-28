package com.levarvizu.fullstacklab.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.levarvizu.fullstacklab.entity.History;
import com.levarvizu.fullstacklab.entity.User;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUser(User user);
}
