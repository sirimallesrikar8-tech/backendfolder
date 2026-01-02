package com.eventapp.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "vendors")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to the User table
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String businessName;

    @Column
    private String category;

    @Column
    private String phone;

    @Column
    private String location;

    // ✅ Profile image (logo/avatar)
    @Column(name = "profile_image")
    private String profileImage;

    // ✅ Cover image (banner)
    @Column(name = "cover_image")
    private String coverImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    // ---------------- RELATIONSHIPS ----------------

    // Vendor slots
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VendorSlot> slots;

    // Vendor gallery (photos like Instagram)
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VendorMedia> mediaList;

    // Vendor reviews
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VendorReview> reviews;

    // ---------- Constructors ----------
    public Vendor() {}

    // ---------- Getters & Setters ----------
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
