package com.spear.e_commerce.service.Order;

import com.spear.e_commerce.Enums.OrderStatus;
import com.spear.e_commerce.exceptions.ResourceNotFoundException;
import com.spear.e_commerce.model.Cart;
import com.spear.e_commerce.model.Order;
import com.spear.e_commerce.model.OrderItem;
import com.spear.e_commerce.model.Product;
import com.spear.e_commerce.repository.OrderRepository.OrderRepository;
import com.spear.e_commerce.repository.productRepository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }


    @Override
    public Order placeOrder(Long userId) {
        return null;
    }

    @Override
    public Order getOrder(Long orderId) {
       return orderRepository.findById(orderId).
               orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }


    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }




    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice(),
                    order,
                    product
            );



        }).toList();
    }






    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList
                .stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }
}
