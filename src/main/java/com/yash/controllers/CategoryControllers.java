package com.yash.controllers;

import com.yash.model.Category;
import com.yash.model.User;
import com.yash.service.CategoryService;
import com.yash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryControllers {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category,
                                                   @RequestHeader ("Authorization")String jwt) throws Exception {
        User user=userService.findUserByJwt(jwt);
        Category category1=categoryService.createCategory(category.getName(), user.getId());
        return new ResponseEntity<>(category1, HttpStatus.CREATED);

    }


    @GetMapping
}
