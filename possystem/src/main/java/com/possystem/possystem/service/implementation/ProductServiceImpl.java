package com.possystem.possystem.service.implementation;

import com.possystem.possystem.domain.Product;
import com.possystem.possystem.dto.ProductDTO;
import com.possystem.possystem.dto.SearchCriteria;
import com.possystem.possystem.exception.RecordNotFoundException;
import com.possystem.possystem.repository.ProductRepository;
import com.possystem.possystem.repository.ProductSpecification;
import com.possystem.possystem.service.ProductService;
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
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    public ProductDTO save(ProductDTO productDTO) {
        Product product = toDo(productDTO);
        return toDto(productRepository.save(product));
    }

    public ProductDTO toDto(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .build();
    }

    public Product toDo(ProductDTO productDTO){
        if(productDTO.getId()==null){
            return Product.builder()
                    .name(productDTO.getName())
                    .category(productDTO.getCategory())
                    .build();
        }
        else{
            return Product.builder()
                    .id(productDTO.getId())
                    .name(productDTO.getName())
                    .category(productDTO.getCategory())
                    .build();
        }
    }

    public List<ProductDTO> getAll() {
        List<ProductDTO> products = productRepository.findAll()
                .stream()
                .map(product -> toDto(product))
                .collect(Collectors.toList());

        return products;
    }

    public ProductDTO getById(Long id) {
        Optional<Product> found = productRepository.findById(id);
        if(found.isPresent()){
            return toDto(found.get());
        }
        else{
            throw new RecordNotFoundException(String.format("Product doesnt exist with id: %d", id));
        }
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getSearchedProduct(SearchCriteria searchCriteria) {
        try{
            ProductSpecification productSpecification = new ProductSpecification(searchCriteria);
            List<Product> products = productRepository.findAll(productSpecification);
            return products;
        }
        catch (Exception e){
            throw new RuntimeException("No record exists. " + e);
        }
    }

    public List<Product> getProductWithFilters(String name, Long categoryId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = cq.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();
        if(name != null){
            predicates.add(criteriaBuilder.equal(productRoot.get("name"),name));
        }
        if(categoryId != null){
            predicates.add(criteriaBuilder.equal(productRoot.get("category"),categoryId));
        }
        cq.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<Product> query = entityManager.createQuery(cq);
        return query.getResultList();
    }


}
