package com.yash.controllers;

import com.yash.model.Restaurant;
import com.yash.model.User;
import com.yash.request.CreateRestaurantRequest;
import com.yash.response.MessageResponse;
import com.yash.service.RestaurantService;
import com.yash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest req,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {
        System.out.println(jwt);
        User user = userService.findUserByJwt(jwt);
        System.out.println(user);


        Restaurant restaurant = restaurantService.createRestaurant(req, user);

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestHeader("Authorization") String jwt,
                                                       @RequestBody CreateRestaurantRequest req,
                                                       @PathVariable Long id) throws Exception {

        User user = userService.findUserByJwt(jwt);

        Restaurant restaurant = restaurantService.updateRestaurant(id, req);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);

    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> changeRestaurantStatus(@PathVariable Long id,
                                                                  @RequestHeader("Authorization") String jwt
                                                                  ) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Restaurant restaurant=restaurantService.updateRestaurantStatus(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);

    }



    @GetMapping("/user")
    public ResponseEntity<Restaurant>findRestaurantByUserId(@RequestHeader("Authorization") String jwt
                                                             ) throws Exception {
        User user=userService.findUserByJwt(jwt);
        Restaurant restaurant=restaurantService.getRestaurantByUserId(user.getId());
        return new ResponseEntity<>(restaurant,HttpStatus.OK);

    }


    }
