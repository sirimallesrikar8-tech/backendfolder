package com.eventapp.dto;

import java.util.List;

public class VendorResponseDTO {

    private Long id;
    private String businessName;
    private String category;
    private String phone;
    private String location;
    private String profileImage;
    private String coverImage;
    private String status;
    private List<VendorReviewDTO> reviews;

    public VendorResponseDTO() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<VendorReviewDTO> getReviews() { return reviews; }
    public void setReviews(List<VendorReviewDTO> reviews) { this.reviews = reviews; }
}
