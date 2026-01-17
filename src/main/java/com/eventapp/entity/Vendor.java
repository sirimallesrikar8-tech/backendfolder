package com.eventapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "vendors")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Prevent infinite loop + lazy loading crash
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private String businessName;

    private String category;
    private String phone;
    private String location;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "cover_image")
    private String coverImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    // ---------- RELATIONSHIPS (IGNORE ALL IN JSON) ----------

    @OneToMany(
            mappedBy = "vendor",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<VendorSlot> slots;

    @OneToMany(
            mappedBy = "vendor",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<VendorMedia> mediaList;

    @OneToMany(
            mappedBy = "vendor",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<VendorReview> reviews;

    // ---------- CONSTRUCTORS ----------
    public Vendor() {}

    // ---------- GETTERS & SETTERS ----------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<VendorSlot> getSlots() {
        return slots;
    }

    public void setSlots(List<VendorSlot> slots) {
        this.slots = slots;
    }

    public List<VendorMedia> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<VendorMedia> mediaList) {
        this.mediaList = mediaList;
    }

    public List<VendorReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<VendorReview> reviews) {
        this.reviews = reviews;
    }
}
