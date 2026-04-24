package com.spear.e_commerce.service.product;

import com.spear.e_commerce.model.Product;
import com.spear.e_commerce.request.AddProductRequest;
import com.spear.e_commerce.request.ProductUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
public interface IProductService {
    Product getProductById(Long id);
    Product addProduct(AddProductRequest request);
    Product updateProduct(ProductUpdateRequest request, Long productId);
    void deleteProduct(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String  brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String name, String brand);
    Long countProductsByBrandAndName(String brand,String name);

}
