package com.possystem.possystem.service;

import com.possystem.possystem.domain.Cart;
import com.possystem.possystem.domain.Product;
import com.possystem.possystem.domain.Variant;
import com.possystem.possystem.dto.VariantDTO;
import com.possystem.possystem.exception.RecordNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface VariantService {
    VariantDTO save(VariantDTO variantDTO);

    List<VariantDTO> getAll();

    VariantDTO getById(Long id);

    String deleteById(Long id);

    Variant getVariantByProductNameAndVariantName(String variantName, String productName);

    Variant toDo(VariantDTO variantDTO);

    VariantDTO toDto(Variant variant);

    void updateQuantity(Variant variant, Long quantity);

    Boolean checkAvailability(Cart cartItem);

}
