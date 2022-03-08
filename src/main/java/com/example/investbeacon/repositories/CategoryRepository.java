package com.example.investbeacon.repositories;

import com.example.investbeacon.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByCategory(String category);
}
