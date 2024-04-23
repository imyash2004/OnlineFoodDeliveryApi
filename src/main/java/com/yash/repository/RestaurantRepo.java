package com.yash.repository;

import com.yash.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant,Long> {
    @Query("SELECT r FROM Restaurant r WHERE lower(r.name) Like lower(concat('%',:query,'%') )  " +
            "OR lower(r.cuisineType)LIKE lower(concat('%',:query,'%') ) ")
    List<Restaurant>findBySearchQuery(String query);
    Restaurant findOwnerById(Long userId);
    Restaurant findByOwnerId(Long Id);

    Restaurant findRestaurantById(Long restaurantId);
}
