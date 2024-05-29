package com.yash.controllers;

import com.yash.model.CartItem;
import com.yash.model.Order;
import com.yash.model.OrderItem;
import com.yash.model.User;
import com.yash.request.OrderRequest;
import com.yash.service.OrderService;
import com.yash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService  userService;

    @PostMapping("/order")
    public ResponseEntity<Order>createOrder(@RequestBody OrderRequest req,
                                               @RequestHeader("Authorization") String jwt) throws Exception{
        User user=userService.findUserByJwt(jwt);
        Order order=orderService.createOrder(req,user);
        return new ResponseEntity<>(order, HttpStatus.CREATED);



    }


    @GetMapping("/order/user")
    public ResponseEntity<List<Order>>getOrderHistory(@RequestHeader("Authorization") String jwt) throws Exception{
        User user=userService.findUserByJwt(jwt);
        List<Order>orders=orderService.getUserOrder(user.getId());
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}
