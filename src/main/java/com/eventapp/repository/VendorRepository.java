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

    // ================= SEARCH (NULL SAFE + CASE INSENSITIVE) =================

    @Query("""
        SELECT v
        FROM Vendor v
        WHERE v.businessName IS NOT NULL
          AND LOWER(v.businessName) LIKE LOWER(CONCAT('%', :name, '%'))
    """)
    List<Vendor> searchByBusinessName(@Param("name") String name);

    // ================= LOCATION (NULL SAFE + CASE INSENSITIVE) =================

    @Query("""
        SELECT v
        FROM Vendor v
        WHERE v.location IS NOT NULL
          AND LOWER(v.location) = LOWER(:location)
          AND v.status = :status
    """)
    List<Vendor> findApprovedByLocation(
            @Param("location") String location,
            @Param("status") Status status
    );
}
