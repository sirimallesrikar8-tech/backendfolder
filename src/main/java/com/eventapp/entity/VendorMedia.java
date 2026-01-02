package com.eventapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vendor_media")
public class VendorMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    @JsonIgnore              // ✅ hides vendor in API response
    @Schema(hidden = true)   // ✅ hides vendor in Swagger
    private Vendor vendor;

    @Column(nullable = false)
    private String imageUrl;

    private String caption;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // ---------------- CONSTRUCTORS ----------------
    public VendorMedia() {
        this.createdAt = LocalDateTime.now();
    }

    // ---------------- GETTERS & SETTERS ----------------

    public Long getId() {
        return id;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
