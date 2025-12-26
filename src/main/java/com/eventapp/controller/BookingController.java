package com.eventapp.controller;

import com.eventapp.entity.Booking;
import com.eventapp.entity.BookingStatus;
import com.eventapp.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // User creates booking
    @PostMapping("/create")
    public Booking createBooking(@RequestParam Long userId,
                                 @RequestParam Long vendorId,
                                 @RequestBody Booking booking) {
        return bookingService.createBooking(userId, vendorId, booking);
    }

    // Vendor views bookings
    @GetMapping("/vendor/{vendorId}")
    public List<Booking> getBookingsByVendor(@PathVariable Long vendorId) {
        return bookingService.getBookingsByVendor(vendorId);
    }

    // User views bookings
    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable Long userId) {
        return bookingService.getBookingsByUser(userId);
    }

    // Vendor/Admin updates booking status
    @PutMapping("/{bookingId}/status")
    public Booking updateBookingStatus(@PathVariable Long bookingId,
                                       @RequestParam BookingStatus status) {
        return bookingService.updateBookingStatus(bookingId, status);
    }

    // Admin views all bookings
    @GetMapping("/all")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }
}
