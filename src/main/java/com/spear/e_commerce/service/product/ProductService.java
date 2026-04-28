package com.spear.e_commerce.service.product;

import com.spear.e_commerce.Mapper.ProductMapper;
import com.spear.e_commerce.dto.ProductDto;
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
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }


    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow
                (() -> new ResourceNotFoundException("Product Not Found"));
        return productMapper.toDTO(product);

    }

    @Override
    public ProductDto addProduct(AddProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category not found with id" + request.getCategoryId()));

        return productMapper.toDTO(productRepository.save(createProduct(request, category)));



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
    public ProductDto updateProduct(ProductUpdateRequest request, Long productId) {

        return productRepository.findById(productId)
                .map(exsitingProduct -> updateExistingProduct(exsitingProduct, request))
                .map(productRepository:: save)
                .map(productMapper::toDTO)
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
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        return productRepository.findByCategory(category)
                .stream()
            .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand)
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryAndBrand(category,brand)
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByName(String name) {
        return productRepository.findByName(name)
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByBrandAndName(String name, String brand) {
        return productRepository.findByBrandAndName(brand,name)
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
}
