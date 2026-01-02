package com.eventapp.entity;

import jakarta.persistence.*;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private VendorSlot slot;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    // ---------- Getters and Setters ----------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public VendorSlot getSlot() { return slot; }
    public void setSlot(VendorSlot slot) { this.slot = slot; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
}
