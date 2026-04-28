package com.spear.e_commerce.service.category;

import com.spear.e_commerce.Mapper.CategoryMapper;
import com.spear.e_commerce.dto.CategoryDto;
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
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }


    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow
                (() -> new ResourceNotFoundException("Category not found by id " + id ));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto getCategoryByName(String name) {

        Category category = Optional.ofNullable(categoryRepository.findByName(name)).orElseThrow(
                () -> new ResourceNotFoundException("Category not found by name " + name));

        return categoryMapper.toDto(category);

    }

    @Override
    public List<CategoryDto> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper :: toDto)
                .toList();
    }

    @Override
    public CategoryDto addCategory(Category category) {

        if (categoryRepository.existsByName(category.getName())) {

            throw new AlreadyExistsException(
                    category.getName() + " already exists");
        }
        Category category1 = categoryRepository.save(category);
        return categoryMapper.toDto(category1);


    }

    @Override
    public CategoryDto updateCategory(Category category, Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + id));

        existingCategory.setName(category.getName());

        Category category1 = categoryRepository.save(existingCategory);
        return categoryMapper.toDto(category1);
    }

    @Override
    public void deleteCategoryById(Long id) {

        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete,
                () -> {
            throw new ResourceNotFoundException("Category not found");
        });

    }
}
