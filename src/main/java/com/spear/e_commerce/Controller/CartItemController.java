package com.spear.e_commerce.Controller;


import com.spear.e_commerce.Response.ApiResponse;
import com.spear.e_commerce.exceptions.ResourceNotFoundException;
import com.spear.e_commerce.service.CartItem.ICartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;

    public CartItemController(ICartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {

        try {
            cartItemService.addItemToCart(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Add Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }


    }

    @DeleteMapping("/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId ,@PathVariable Long itemId) {
        try {
            cartItemService.removeItem(cartId, itemId);
            return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PostMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long itemId,
                                                          @RequestParam Integer quantity) {
        try {
            cartItemService.updateItem(cartId ,itemId , quantity);
            return ResponseEntity.ok(new ApiResponse("Update Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }


}
