package com.yash.repository;

import com.yash.model.IngredientsItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientItemRepo extends JpaRepository<IngredientsItems,Long> {
    List<IngredientsItems>findByRestaurantId(Long id);
}
