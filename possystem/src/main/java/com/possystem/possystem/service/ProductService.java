package com.possystem.possystem.service;

import com.possystem.possystem.domain.Product;
import com.possystem.possystem.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO save(ProductDTO productDTO);

    ProductDTO toDto(Product product);

    Product toDo(ProductDTO productDTO);

    List<ProductDTO> getAll();

    ProductDTO getById(Long id);

    void deleteById(Long id);

}
