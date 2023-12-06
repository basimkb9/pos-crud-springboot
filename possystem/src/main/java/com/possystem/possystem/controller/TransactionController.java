package com.possystem.possystem.controller;


import com.possystem.possystem.domain.Transaction;
import com.possystem.possystem.dto.SearchCriteria;
import com.possystem.possystem.dto.TransactionDTO;
import com.possystem.possystem.service.implementation.TransactionDetailsServiceImpl;
import com.possystem.possystem.service.implementation.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.possystem.possystem.service.implementation.CartServiceImpl.cartItems;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionService;

    @Autowired
    private TransactionDetailsServiceImpl transactionDetailsService;

    /* this method will do multiple actions:
     save transaction,
     save each item's details into TransactionDetails table
     update variants (quantity) */
    @PostMapping("/transaction")
    public ResponseEntity<TransactionDTO> save(){
        Double total = cartItems.stream()
                .mapToDouble(c->c.getQuantity()*c.getSalePrice())
                .sum();
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .totalAmount(total)
                .date(LocalDate.now())
                .build();
        TransactionDTO savedTransaction = transactionService.save(transactionDTO);

        transactionDetailsService.save(cartItems, savedTransaction);
        cartItems = new ArrayList<>();

        return ResponseEntity.ok(savedTransaction);
    }

    @GetMapping("/transaction")
    public ResponseEntity<List<TransactionDTO>> getAll(){
        return ResponseEntity.ok(transactionService.getAll());
    }

    @GetMapping("/transaction/{id}")
    public ResponseEntity<TransactionDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @DeleteMapping("/transaction/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        return ResponseEntity.ok(String.format(transactionService.deleteById(id)));
    }

    @PutMapping("/transaction")
    public ResponseEntity<TransactionDTO> update(@RequestBody TransactionDTO transactionDTO){
        return ResponseEntity.ok(transactionService.save(transactionDTO));
    }

    @PostMapping("/transaction/search")
    public ResponseEntity<List<Transaction>> searchCar(@RequestBody SearchCriteria searchCriteria){
        List<Transaction> foundTransactions = transactionService.getSearchedTransaction(searchCriteria);
        return ResponseEntity.ok(foundTransactions);
    }

    @GetMapping("/transactions/")
    public List<Transaction> getCategoryByFilters(@RequestParam(required = false, name = "total-amount") Double totalAmount,
                                                  @RequestParam(required = false) LocalDate date){
        return transactionService.getTransactionWithFilters(totalAmount, date);
    }

}
