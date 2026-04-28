package com.spear.e_commerce.service.category;

import com.spear.e_commerce.dto.CategoryDto;
import com.spear.e_commerce.model.Category;

import java.util.List;

public interface ICategoryService {
    CategoryDto getCategoryById(Long id);
    CategoryDto getCategoryByName(String name);
    List<CategoryDto> getAllCategories();
    CategoryDto addCategory(Category category);
    CategoryDto updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);
}
