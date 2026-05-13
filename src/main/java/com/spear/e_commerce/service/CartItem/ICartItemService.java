package com.spear.e_commerce.service.CartItem;

import com.spear.e_commerce.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId,Long productId,int quantity);
    void removeItem(Long cartId,Long productId);
    void updateItem(Long cartId,Long productId,int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
