package com.possystem.possystem.service;

import com.possystem.possystem.domain.Category;
import com.possystem.possystem.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    Category toDo(CategoryDTO categoryDTO);

    CategoryDTO toDto(Category category);

    CategoryDTO save(CategoryDTO categoryDTO);

    List<CategoryDTO> getAll();

    void deleteById(Long id);
}
