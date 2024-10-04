package com.pratap.superstore.controllers;

import com.pratap.superstore.models.ApiResponse;
import com.pratap.superstore.models.Product;
import com.pratap.superstore.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductsController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {

        return productService.getAllProducts();


    }


    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveProduct(@RequestBody Product product) {
        ApiResponse apiResponse;
        Product createdProduct = productService.saveProduct(product);

        if (createdProduct != null) {
            apiResponse = new ApiResponse("Product created successfully", HttpStatus.CREATED.value(), createdProduct);
        } else {
            apiResponse = new ApiResponse("Error occurred while creating product", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return ResponseEntity.ok(apiResponse);
    }

    // Update an existing product
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        boolean isUpdated = productService.updateProduct(product);

        if (isUpdated) {
            return ResponseEntity.ok(new ApiResponse("Product updated successfully", HttpStatus.OK.value()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Product not found with ID: " + id, HttpStatus.NOT_FOUND.value()));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        boolean isRemoved = productService.deleteProduct(id);

        if (!isRemoved) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Product not found with ID: " + id, HttpStatus.NOT_FOUND.value()));
        }

        return ResponseEntity.ok(new ApiResponse("Product deleted successfully", HttpStatus.OK.value()));
    }


    @GetMapping("/paging")
    public ResponseEntity<ApiResponse<Page<Product>>> getAllProductsByPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.getProducts(pageable);
        ApiResponse<Page<Product>> response = new ApiResponse<>("success", 200, productPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchProducts")
    public ResponseEntity<ApiResponse<Page<Product>>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double price,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.searchProducts(name, price, pageable);
        ApiResponse<Page<Product>> response = new ApiResponse<>("success", 200, productPage);
        return ResponseEntity.ok(response);
    }


}
