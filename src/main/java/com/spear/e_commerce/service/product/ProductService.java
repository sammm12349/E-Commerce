package com.spear.e_commerce.service.product;

import com.spear.e_commerce.exceptions.ResourceNotFoundException;
import com.spear.e_commerce.model.Category;
import com.spear.e_commerce.model.Product;
import com.spear.e_commerce.repository.categoryRepository.CategoryRepository;
import com.spear.e_commerce.repository.productRepository.ProductRepository;
import com.spear.e_commerce.request.AddProductRequest;
import com.spear.e_commerce.request.ProductUpdateRequest;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow
                (() -> new ResourceNotFoundException("Product Not Found"));
    }

    @Override
    public Product addProduct(AddProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category not found with id" + request.getCategoryId()));

        return productRepository.save(createProduct(request, category));



    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {

        return productRepository.findById(productId)
                .map(exsitingProduct -> updateExistingProduct(exsitingProduct, request))
                .map(productRepository:: save)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));


    }

    private Product updateExistingProduct(Product exsitingProduct, ProductUpdateRequest request) {
        exsitingProduct.setName(request.getName());
        exsitingProduct.setBrand(request.getBrand());
        exsitingProduct.setPrice(request.getPrice());
        exsitingProduct.setInventory(request.getInventory());
        exsitingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        exsitingProduct.setCategory(category);
        return exsitingProduct;


    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                () -> {throw new ResourceNotFoundException("Product Not Found");});

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String name, String brand) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
}
