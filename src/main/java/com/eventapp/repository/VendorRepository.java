package com.eventapp.repository;

import com.eventapp.entity.Status;
import com.eventapp.entity.User;
import com.eventapp.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    // ================= BASIC =================

    Optional<Vendor> findByUser(User user);

    List<Vendor> findByStatus(Status status);


    // ================= SEARCH BY BUSINESS NAME =================
    // ✅ JOIN FETCH avoids LazyInitializationException
    // ✅ Status fixed to APPROVED (safe for production)
    // ✅ Case-insensitive + trim search

    @Query("""
        SELECT v
        FROM Vendor v
        JOIN FETCH v.user u
        WHERE v.businessName IS NOT NULL
          AND LOWER(TRIM(v.businessName)) LIKE LOWER(CONCAT('%', :name, '%'))
          AND v.status = 'APPROVED'
    """)
    List<Vendor> searchApprovedVendorsByName(
            @Param("name") String name
    );


    // ================= SEARCH BY LOCATION =================
    // ✅ JOIN FETCH avoids LazyInitializationException
    // ✅ APPROVED vendors only
    // ✅ Partial + case-insensitive match

    @Query("""
        SELECT v
        FROM Vendor v
        JOIN FETCH v.user u
        WHERE v.location IS NOT NULL
          AND LOWER(TRIM(v.location)) LIKE LOWER(CONCAT('%', :location, '%'))
          AND v.status = 'APPROVED'
    """)
    List<Vendor> searchApprovedVendorsByLocation(
            @Param("location") String location
    );
}
