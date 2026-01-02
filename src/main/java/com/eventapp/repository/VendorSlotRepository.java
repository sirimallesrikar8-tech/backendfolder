package com.eventapp.repository;

import com.eventapp.entity.Vendor;
import com.eventapp.entity.VendorSlot;
import com.eventapp.entity.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VendorSlotRepository extends JpaRepository<VendorSlot, Long> {

    // Find all slots for a vendor on a given date with specific status
    @Query("SELECT v FROM VendorSlot v " +
           "WHERE v.vendor = :vendor " +
           "AND v.date = :date " +
           "AND v.status = :status")
    List<VendorSlot> findByVendorAndDateAndStatus(
            @Param("vendor") Vendor vendor,
            @Param("date") LocalDate date,
            @Param("status") SlotStatus status
    );

    // Find all slots for a vendor
    List<VendorSlot> findByVendor(Vendor vendor);

    // Optional: find slots overlapping a time range for a vendor
    @Query("SELECT v FROM VendorSlot v " +
           "WHERE v.vendor = :vendor " +
           "AND v.startTime < :endTime " +
           "AND v.endTime > :startTime")
    List<VendorSlot> findOverlappingSlots(
            @Param("vendor") Vendor vendor,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}
