package com.eventapp.repository;

import com.eventapp.entity.Booking;
import com.eventapp.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUser_Id(Long userId);               // bookings by user
    List<Booking> findBySlot_Vendor_Id(Long vendorId);      // bookings by vendor

    long countByUser_IdAndStatus(Long userId, BookingStatus status);
    long countBySlot_Vendor_IdAndStatus(Long vendorId, BookingStatus status);
}
