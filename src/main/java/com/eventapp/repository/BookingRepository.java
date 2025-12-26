package com.eventapp.repository;

import com.eventapp.entity.Booking;
import com.eventapp.entity.User;
import com.eventapp.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Find bookings by user
    List<Booking> findByUser(User user);

    // Find bookings by vendor
    List<Booking> findByVendor(Vendor vendor);
}
