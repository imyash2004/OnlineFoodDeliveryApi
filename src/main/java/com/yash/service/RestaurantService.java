package com.yash.service;

import com.yash.dto.RestaurantDto;
import com.yash.model.Restaurant;
import com.yash.model.User;
import com.yash.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant)throws Exception;



    public void deleteRestaurant(long restaurantId) throws Exception;
    public List<Restaurant>getAllRestaurant();
    public List<Restaurant>searchRestaurant(String keyword);


    public Restaurant findRestaurantById(long id)throws Exception;



    public Restaurant getRestaurantByUserId(Long userId) throws  Exception;


    public RestaurantDto addToFavourities(Long restaurantId,User user)throws Exception;



    public Restaurant updateRestaurantStatus(Long id)throws Exception;




}
