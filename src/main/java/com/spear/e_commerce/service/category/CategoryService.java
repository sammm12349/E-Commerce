package com.spear.e_commerce.service.category;

import com.spear.e_commerce.exceptions.AlreadyExistsException;
import com.spear.e_commerce.exceptions.ResourceNotFoundException;
import com.spear.e_commerce.model.Category;
import com.spear.e_commerce.repository.categoryRepository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow
                (() -> new ResourceNotFoundException("Category not found by id " + id ));
    }

    @Override
    public Category getCategoryByName(String name) {

        return Optional.ofNullable(categoryRepository.findByName(name)).orElseThrow(
                () -> new ResourceNotFoundException("Category not found by name " + name));
    }

    @Override
    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {

        if (categoryRepository.existsByName(category.getName())) {

            throw new AlreadyExistsException(
                    category.getName() + " already exists");
        }
        return categoryRepository.save(category);

    }

    @Override
    public Category updateCategory(Category category, Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + id));

        existingCategory.setName(category.getName());

        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategoryById(Long id) {

        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete,
                () -> {
            throw new ResourceNotFoundException("Category not found");
        });

    }
}
