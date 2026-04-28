package com.spear.e_commerce.dto;

import com.spear.e_commerce.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private long id;
    private String name;
    private List<Product> products;
}
