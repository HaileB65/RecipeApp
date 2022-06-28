package com.example.RecipeAPI.exceptions;

public class NoSuchReviewException extends Exception {

    public NoSuchReviewException(String message) {
        super(message);
    }

    public NoSuchReviewException() {
    }
}
