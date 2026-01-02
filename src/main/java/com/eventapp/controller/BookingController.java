package com.eventapp.controller;

import com.eventapp.dto.BookingResponse;
import com.eventapp.entity.Booking;
import com.eventapp.entity.BookingStatus;
import com.eventapp.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // -------------------------
    // User books a slot
    // -------------------------
    @PostMapping("/book-slot")
    public ResponseEntity<?> bookSlot(
            @RequestParam Long userId,
            @RequestParam Long slotId
    ) {
        try {
            Booking booking = bookingService.createBooking(userId, slotId);
            return ResponseEntity.ok(mapToResponse(booking));
        } catch (RuntimeException e) {
            // Return 400 Bad Request with error message
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // -------------------------
    // Vendor views bookings
    // -------------------------
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByVendor(@PathVariable Long vendorId) {
        List<Booking> bookings = bookingService.getBookingsByVendor(vendorId);
        List<BookingResponse> response = bookings.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // -------------------------
    // User views bookings
    // -------------------------
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByUser(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.getBookingsByUser(userId);
        List<BookingResponse> response = bookings.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // -------------------------
    // Vendor/Admin updates booking status
    // -------------------------
    @PutMapping("/{bookingId}/status")
    public ResponseEntity<?> updateBookingStatus(@PathVariable Long bookingId,
                                                 @RequestParam BookingStatus status) {
        try {
            Booking booking = bookingService.updateBookingStatus(bookingId, status);
            return ResponseEntity.ok(mapToResponse(booking));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // -------------------------
    // Admin views all bookings
    // -------------------------
    @GetMapping("/all")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        List<BookingResponse> response = bookings.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // -------------------------
    // Helper method to convert Booking entity -> BookingResponse DTO
    // -------------------------
    private BookingResponse mapToResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getId());
        response.setUserId(booking.getUser().getId());
        response.setUserName(booking.getUser().getName());
        response.setSlotId(booking.getSlot().getId());
        response.setVendorName(booking.getSlot().getVendor().getBusinessName());
        response.setStatus(booking.getStatus());
        // If you want, you can also add booking time here using LocalDateTime.now()
        return response;
    }
}
