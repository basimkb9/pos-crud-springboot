package com.possystem.possystem.service;

import com.possystem.possystem.domain.Transaction;
import com.possystem.possystem.dto.TransactionDTO;
import com.possystem.possystem.exception.RecordNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface TransactionService {
    TransactionDTO save(TransactionDTO transactionDTO);

    List<TransactionDTO> getAll();

    TransactionDTO getById(Long id);

    String deleteById(Long id);

    Transaction toDo(TransactionDTO transactionDTO);

    TransactionDTO toDto(Transaction transaction);
}
