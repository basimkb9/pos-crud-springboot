package com.possystem.possystem.service;

import com.possystem.possystem.domain.Cart;
import com.possystem.possystem.domain.TransactionDetails;
import com.possystem.possystem.domain.Variant;
import com.possystem.possystem.dto.TransactionDTO;
import com.possystem.possystem.dto.TransactionDetailsDTO;
import com.possystem.possystem.exception.RecordNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface TransactionDetailService {
    List<TransactionDetailsDTO> save(List<Cart> cartItems, TransactionDTO transactionDTO);
    List<TransactionDetailsDTO> getAll();
    TransactionDetailsDTO getById(Long id);
    String deleteById(Long id);
    TransactionDetailsDTO toDto(TransactionDetails transactionDetails);
    TransactionDetails toDo(TransactionDetailsDTO dto);
}
