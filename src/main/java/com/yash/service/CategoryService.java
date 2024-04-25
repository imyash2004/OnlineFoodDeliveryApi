package com.yash.service;

import com.yash.model.Category;

import java.util.List;

public interface CategoryService {

    public Category createCategory(String name,Long userId);


    public List<Category>findCategoryByResataurantId(Long id)throws Exception;
    public Category findCategoryById(Long id)throws Exception;



}
