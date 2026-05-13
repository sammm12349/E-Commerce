package com.spear.e_commerce.model;

import jakarta.persistence.*;


import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "cartitem")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public void setTotalPrice(){
        this.totalPrice = this.unitPrice.multiply(new BigDecimal(quantity));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CartItem(Long id, int quantity, BigDecimal unitPrice, BigDecimal totalPrice, Cart cart, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.cart = cart;
        this.product = product;
    }
    public CartItem() {

    }
}
