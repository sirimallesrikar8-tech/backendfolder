package com.eventapp.dto;

public class ReviewRequestDTO {
    private Long userId;
    private int rating;
    private String review;

    public ReviewRequestDTO() {}

    public ReviewRequestDTO(Long userId, int rating, String review) {
        this.userId = userId;
        this.rating = rating;
        this.review = review;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }
}
