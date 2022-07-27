package com.example.RecipeAPI.controllers;

import com.example.RecipeAPI.security.CustomUserDetailsService;
import com.example.RecipeAPI.services.ReviewService;
import com.example.RecipeAPI.exceptions.NoSuchRecipeException;
import com.example.RecipeAPI.exceptions.NoSuchReviewException;
import com.example.RecipeAPI.models.Recipe;
import com.example.RecipeAPI.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable("id") Long id) {
        try {
            Review retrievedReview = reviewService.getReviewById(id);
            return ResponseEntity.ok(retrievedReview);
        } catch (IllegalStateException | NoSuchReviewException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<?> getReviewByRecipeId(@PathVariable("recipeId") Long recipeId) {
        try {
            ArrayList<Review> reviews = reviewService.getReviewByRecipeId(recipeId);
            return ResponseEntity.ok(reviews);
        } catch (NoSuchRecipeException | NoSuchReviewException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getReviewByUsername(@PathVariable("username") String username) {
        try {
            ArrayList<Review> reviews = reviewService.getReviewByUsername(username);
            return ResponseEntity.ok(reviews);
        } catch (NoSuchReviewException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{recipeId}")
    public ResponseEntity<?> postNewReview(@RequestBody Review review, @PathVariable("recipeId") Long recipeId) {
        try {
            Recipe insertedRecipe = reviewService.postNewReview(review, recipeId);
            return ResponseEntity.created(insertedRecipe.getLocationURI()).body(insertedRecipe);
        } catch (NoSuchRecipeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteReviewById(@PathVariable("id") Long id) {
//        try {
//            Review review = reviewService.deleteReviewById(id);
//            return ResponseEntity.ok(review);
//        } catch (NoSuchReviewException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

//    @PatchMapping
//    public ResponseEntity<?> updateReviewById(@RequestBody Review reviewToUpdate) {
//        try {
//            Review review = reviewService.updateReviewById(reviewToUpdate);
//            return ResponseEntity.ok(review);
//        } catch (NoSuchReviewException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    // endpoints with Spring Security added
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @PostMapping("/{recipeId}")
    public ResponseEntity<?> postNewReview(@RequestBody @Valid Review review,
                                           @PathVariable("recipeId") Long recipeId, Principal principal) {
        try {
            review.setUser(customUserDetailsService.getUser(principal.getName()));
            Recipe insertedRecipe = reviewService.postNewReview(review, recipeId);
            return ResponseEntity.created(insertedRecipe.getLocationURI()).body(insertedRecipe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'Review', 'delete')")
    public ResponseEntity<?> deleteReviewById(@PathVariable("id")Long id) {
        try {
            Review review = reviewService.deleteReviewById(id);
            return ResponseEntity.ok(review);
        } catch (NoSuchReviewException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping
    @PreAuthorize("hasPermission(#reviewToUpdate.id, 'Review', 'edit')")
    public ResponseEntity<?> updateReviewById(@RequestBody Review reviewToUpdate) {
        try {
            Review review = reviewService.updateReviewById(reviewToUpdate);
            return ResponseEntity.ok(review);
        } catch (NoSuchReviewException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
