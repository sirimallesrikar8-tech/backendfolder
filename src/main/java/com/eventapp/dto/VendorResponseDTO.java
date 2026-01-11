package com.eventapp.dto;

import java.util.List;

public class VendorResponseDTO {

    private Long id;
    private String name;            // NEW
    private String businessName;
    private String category;
    private String phone;
    private String email;           // NEW
    private String location;
    private String gstNumber;       // NEW
    private String panOrTan;        // NEW
    private String aadharNumber;    // NEW (optional)
    private String profileImage;
    private String coverImage;
    private String status;
    private List<VendorReviewDTO> reviews;

    public VendorResponseDTO() {
    }

    // ---------- Getters & Setters ----------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getGstNumber() { return gstNumber; }
    public void setGstNumber(String gstNumber) { this.gstNumber = gstNumber; }

    public String getPanOrTan() { return panOrTan; }
    public void setPanOrTan(String panOrTan) { this.panOrTan = panOrTan; }

    public String getAadharNumber() { return aadharNumber; }
    public void setAadharNumber(String aadharNumber) { this.aadharNumber = aadharNumber; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<VendorReviewDTO> getReviews() { return reviews; }
    public void setReviews(List<VendorReviewDTO> reviews) { this.reviews = reviews; }
}
