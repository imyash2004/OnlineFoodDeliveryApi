package com.yash.controllers;

import com.yash.model.Order;
import com.yash.model.User;
import com.yash.service.OrderService;
import com.yash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>>getOrderHistory(
            @PathVariable Long id,
            @RequestParam (required = true)String order_Status,
            @RequestHeader("Authorization") String jwt) throws Exception{
        User user=userService.findUserByJwt(jwt);
        List<Order>orders=orderService.getRestaurantOrder(id,order_Status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<Order>updateOrderStatus(
            @PathVariable Long id,
            @PathVariable String orderStatus,
            @RequestHeader("Authorization") String jwt) throws Exception{
        User user=userService.findUserByJwt(jwt);
        Order order=orderService.updateOrder(id,orderStatus);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }


}
