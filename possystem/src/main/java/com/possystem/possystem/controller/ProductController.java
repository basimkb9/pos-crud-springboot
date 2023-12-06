package com.possystem.possystem.controller;

import com.possystem.possystem.domain.Product;
import com.possystem.possystem.dto.ProductDTO;
import com.possystem.possystem.dto.SearchCriteria;
import com.possystem.possystem.service.implementation.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    private ProductServiceImpl productService;
    public ProductController(ProductServiceImpl productService){
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO productDTO){
        ProductDTO savedProduct = productService.save(productDTO);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDTO>> getAll(){
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getById(id));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        productService.deleteById(id);
        return ResponseEntity.ok(String.format("Product with id %d Deleted successfully",id));
    }

    @PutMapping("/product")
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productService.save(productDTO));
    }

    @PostMapping("/product/search")
    public ResponseEntity<List<Product>> searchCar(@RequestBody SearchCriteria searchCriteria){
        List<Product> foundProducts = productService.getSearchedProduct(searchCriteria);
        return ResponseEntity.ok(foundProducts);
    }

    @GetMapping("/products/")
    public List<Product> getCategoryByFilters(@RequestParam(required = false) String name,
                                              @RequestParam(required = false, name = "category") Long categoryId){
        return productService.getProductWithFilters(name,categoryId);
    }
}
