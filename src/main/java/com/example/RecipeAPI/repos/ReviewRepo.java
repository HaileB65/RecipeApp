package com.example.RecipeAPI.repos;

import com.example.RecipeAPI.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {

    ArrayList<Review> findByUsername(String username);
}
