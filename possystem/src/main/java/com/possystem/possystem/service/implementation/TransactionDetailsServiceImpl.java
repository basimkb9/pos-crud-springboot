package com.possystem.possystem.service.implementation;

import com.possystem.possystem.domain.Cart;
import com.possystem.possystem.domain.TransactionDetails;
import com.possystem.possystem.domain.Variant;
import com.possystem.possystem.dto.SearchCriteria;
import com.possystem.possystem.dto.TransactionDTO;
import com.possystem.possystem.dto.TransactionDetailsDTO;
import com.possystem.possystem.exception.RecordNotFoundException;
import com.possystem.possystem.repository.ProductRepository;
import com.possystem.possystem.repository.TransactionDetailsRepository;
import com.possystem.possystem.repository.TransactionDetailsSpecification;
import com.possystem.possystem.repository.VariantRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionDetailsServiceImpl {
    private TransactionDetailsRepository detailsRepository;
    private TransactionServiceImpl transactionService;
    private VariantServiceImpl variantService;
    private EntityManager entityManager;
    private VariantRepository variantRepository;
    private ProductRepository productRepository;

    public TransactionDetailsServiceImpl(TransactionDetailsRepository detailsRepository, EntityManager entityManager,
                                         TransactionServiceImpl transactionService, VariantServiceImpl variantService,
                                         VariantRepository variantRepository, ProductRepository productRepository){
        this.detailsRepository = detailsRepository;
        this.entityManager = entityManager;
        this.transactionService = transactionService;
        this. variantService = variantService;
        this.variantRepository = variantRepository;
        this.productRepository = productRepository;
    }

    public List<TransactionDetailsDTO> save(List<Cart> cartItems, TransactionDTO transactionDTO) {
        List<TransactionDetailsDTO> savedTransactionDetails = new ArrayList<>();
        for (Cart item : cartItems) {
            Variant variant = variantService.getVariantByProductNameAndVariantName(item.getVariantName(),item.getProductName());
            Double profitAmount = (item.getSalePrice() - item.getCostPrice()) * item.getQuantity();

            TransactionDetailsDTO detailsDTO = TransactionDetailsDTO.builder()
                    .variant(variant)
                    .transaction(transactionService.toDo(transactionDTO))
                    .costPrice(item.getCostPrice())
                    .unitPrice(item.getSalePrice())
                    .quantity(item.getQuantity())
                    .subTotal(item.getSalePrice() * item.getQuantity())
                    .profit(profitAmount)
                    .build();

            TransactionDetailsDTO savedDetail = toDto(detailsRepository.save(toDo(detailsDTO)));
            variantService.updateQuantity(variant,savedDetail.getQuantity());
            savedTransactionDetails.add(savedDetail);
        }
        return savedTransactionDetails;
    }

    public List<TransactionDetailsDTO> getAll() {
        List<TransactionDetailsDTO> transactionDetails = detailsRepository.findAll()
                .stream().map(tr -> toDto(tr)).collect(Collectors.toList());
        return transactionDetails;
    }

    public TransactionDetailsDTO getById(Long id) {
        Optional<TransactionDetails> found = detailsRepository.findById(id);
        if(found.isPresent()){
            return toDto(found.get());
        }
        else{
            throw new RecordNotFoundException(String.format("No Sale item exists with id = %d",id));
        }
    }

    public String deleteById(Long id) {
        Optional<TransactionDetails> found = detailsRepository.findById(id);
        if(found.isPresent()){
            detailsRepository.deleteById(id);
            return "Record Successfully deleted";
        }
        else{
            throw new RecordNotFoundException(String.format("No Sale item exists with id = %d",id));
        }
    }

    public TransactionDetailsDTO toDto(TransactionDetails transactionDetails){
        return TransactionDetailsDTO.builder()
                .id(transactionDetails.getId())
                .transaction(transactionDetails.getTransaction())
                .profit(transactionDetails.getProfit())
                .costPrice(transactionDetails.getCostPrice())
                .subTotal(transactionDetails.getSubTotal())
                .quantity(transactionDetails.getQuantity())
                .unitPrice(transactionDetails.getUnitPrice())
                .variant(transactionDetails.getVariant())
                .build();
    }

    public TransactionDetails toDo(TransactionDetailsDTO dto){
        if(dto.getId() == null){
            return TransactionDetails.builder()
                    .profit(dto.getProfit())
                    .transaction(dto.getTransaction())
                    .variant(dto.getVariant())
                    .costPrice(dto.getCostPrice())
                    .subTotal(dto.getSubTotal())
                    .quantity(dto.getQuantity())
                    .unitPrice(dto.getUnitPrice())
                    .build();
        }
        else{
            return TransactionDetails.builder()
                    .id(dto.getId())
                    .profit(dto.getProfit())
                    .transaction(dto.getTransaction())
                    .variant(dto.getVariant())
                    .costPrice(dto.getCostPrice())
                    .subTotal(dto.getSubTotal())
                    .quantity(dto.getQuantity())
                    .unitPrice(dto.getUnitPrice())
                    .build();
        }
    }

    public List<TransactionDetails> getSearchedTransactionDetails(SearchCriteria searchCriteria) {
        try{
            TransactionDetailsSpecification transactionDetailsSpecification = new TransactionDetailsSpecification(searchCriteria);
            List<TransactionDetails> transactionDetailss = detailsRepository.findAll(transactionDetailsSpecification);
            return transactionDetailss;
        }
        catch (Exception e){
            throw new RuntimeException("No record exists. " + e);
        }
    }

    public List<TransactionDetails> getTransactionDetailsWithFilters(Double costPrice, Double salePrice, Double profit, Long quantitySold, Long variantId, Long transactionId, Double subTotal) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TransactionDetails> cq = criteriaBuilder.createQuery(TransactionDetails.class);
        Root<TransactionDetails> transactionDetailsRoot = cq.from(TransactionDetails.class);
        List<Predicate> predicates = new ArrayList<>();
        if(costPrice != null){
            predicates.add(criteriaBuilder.equal(transactionDetailsRoot.get("cost_price"),costPrice));
        }if(salePrice != null){
            predicates.add(criteriaBuilder.equal(transactionDetailsRoot.get("unit_price"),salePrice));
        }if(profit != null){
            predicates.add(criteriaBuilder.equal(transactionDetailsRoot.get("profit"),profit));
        }if(subTotal != null){
            predicates.add(criteriaBuilder.equal(transactionDetailsRoot.get("sub_total"),subTotal));
        }if(variantId != null){
            predicates.add(criteriaBuilder.equal(transactionDetailsRoot.get("variant_id"),variantId));
        }if(transactionId != null){
            predicates.add(criteriaBuilder.equal(transactionDetailsRoot.get("transaction_id"),subTotal));
        }if(quantitySold != null){
            predicates.add(criteriaBuilder.equal(transactionDetailsRoot.get("quantity"),quantitySold));
        }
        cq.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<TransactionDetails> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

}
