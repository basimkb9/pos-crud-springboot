package com.possystem.possystem.controller;

import com.possystem.possystem.domain.Category;
import com.possystem.possystem.dto.CategoryDTO;
import com.possystem.possystem.dto.SearchCriteria;
import com.possystem.possystem.service.implementation.CategoryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryServiceImpl categoryService;
    public CategoryController(CategoryServiceImpl categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategory = categoryService.save(categoryDTO);
        return ResponseEntity.ok(savedCategory);
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryDTO>> getAll(){
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        categoryService.deleteById(id);
        return ResponseEntity.ok(String.format("Category with id %d Deleted successfully",id));
    }

    @PutMapping("/category")
    public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.ok(categoryService.save(categoryDTO));
    }


    @PostMapping("/category/search")
    public ResponseEntity<List<Category>> searchCar(@RequestBody SearchCriteria searchCriteria){
        List<Category> foundCategories = categoryService.getSearchedCategory(searchCriteria);
        return ResponseEntity.ok(foundCategories);
    }

    @GetMapping("/categories/")
    public List<Category> getCategoryByFilters(@RequestParam(required = false) String name){
        return categoryService.getCategoryWithFilters(name);
    }
}
