package com.spear.e_commerce.Mapper;

import com.spear.e_commerce.dto.CategoryDto;
import com.spear.e_commerce.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);

}
