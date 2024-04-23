package com.yash.controllers;

import com.yash.dto.RestaurantDto;
import com.yash.model.Restaurant;
import com.yash.model.User;
import com.yash.request.CreateRestaurantRequest;
import com.yash.service.RestaurantService;
import com.yash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;



    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>>searchRestaurant(@RequestParam String keyword,
                                                            @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwt(jwt);
        List<Restaurant> restaurants=restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);


    }


    @GetMapping()
    public ResponseEntity<List<Restaurant>>searchRestaurant(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwt(jwt);
        List<Restaurant> restaurants=restaurantService.getAllRestaurant();
        return new ResponseEntity<>(restaurants,HttpStatus.OK);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Restaurant>findRestaurantById(@RequestHeader("Authorization") String jwt,
                                                        @PathVariable Long id) throws Exception {
        User user=userService.findUserByJwt(jwt);
        Restaurant restaurant=restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant,HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favourites")
    public ResponseEntity<RestaurantDto> addToFavourites(@RequestHeader("Authorization") String jwt,
                                                      @PathVariable Long id) throws Exception {
        User user=userService.findUserByJwt(jwt);
        RestaurantDto restaurantDto=restaurantService.addToFavourities(id,user);
        return new ResponseEntity<>(restaurantDto,HttpStatus.OK);
    }
}
