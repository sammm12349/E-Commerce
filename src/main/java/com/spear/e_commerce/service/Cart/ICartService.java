package com.spear.e_commerce.service.Cart;


import com.spear.e_commerce.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

}
