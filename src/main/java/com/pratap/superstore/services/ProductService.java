package com.pratap.superstore.services;

import com.pratap.superstore.models.Product;
import com.pratap.superstore.repository.ProductRepository;
import com.pratap.superstore.specifications.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {

        return productRepository.save(product);
    }

    public boolean updateProduct(Product product) {

        Optional<Product> existingProduct = productRepository.findById(product.getId());

        if (existingProduct.isPresent()) {
                 productRepository.save(product);
            return true;
        } else {
            return false; // Product not found
        }

    }




    public boolean deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    public Page<Product> searchProducts(String name, Double price, Pageable pageable) {
     /*   Specification<Product> spec = Specification.where(ProductSpecification.nameContains(name))
                .or(ProductSpecification.priceGreaterThan(price));*/

        Specification<Product> spec = Specification.where(ProductSpecification.nameContains(name));
        return productRepository.findAll(spec, pageable);
    }
}
