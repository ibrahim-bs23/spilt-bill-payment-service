package com.brainstation23.skeleton.data.repository;

import com.brainstation23.skeleton.data.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    Optional<TransactionHistory> findByTransactionId(String transactionId);
}
