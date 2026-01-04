package com.eventapp.dto;

import java.time.LocalDateTime;

public class VendorReviewDTO {

    private Long id;
    private int rating;
    private String review;
    private Long userId;
    private LocalDateTime createdAt;

    // ✅ Default constructor (IMPORTANT for Spring / Jackson)
    public VendorReviewDTO() {
    }

    // ✅ All-args constructor (good for mapping)
    public VendorReviewDTO(Long id, int rating, String review, Long userId, LocalDateTime createdAt) {
        this.id = id;
        this.rating = rating;
        this.review = review;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    // ---------- Getters & Setters ----------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
