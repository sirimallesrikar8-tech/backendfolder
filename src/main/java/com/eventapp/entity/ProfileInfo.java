package com.eventapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "profile_info")
public class ProfileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One profile info per user
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "gst_number")
    private String gstNumber;

    @Column(name = "pan_or_tan")
    private String panOrTan;

    @Column(name = "aadhar_number")
    private String aadharNumber;

    @Column(name = "profile_picture")
    private String profilePicture;

    // ---------- Constructors ----------
    public ProfileInfo() {}

    // ---------- Getters & Setters ----------

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

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getPanOrTan() {
        return panOrTan;
    }

    public void setPanOrTan(String panOrTan) {
        this.panOrTan = panOrTan;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
