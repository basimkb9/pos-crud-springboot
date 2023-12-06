package com.possystem.possystem.service.implementation;

import com.possystem.possystem.domain.Cart;
import com.possystem.possystem.domain.Product;
import com.possystem.possystem.domain.Variant;
import com.possystem.possystem.dto.SearchCriteria;
import com.possystem.possystem.dto.VariantDTO;
import com.possystem.possystem.exception.RecordNotFoundException;
import com.possystem.possystem.repository.ProductRepository;
import com.possystem.possystem.repository.VariantRepository;
import com.possystem.possystem.repository.VariantSpecification;
import com.possystem.possystem.service.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class VariantServiceImpl implements VariantService {
    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    public VariantDTO save(VariantDTO variantDTO) {
        return toDto(variantRepository.save(toDo(variantDTO)));
    }

    public List<VariantDTO> getAll() {
        List<VariantDTO> variants = variantRepository.findAll()
                .stream().map(variant -> toDto(variant))
                .collect(Collectors.toList());

        return variants;
    }

    public VariantDTO getById(Long id) {
        Optional<Variant> found = variantRepository.findById(id);
        if(found.isPresent()){
            return toDto(found.get());
        }
        else{
            throw new RecordNotFoundException(String.format("No variant exists with id: %d", id));
        }
    }

    public String deleteById(Long id) {
        Optional<Variant> found = variantRepository.findById(id);
        if(found.isPresent()){
            variantRepository.deleteById(id);
            return "Deleted successfully";
        }
        else{
            throw new RecordNotFoundException(String.format("No variant exists with id: %d", id));
        }
    }

    public Variant getVariantByProductNameAndVariantName(String variantName, String productName) {
        Optional<Product> product = productRepository.findByName(productName);
        if(product.isPresent()){
            Optional<Variant> variant = variantRepository.findByNameAndProductId(variantName, product.get().getId());
            if(variant.isPresent()) return variant.get();
            else throw new RecordNotFoundException(String.format("Variant not found for product: " + productName + " and variant name: " + variantName));
        }
        else throw new RecordNotFoundException(String.format("Variant not found for product: " + productName));
    }


    public Variant toDo(VariantDTO variantDTO){
        if(variantDTO.getId() == null){
            return Variant.builder()
                    .name(variantDTO.getName())
                    .barcode(variantDTO.getBarcode())
                    .costPrice(variantDTO.getCostPrice())
                    .product(variantDTO.getProduct())
                    .salePrice(variantDTO.getSalePrice())
                    .quantity(variantDTO.getQuantity())
                    .build();
        }

        else{
            return Variant.builder()
                    .id(variantDTO.getId())
                    .name(variantDTO.getName())
                    .barcode(variantDTO.getBarcode())
                    .costPrice(variantDTO.getCostPrice())
                    .product(variantDTO.getProduct())
                    .salePrice(variantDTO.getSalePrice())
                    .quantity(variantDTO.getQuantity())
                    .build();
        }
    }

    public VariantDTO toDto(Variant variant){
        return VariantDTO.builder()
                .barcode(variant.getBarcode())
                .costPrice(variant.getCostPrice())
                .salePrice(variant.getSalePrice())
                .id(variant.getId())
                .quantity(variant.getQuantity())
                .name(variant.getName())
                .build();
    }

    public void updateQuantity(Variant variant, Long quantity) {
        Long updatedQuantity = variant.getQuantity() - quantity;
        variant.setQuantity(updatedQuantity);
        variantRepository.save(variant);
    }

    public Boolean checkAvailability(Cart cartItem) {
        Variant variant = getVariantByProductNameAndVariantName(cartItem.getVariantName(), cartItem.getProductName());
        if(cartItem.getQuantity() < variant.getQuantity()){
            return true;
        }
        else{
            return false;
        }
    }

    public List<Variant> getSearchedVariant(SearchCriteria searchCriteria) {
        try{
            VariantSpecification variantSpecification = new VariantSpecification(searchCriteria);
            List<Variant> variants = variantRepository.findAll(variantSpecification);
            return variants;
        }
        catch (Exception e){
            throw new RuntimeException("No record exists. " + e);
        }
    }

    public List<Variant> getVariantWithFilters(String name, Long productId, String barcode, Double costPrice, Double salePrice, Long quantity) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Variant> cq = criteriaBuilder.createQuery(Variant.class);
        Root<Variant> variantRoot = cq.from(Variant.class);
        List<Predicate> predicates = new ArrayList<>();
        if(name != null){
            predicates.add(criteriaBuilder.equal(variantRoot.get("name"),name));
        }
        if(productId != null){
            predicates.add(criteriaBuilder.equal(variantRoot.get("product_id"),productId));
        }
        if(barcode != null){
            predicates.add(criteriaBuilder.equal(variantRoot.get("barcode"),productId));
        }
        if(costPrice != null){
            predicates.add(criteriaBuilder.equal(variantRoot.get("cost_price"),costPrice));
        }
        if(salePrice != null){
            predicates.add(criteriaBuilder.equal(variantRoot.get("sale_price"),salePrice));
        }
        if(quantity != null){
            predicates.add(criteriaBuilder.equal(variantRoot.get("quantity"),quantity));
        }
        cq.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<Variant> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

}
