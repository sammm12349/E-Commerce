package com.spear.e_commerce.service.product;

import com.spear.e_commerce.dto.ProductDto;
import com.spear.e_commerce.model.Product;
import com.spear.e_commerce.request.AddProductRequest;
import com.spear.e_commerce.request.ProductUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
public interface IProductService {
    ProductDto getProductById(Long id);
    ProductDto addProduct(AddProductRequest request);
    ProductDto updateProduct(ProductUpdateRequest request, Long productId);
    void deleteProduct(Long id);
    List<ProductDto> getAllProducts();
    List<ProductDto> getProductsByCategory(String category);
    List<ProductDto> getProductsByBrand(String  brand);
    List<ProductDto> getProductsByCategoryAndBrand(String category, String brand);
    List<ProductDto> getProductsByName(String name);
    List<ProductDto> getProductsByBrandAndName(String name, String brand);
    Long countProductsByBrandAndName(String brand,String name);

}
