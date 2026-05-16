package com.spear.e_commerce.service.Order;

import com.spear.e_commerce.model.Order;

public interface IOrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
}
