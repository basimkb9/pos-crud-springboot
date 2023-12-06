package com.possystem.possystem.controller;

import com.possystem.possystem.domain.Variant;
import com.possystem.possystem.dto.SearchCriteria;
import com.possystem.possystem.dto.VariantDTO;
import com.possystem.possystem.service.implementation.VariantServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VariantController {

    private VariantServiceImpl variantService;
    public VariantController(VariantServiceImpl variantService){
        this.variantService = variantService;
    }

    @PostMapping("/variant")
    public ResponseEntity<VariantDTO> save(@RequestBody VariantDTO variantDTO){
        VariantDTO savedVariant = variantService.save(variantDTO);
        return ResponseEntity.ok(savedVariant);
    }

    @GetMapping("/variant")
    public ResponseEntity<List<VariantDTO>> getAll(){
        return ResponseEntity.ok(variantService.getAll());
    }

    @GetMapping("/variant/{id}")
    public ResponseEntity<VariantDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(variantService.getById(id));
    }

    @DeleteMapping("/variant/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        return ResponseEntity.ok(String.format(variantService.deleteById(id)));
    }

    @PutMapping("/variant")
    public ResponseEntity<VariantDTO> update(@RequestBody VariantDTO variantDTO){
        return ResponseEntity.ok(variantService.save(variantDTO));
    }

    @PostMapping("/variant/search")
    public ResponseEntity<List<Variant>> searchCar(@RequestBody SearchCriteria searchCriteria){
        List<Variant> foundVariants = variantService.getSearchedVariant(searchCriteria);
        return ResponseEntity.ok(foundVariants);
    }

    @GetMapping("/variants/")
    public List<Variant> getCategoryByFilters(@RequestParam(required = false) String name,
                                              @RequestParam(required = false) Long productId,
                                              @RequestParam(required = false) String barcode,
                                              @RequestParam(required = false) Double costPrice,
                                              @RequestParam(required = false) Double salePrice,
                                              @RequestParam(required = false) Long quantity){
        return variantService.getVariantWithFilters(name,productId,barcode,costPrice,salePrice,quantity);
    }
}
