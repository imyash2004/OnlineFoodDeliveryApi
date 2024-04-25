package com.yash.service.impl;

import com.yash.model.Category;
import com.yash.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public Category createCategory(String name, Long userId) {
        return null;
    }

    @Override
    public List<Category> findCategoryByResataurantId(Long id) throws Exception {
        return null;
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        return null;
    }
}
