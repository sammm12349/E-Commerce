package com.spear.e_commerce.repository.OrderRepository;

import com.spear.e_commerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
