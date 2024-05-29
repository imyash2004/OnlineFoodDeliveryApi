package com.yash.service.impl;

import com.yash.model.Category;
import com.yash.model.IngredientCategory;
import com.yash.model.IngredientsItems;
import com.yash.model.Restaurant;
import com.yash.repository.CategoryRepo;
import com.yash.repository.IngredientCategoryRepo;
import com.yash.repository.IngredientItemRepo;
import com.yash.service.IngredientsService;
import com.yash.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientsService {
    @Autowired
    private IngredientCategoryRepo ingredientCategoryRepo;

    @Autowired
    private IngredientItemRepo ingredientItemRepo;

    @Autowired
    private RestaurantService restaurantService;




    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {

        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category=new IngredientCategory();
        category.setName(name);
        category.setRestaurant(restaurant);

        return ingredientCategoryRepo.save(category);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> opt=ingredientCategoryRepo.findById(id);
        if(opt.isEmpty()){
            throw new Exception("Ingredient Category not found");
        }
        return opt.get();
    }

    @Override
    public List<IngredientCategory> finIngredientCategoriesByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        List<IngredientCategory> ingredientCategories=ingredientCategoryRepo.findByRestaurantId(id);

        return ingredientCategories;
    }

    @Override
    public IngredientsItems createIngredientsItems(Long restaurantId, String name, Long categoryId) throws Exception {
        IngredientsItems ingredientsItems=new IngredientsItems();
        IngredientCategory category=findIngredientCategoryById(categoryId);
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        ingredientsItems.setCategory(category);
        ingredientsItems.setName(name);
        ingredientsItems.setRestaurant(restaurant);
        IngredientsItems ingredients=ingredientItemRepo.save(ingredientsItems);
        category.getIngredients().add(ingredients);
        //ingredientCategoryRepo.save(category);
        return null;
    }

    @Override
    public List<IngredientsItems> findRestaurantsIngredients(Long restaurantId) {

        return ingredientItemRepo.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItems updateStock(Long id) throws Exception {
        Optional<IngredientsItems>opt=ingredientItemRepo.findById(id);
        if(opt.isEmpty()){
            throw new Exception("Ingredient not found");
        }

        IngredientsItems ingredientsItems=opt.get();
        ingredientsItems.setInStoke(!ingredientsItems.isInStoke());
        return ingredientItemRepo.save(ingredientsItems);
    }
}
