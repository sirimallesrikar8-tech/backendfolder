package com.eventapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "vendors")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔥 FIX 1: Ignore back-reference loop
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private String businessName;

    @Column
    private String category;

    @Column
    private String phone;

    @Column
    private String location;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "cover_image")
    private String coverImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    // ---------------- RELATIONSHIPS ----------------

    // 🔥 FIX 2: Prevent slot recursion
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<VendorSlot> slots;

    // 🔥 FIX 3: Prevent media recursion
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<VendorMedia> mediaList;

    // ✅ Reviews already fixed
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<VendorReview> reviews;

    public Vendor() {}

    // ---------------- GETTERS & SETTERS ----------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

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

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public List<VendorSlot> getSlots() { return slots; }
    public void setSlots(List<VendorSlot> slots) { this.slots = slots; }

    public List<VendorMedia> getMediaList() { return mediaList; }
    public void setMediaList(List<VendorMedia> mediaList) { this.mediaList = mediaList; }

    public List<VendorReview> getReviews() { return reviews; }
    public void setReviews(List<VendorReview> reviews) { this.reviews = reviews; }
}
