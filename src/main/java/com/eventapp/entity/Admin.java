package com.eventapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to the User table
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Add any admin-specific fields if needed in future

    // ---------- Constructors ----------
    public Admin() {
    }

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
}
