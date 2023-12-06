package com.possystem.possystem.controller;

import com.possystem.possystem.domain.TransactionDetails;
import com.possystem.possystem.dto.SearchCriteria;
import com.possystem.possystem.dto.TransactionDetailsDTO;
import com.possystem.possystem.service.implementation.TransactionDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionDetailController {

    @Autowired
    private TransactionDetailsServiceImpl transactionDetailsService;




//    @PostMapping("/transaction-details")
//    public ResponseEntity<TransactionDetailsDTO> save(@RequestBody TransactionDetailsDTO transactionDetailsDTO){
//        TransactionDetailsDTO savedTransaction = transactionDetailsService.save(transactionDetailsDTO);
//        return ResponseEntity.ok(savedTransaction);
//    }

    @GetMapping("/transaction-details")
    public ResponseEntity<List<TransactionDetailsDTO>> getAll(){
        return ResponseEntity.ok(transactionDetailsService.getAll());
    }

    @GetMapping("/transaction-details/{id}")
    public ResponseEntity<TransactionDetailsDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(transactionDetailsService.getById(id));
    }

    @DeleteMapping("/transaction-details/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        return ResponseEntity.ok(String.format(transactionDetailsService.deleteById(id)));
    }

    @PostMapping("/transaction-detail/search")
    public ResponseEntity<List<TransactionDetails>> searchCar(@RequestBody SearchCriteria searchCriteria){
        List<TransactionDetails> foundTransactionDetails = transactionDetailsService.getSearchedTransactionDetails(searchCriteria);
        return ResponseEntity.ok(foundTransactionDetails);
    }

    @GetMapping("/transaction-details/")
    public List<TransactionDetails> getCategoryByFilters(@RequestParam(required = false) Double costPrice,
                                                         @RequestParam(required = false) Double salePrice,
                                                         @RequestParam(required = false) Double profit,
                                                         @RequestParam(required = false) Long quantitySold,
                                                         @RequestParam(required = false) Long variantId,
                                                         @RequestParam(required = false) Long transactionId,
                                                         @RequestParam(required = false) Double subTotal){
        return transactionDetailsService.getTransactionDetailsWithFilters(costPrice,salePrice,profit,quantitySold,variantId,transactionId, subTotal);
    }
}
