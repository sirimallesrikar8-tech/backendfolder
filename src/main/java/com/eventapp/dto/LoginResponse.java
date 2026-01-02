package com.eventapp.dto;

public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String token;
    private String profilePicture; // NEW: profile picture URL

    public LoginResponse(Long id, String name, String email, String role, String token, String profilePicture) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.token = token;
        this.profilePicture = profilePicture;
    }

    // ---------- Getters & Setters ----------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }
}
