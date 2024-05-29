package com.yash.service.impl;

import com.yash.model.Category;
import com.yash.model.Restaurant;
import com.yash.repository.CategoryRepo;
import com.yash.service.CategoryService;
import com.yash.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private RestaurantService restaurantService;



    @Override
    public Category createCategory(String name, Long userId) throws Exception {
        Restaurant restaurant=restaurantService.getRestaurantByUserId(userId);
        Category  category=new Category();
        category.setName(name);
        category.setRestaurant(restaurant);

        return categoryRepo.save(category);


    }

    @Override
    public List<Category> findCategoryByResataurantId(Long id) throws Exception {
        Restaurant restaurant=restaurantService.getRestaurantByUserId(id);
        return categoryRepo.findByRestaurantId(restaurant.getId());
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category>opt=categoryRepo.findById(id);
        if(opt==null || opt.isEmpty() || opt.equals("")){
            throw new Exception("Category not exist");
        }
        return opt.get();
    }
}
