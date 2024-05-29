package com.yash.service.impl;

import com.yash.model.*;
import com.yash.repository.AddressRepo;
import com.yash.repository.OrderItemRepo;
import com.yash.repository.OrderRepo;
import com.yash.repository.UserRepo;
import com.yash.request.OrderRequest;
import com.yash.service.CartService;
import com.yash.service.OrderService;
import com.yash.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;



    @Override
    public Order createOrder(OrderRequest req, User user) throws Exception {
        Address shipAdd=req.getDeliveryAddress();
        Address savedAdd=addressRepo.save(shipAdd);

        if(!user.getAddresses().contains(savedAdd)){
            user.getAddresses().add(savedAdd);
            userRepo.save(user);
        }
        Restaurant restaurant=restaurantService.findRestaurantById(req.getRestaurantId());

        Order order=new Order();
        order.setCustomer(user);
        order.setCreatedAt(new Date());
        order.setOrderStatus("Pending");
        order.setRestaurants(restaurant);
        order.setDeliveryAddress(shipAdd);
//        order.setItems(cartService.findCartByUserId(user.getId()));
        Cart cart=cartService.findCartByUserId(user.getId());
        List<OrderItem>items=new ArrayList<>();
        for(CartItem cartItem: cart.getItems()){
            OrderItem item=new OrderItem();
            item.setFood(cartItem.getFood());
            item.setQuantity(cartItem.getQuantity());
            item.setIngredients(cartItem.getIngredients());
            item.setTotalPrice(cartItem.getTotalPrice());
            OrderItem savedOrderItem=orderItemRepo.save(item);
            items.add(savedOrderItem);

        }
        order.setItems(items);
        Long total= cartService.calculateCartTotal(cart);
        order.setTotalAmount(total);

        orderRepo.save(order);

        restaurant.getOrders().add(order);


        return order;
    }

    @Override
    public Order updateOrder(Long orderId, String OrderStatus) throws Exception {
        Order order=findOrderById(orderId);

        if(OrderStatus.equals("OUT_FOR_DELIVERY")||
           OrderStatus.equals("DELIVERED")
        ||OrderStatus.equals("PENDING")
        ||OrderStatus.equals("COMPLETED")){
            order.setOrderStatus(OrderStatus);
            return orderRepo.save(order);
        }
        throw new Exception("Please select a valid order status:::::::::::::////////////");

    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {

        orderRepo.deleteById(orderId);

    }

    @Override
    public List<Order> getUserOrder(Long userId) throws Exception {
        return orderRepo
                .findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order>orders= orderRepo.findByRestaurantId(restaurantId);

        if(orderStatus!=null){
            orders=orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order>opt=orderRepo.findById(orderId);
        if (opt.isEmpty()){
            throw new Exception("order not found");
        }

        return opt.get();
    }
}
