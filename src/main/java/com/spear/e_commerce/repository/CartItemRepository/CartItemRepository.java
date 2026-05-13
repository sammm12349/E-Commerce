package com.spear.e_commerce.repository.CartItemRepository;

import com.spear.e_commerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);
}
