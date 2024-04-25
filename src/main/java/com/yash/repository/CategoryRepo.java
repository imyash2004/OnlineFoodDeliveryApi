package com.yash.repository;

import com.yash.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
    public List<Category>findByRestaurantId(Long id);
}
