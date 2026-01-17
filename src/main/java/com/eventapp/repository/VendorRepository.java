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

    // ✅ REQUIRED for /api/admin/vendor/status/{status}
    List<Vendor> findByStatus(Status status);

    // ================= FILTERING =================

    List<Vendor> findByLocationAndStatus(String location, Status status);

    // ================= SEARCH =================

    @Query("""
        SELECT v
        FROM Vendor v
        WHERE LOWER(v.businessName) LIKE LOWER(CONCAT('%', :name, '%'))
    """)
    List<Vendor> searchByBusinessName(@Param("name") String name);

    // ================= APPROVED VENDORS =================

    @Query("""
        SELECT v
        FROM Vendor v
        WHERE v.location = :location
          AND v.status = :status
    """)
    List<Vendor> findApprovedByLocation(
            @Param("location") String location,
            @Param("status") Status status
    );
}
