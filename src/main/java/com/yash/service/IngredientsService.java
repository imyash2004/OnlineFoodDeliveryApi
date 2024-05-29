package com.yash.service;

import com.yash.model.IngredientCategory;
import com.yash.model.IngredientsItems;

import java.util.List;

public interface IngredientsService {

    public IngredientCategory createIngredientCategory(String name,Long restaurantId)throws Exception;
    public IngredientCategory findIngredientCategoryById(Long id)throws Exception;
    public List<IngredientCategory> finIngredientCategoriesByRestaurantId(Long id) throws Exception;

    public IngredientsItems createIngredientsItems(Long restaurantId, String name,Long categoryId)throws Exception;

    public List<IngredientsItems>findRestaurantsIngredients(Long restaurantId);

    public IngredientsItems updateStock(Long id)throws Exception;




}
