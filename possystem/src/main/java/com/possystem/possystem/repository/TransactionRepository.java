package com.possystem.possystem.repository;

import com.possystem.possystem.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> , JpaSpecificationExecutor<Transaction> {
    Optional<Transaction> findByDate(LocalDate date);
}
