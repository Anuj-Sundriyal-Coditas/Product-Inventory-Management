package com.inventery.product.controller;

import com.inventery.product.dto.ProductDTO;
import com.inventery.product.entity.Product;
import com.inventery.product.exception.BadRequestException;
import com.inventery.product.exception.ProductNotFoundException;
import com.inventery.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Adds a new product to the inventory.
     *
     * @param productDTO the product data to be added; must contain a non-negative price
     * @return a ResponseEntity containing the created product and a status of 201 Created
     * @throws BadRequestException if the price of the product is negative
     */
    @PostMapping("/add-product")
    public ResponseEntity<ProductDTO> addProduct( @RequestBody ProductDTO productDTO) throws BadRequestException {
        Product createdProduct = productService.addProduct(convertToEntity(productDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdProduct));
    }

    /**
     * Retrieves a product by its unique identifier.
     *
     * @param id the unique identifier of the product to retrieve
     * @return a ResponseEntity containing the requested product and a status of 200 OK
     */
    @GetMapping("/get-single-product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(convertToDTO(product));
    }

    /**
     * Retrieves all products in the inventory.
     *
     * @return a ResponseEntity containing a list of all products and a status of 200 OK
     */
    @GetMapping("/get-all-products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOs = products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }

    /**
     * Updates the details of an existing product.
     *
     * @param id            the unique identifier of the product to update
     * @param updatedProductDTO the updated product details
     * @return a ResponseEntity containing the updated product and a status of 200 OK
     * @throws ProductNotFoundException if no product with the specified ID exists
     */
    @PutMapping("/update-product/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,  @RequestBody ProductDTO updatedProductDTO) {
        Product updatedProduct = productService.updateProduct(id, convertToEntity(updatedProductDTO));
        return ResponseEntity.ok(convertToDTO(updatedProduct));
    }

    /**
     * Deletes a product from the inventory.
     *
     * @param id the unique identifier of the product to delete
     * @return a ResponseEntity with no content and a status of 204 No Content
     * @throws ProductNotFoundException if no product with the specified ID exists
     */
    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        return product;
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuantity(product.getQuantity());
        return productDTO;
    }
}
