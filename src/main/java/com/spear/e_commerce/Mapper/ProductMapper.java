package com.spear.e_commerce.Mapper;

import com.spear.e_commerce.dto.ProductDto;
import com.spear.e_commerce.model.Product;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface ProductMapper {
        ProductDto toDTO(Product product );

    }
