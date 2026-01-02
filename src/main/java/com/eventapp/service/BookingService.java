package com.eventapp.service;

import com.eventapp.entity.*;
import com.eventapp.repository.BookingRepository;
import com.eventapp.repository.UserRepository;
import com.eventapp.repository.VendorSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorSlotRepository slotRepository;

    // -------------------------
    // Create booking by slot
    // -------------------------
    public Booking createBooking(Long userId, Long slotId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        VendorSlot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.getStatus() != SlotStatus.AVAILABLE) {
            throw new RuntimeException("Slot is not available");
        }

        slot.setStatus(SlotStatus.BOOKED);
        slotRepository.save(slot);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSlot(slot);
        booking.setStatus(BookingStatus.PENDING);

        return bookingRepository.save(booking);
    }

    // -------------------------
    // Vendor view bookings
    // -------------------------
    public List<Booking> getBookingsByVendor(Long vendorId) {
        return bookingRepository.findBySlot_Vendor_Id(vendorId);
    }

    // -------------------------
    // User view bookings
    // -------------------------
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUser_Id(userId);
    }

    // -------------------------
    // Update booking status
    // -------------------------
    public Booking updateBookingStatus(Long bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    // -------------------------
    // Admin view all bookings
    // -------------------------
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
