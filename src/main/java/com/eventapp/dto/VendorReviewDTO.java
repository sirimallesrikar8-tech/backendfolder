package com.eventapp.dto;

import java.time.LocalDateTime;

public class VendorReviewDTO {

    private Long id;
    private int rating;
    private String review;
    private Long userId;
    private LocalDateTime createdAt;

    public VendorReviewDTO(Long id, int rating, String review, Long userId, LocalDateTime createdAt) {
        this.id = id;
        this.rating = rating;
        this.review = review;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
