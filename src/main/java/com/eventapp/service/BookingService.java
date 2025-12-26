package com.eventapp.service;

import com.eventapp.entity.*;
import com.eventapp.repository.BookingRepository;
import com.eventapp.repository.UserRepository;
import com.eventapp.repository.VendorRepository;
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
    private VendorRepository vendorRepository;

    // Create booking
    public Booking createBooking(Long userId, Long vendorId, Booking booking) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        booking.setUser(user);
        booking.setVendor(vendor);
        booking.setStatus(BookingStatus.PENDING);

        return bookingRepository.save(booking);
    }

    // Vendor view bookings
    public List<Booking> getBookingsByVendor(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        return bookingRepository.findByVendor(vendor);
    }

    // User view bookings
    public List<Booking> getBookingsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return bookingRepository.findByUser(user);
    }

    // Update booking status (ACCEPT/REJECT)
    public Booking updateBookingStatus(Long bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    // Admin view all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
