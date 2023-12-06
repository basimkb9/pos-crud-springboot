package com.possystem.possystem.service.implementation;

import com.possystem.possystem.domain.Category;
import com.possystem.possystem.dto.CategoryDTO;
import com.possystem.possystem.dto.SearchCriteria;
import com.possystem.possystem.exception.RecordNotFoundException;
import com.possystem.possystem.repository.CategoryRepository;
import com.possystem.possystem.repository.CategorySpecification;
import com.possystem.possystem.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    EntityManager entityManager;

    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = toDo(categoryDTO);
        return toDto(categoryRepository.save(category));
    }

    public Category toDo(CategoryDTO categoryDTO) {
        if(categoryDTO.getId() == null){
            return Category.builder()
                    .name(categoryDTO.getName())
                    .build();
        }
        else{
            return Category.builder()
                    .name(categoryDTO.getName())
                    .id(categoryDTO.getId())
                    .build();
        }

    }

    public CategoryDTO toDto(Category category){
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }


    public List<CategoryDTO> getAll() {
        return categoryRepository
                .findAll()
                .stream()
                .map(category -> toDto(category))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public CategoryDTO getById(Long id) {
        Optional<Category> found = categoryRepository.findById(id);
        if(found.isPresent()){
            return toDto(found.get());
        }
        else{
            throw new RecordNotFoundException(String.format("No Category exists on id %d",id));
        }
    }

    public List<Category> getSearchedCategory(SearchCriteria searchCriteria) {
        try{
            CategorySpecification categorySpecification = new CategorySpecification(searchCriteria);
            return categoryRepository.findAll(categorySpecification);
        }
        catch (Exception e){
            throw new RuntimeException("No record exists. " + e);
        }
    }

    public List<Category> getCategoryWithFilters(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> cq = criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot = cq.from(Category.class);
        List<Predicate> predicates = new ArrayList<>();
        if(name != null){
            predicates.add(criteriaBuilder.equal(categoryRoot.get("name"),name));
        }
        cq.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<Category> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
