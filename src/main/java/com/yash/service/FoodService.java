package com.yash.service;

import com.yash.model.Category;
import com.yash.model.Food;
import com.yash.model.Restaurant;
import com.yash.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {


    public Food createFood(CreateFoodRequest req, Restaurant restaurant, Category category);
    void  deleteFood(Long foodId)throws Exception;
    public List<Food >getRestaurantsFood(Long restaurantId,boolean isVeg,boolean isNonVeg,boolean isSeasonal,String foodCategory);
    public List<Food>searchFood(String keyword);
    public Food findFoodById(Long foodId)throws Exception;
    public Food updateAvailabilityStatus(Long FoodId)throws Exception;
}
