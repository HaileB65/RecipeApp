package com.example.RecipeAPI.repos;

import com.example.RecipeAPI.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe, Long> {

    ArrayList<Recipe> findByNameContaining(String name);

//    ArrayList<Recipe> findByRating(Integer rating);
}
