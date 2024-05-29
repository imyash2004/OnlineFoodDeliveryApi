package com.yash.service;

import com.yash.model.Order;
import com.yash.model.User;
import com.yash.request.OrderRequest;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderRequest req, User user) throws Exception;
    public Order updateOrder(Long orderId,String OrderStatus)throws Exception;
    public void cancelOrder(Long orderId)throws Exception;

    public List<Order> getUserOrder(Long userId)throws Exception;
    public List<Order>getRestaurantOrder(Long restaurantId,String orderStatus)throws Exception;

    public Order findOrderById(Long orderId)throws Exception;

}
