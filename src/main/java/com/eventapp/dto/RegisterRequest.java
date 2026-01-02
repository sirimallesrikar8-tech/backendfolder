package com.eventapp.dto;

import com.eventapp.entity.Role;

public class RegisterRequest {

    private String name;
    private String email;
    private String phone;
    private String password;
    private Role role; // USER / VENDOR / ADMIN

    // Vendor-only fields
    private String businessName;
    private String category;
    private String location;

    // Optional profile picture (frontend can send URL or base64)
    private String profilePicture; // NEW

    // ---------- Getters & Setters ----------

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }
}
