package com.possystem.possystem.service.implementation;

import com.possystem.possystem.domain.Transaction;
import com.possystem.possystem.dto.SearchCriteria;
import com.possystem.possystem.dto.TransactionDTO;
import com.possystem.possystem.exception.RecordNotFoundException;
import com.possystem.possystem.repository.TransactionRepository;
import com.possystem.possystem.repository.TransactionSpecification;
import com.possystem.possystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private EntityManager entityManager;


    public TransactionDTO save(TransactionDTO transactionDTO) {
        return toDto(transactionRepository.save(toDo(transactionDTO)));
    }

    public List<TransactionDTO> getAll() {
        List<TransactionDTO> transactions = transactionRepository.findAll()
                .stream()
                .map(tr -> toDto(tr))
                .collect(Collectors.toList());

        return transactions;
    }

    public TransactionDTO getById(Long id) {
        Optional<Transaction> found = transactionRepository.findById(id);
        if(found.isPresent()){
            return toDto(found.get());
        }
        else{
            throw new RecordNotFoundException(String.format("No transaction exists with id: %d",id));
        }
    }

    public String deleteById(Long id) {
        Optional<Transaction> found = transactionRepository.findById(id);
        if(found.isPresent()){
            transactionRepository.deleteById(id);
            return "Transaction Successfully Deleted";
        }
        else{
            throw new RecordNotFoundException(String.format("No transaction exists with id: %d",id));
        }
    }

    public Transaction toDo(TransactionDTO transactionDTO){
        if(transactionDTO.getId() == null){
            return Transaction.builder()
                    .date(transactionDTO.getDate())
                    .totalAmount(transactionDTO.getTotalAmount())
                    .build();
        }
        else{
            return Transaction.builder()
                    .date(transactionDTO.getDate())
                    .id(transactionDTO.getId())
                    .totalAmount(transactionDTO.getTotalAmount())
                    .build();
        }
    }

    public TransactionDTO toDto(Transaction transaction){
        return TransactionDTO.builder()
                .id(transaction.getId())
                .date(transaction.getDate())
                .totalAmount(transaction.getTotalAmount())
                .build();
    }

    public List<Transaction> getSearchedTransaction(SearchCriteria searchCriteria) {
        try{
            TransactionSpecification transactionSpecification = new TransactionSpecification(searchCriteria);
            List<Transaction> transactions = transactionRepository.findAll(transactionSpecification);
            return transactions;
        }
        catch (Exception e){
            throw new RuntimeException("No record exists. " + e);
        }
    }

    public List<Transaction> getTransactionWithFilters(Double totalAmount, LocalDate date) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> cq = criteriaBuilder.createQuery(Transaction.class);
        Root<Transaction> transactionRoot = cq.from(Transaction.class);
        List<Predicate> predicates = new ArrayList<>();
        if(date != null){
            predicates.add(criteriaBuilder.equal(transactionRoot.get("date"),date));
        }
        if(totalAmount != null){
            predicates.add(criteriaBuilder.equal(transactionRoot.get("totalAmount"),totalAmount));
        }
        cq.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<Transaction> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

}
