package com.inventery.product.service;

import com.inventery.product.entity.Product;
import com.inventery.product.exception.ProductNotFoundException;
import com.inventery.product.repository.ProductRepository;
import com.inventery.product.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Adds a new product to the inventory.
     *
     * @param product the product to be added; must contain a non-negative price
     * @return the saved product with a unique identifier
     * @throws BadRequestException if the price of the product is negative
     */
    public Product addProduct(Product product) throws BadRequestException {
        if (product.getPrice() < 0) {
            throw new BadRequestException("Price must not be negative");
        }
        return productRepository.save(product);
    }

    /**
     * Retrieves a product by its unique identifier.
     *
     * @param id the unique identifier of the product to retrieve
     * @return the product with the specified ID
     * @throws ProductNotFoundException if no product with the specified ID exists
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * Retrieves all products in the inventory.
     *
     * @return a list of all products
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Updates the details of an existing product.
     *
     * @param id            the unique identifier of the product to update
     * @param updatedProduct the updated product details
     * @return the updated product
     * @throws ProductNotFoundException if no product with the specified ID exists
     */
    public Product updateProduct(Long id, Product updatedProduct) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        updatedProduct.setId(id);
        return productRepository.save(updatedProduct);
    }

    /**
     * Deletes a product from the inventory.
     *
     * @param id the unique identifier of the product to delete
     * @throws ProductNotFoundException if no product with the specified ID exists
     */
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
}
