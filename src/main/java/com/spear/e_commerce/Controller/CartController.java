package com.spear.e_commerce.Controller;


import com.spear.e_commerce.Response.ApiResponse;
import com.spear.e_commerce.exceptions.ResourceNotFoundException;
import com.spear.e_commerce.model.Cart;
import com.spear.e_commerce.service.Cart.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/carts")

public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Success", cart));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Success. Cart Cleared", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId) {
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("Success", totalPrice));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
