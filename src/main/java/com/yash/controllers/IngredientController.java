package com.yash.controllers;

import com.yash.model.IngredientCategory;
import com.yash.model.IngredientsItems;
import com.yash.request.IngredientCategoryRequest;
import com.yash.request.IngredientRequest;
import com.yash.service.IngredientsService;
import com.yash.service.impl.IngredientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {


    @Autowired
    private IngredientsService ingredientService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory>createIngredientCategory(@RequestBody IngredientCategoryRequest ingredientCategoryRequest)throws Exception{
        IngredientCategory items=ingredientService.createIngredientCategory(ingredientCategoryRequest.getName(),ingredientCategoryRequest.getRestaurantId());
        return new ResponseEntity<>(items, HttpStatus.CREATED);
    }


    @PostMapping("/item")
    public ResponseEntity<IngredientsItems>createIngredientsItems(@RequestBody IngredientRequest req)throws Exception{
        IngredientsItems items= ingredientService.createIngredientsItems(req.getRestaurantId(), req.getName(), req.getCategoryId());

        return new ResponseEntity<>(items,HttpStatus.CREATED);
    }


    @PutMapping("{id}/stock")
    public ResponseEntity<IngredientsItems>updateStock(@PathVariable Long id)throws Exception{
        IngredientsItems items=ingredientService.updateStock(id);
        return new ResponseEntity<>(items,HttpStatus.OK);

    }


    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItems>>getRestaurantIngredients(@PathVariable Long id )throws Exception{

        List<IngredientsItems>items=ingredientService.findRestaurantsIngredients(id);

        return new ResponseEntity<>(items,HttpStatus.OK);

    }


    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>>getRestaurantIngredientCategory(@PathVariable Long id)throws Exception{
        List<IngredientCategory>categories=ingredientService.finIngredientCategoriesByRestaurantId(id);
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }









}
