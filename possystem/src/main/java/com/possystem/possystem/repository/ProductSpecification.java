package com.possystem.possystem.repository;
import com.possystem.possystem.domain.Product;
import com.possystem.possystem.dto.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProductSpecification implements Specification<Product> {

    private SearchCriteria searchCriteria;

    public ProductSpecification(SearchCriteria searchCriteria){
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if(searchCriteria.getOperation().equalsIgnoreCase(":")){
            return criteriaBuilder.equal(root.get(searchCriteria.getKey()),searchCriteria.getValue());
        }
        if(searchCriteria.getOperation().equalsIgnoreCase(">")){
            return criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()),searchCriteria.getValue());
        }
        if(searchCriteria.getOperation().equalsIgnoreCase("<")){
            return criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()),searchCriteria.getValue());
        }
        return null;
    }
}

