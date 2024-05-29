package com.yash.repository;

import com.yash.model.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientCategoryRepo extends JpaRepository<IngredientCategory,Long> {


    List<IngredientCategory> findByRestaurantId(Long id);
}
