package com.spear.e_commerce.service.CartItem;

import com.spear.e_commerce.dto.ProductDto;
import com.spear.e_commerce.exceptions.ResourceNotFoundException;
import com.spear.e_commerce.model.Cart;
import com.spear.e_commerce.model.CartItem;
import com.spear.e_commerce.repository.CartItemRepository.CartItemRepository;
import com.spear.e_commerce.repository.CartRepository.CartRepository;
import com.spear.e_commerce.service.Cart.ICartService;
import com.spear.e_commerce.service.product.IProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;

    public CartItemService(CartItemRepository cartItemRepository, CartRepository cartRepository, IProductService productService, ICartService cartService) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.cartService = cartService;
    }


    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        ProductDto product = productService.getProductById(productId);
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if(cartItem.getId() == null){
            cartItem.setCart(cart);
            cartItem.setId(productId);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }

        else{
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartItemRepository.save(cartItem);


    }

    @Override
    public void removeItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);

    }

    @Override
    public void updateItem(Long cartId, Long productId, int quantity) {

        Cart cart = cartService.getCart(cartId);
        cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct()
                        .getId()
                        .equals(productId))
                .findFirst().ifPresent(item ->
                {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();

                });

        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);


    }


    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return  cart.getCartItems()
                .stream()
                .filter
                        (item -> item.getProduct().
                                getId().
                                equals(productId)).
                findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));

    }



    }

