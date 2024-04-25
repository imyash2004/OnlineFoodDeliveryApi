package com.yash.controllers;

import com.yash.model.Food;
import com.yash.model.User;
import com.yash.service.FoodService;
import com.yash.service.RestaurantService;
import com.yash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>>searchFood(@RequestParam String name,

                                                @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserByJwt(jwt);



        List<Food>foods=foodService.searchFood(name);
        return new ResponseEntity<>(foods, HttpStatus.OK);



    }
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<Food>>getRestaurantFood(@PathVariable Long id,
                                                @RequestParam boolean isVeg,
                                                @RequestParam boolean isNonVeg,
                                                @RequestParam boolean isSeasonal,
                                                @RequestParam String category,
                                                @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserByJwt(jwt);



        List<Food>foods=foodService.getRestaurantsFood(id,isVeg,isNonVeg,isSeasonal,category);

        return new ResponseEntity<>(foods, HttpStatus.OK);



    }

}
