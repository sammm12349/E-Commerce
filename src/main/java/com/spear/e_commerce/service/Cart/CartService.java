package com.spear.e_commerce.service.Cart;

import com.spear.e_commerce.exceptions.ResourceNotFoundException;
import com.spear.e_commerce.model.Cart;
import com.spear.e_commerce.model.CartItem;
import com.spear.e_commerce.repository.CartItemRepository.CartItemRepository;
import com.spear.e_commerce.repository.CartRepository.CartRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }


    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cart not found"));
        BigDecimal total = cart.getTotalAmount();
        cart.setTotalAmount(total);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getCartItems()
                .stream()
                .map(CartItem :: getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


    }
}
