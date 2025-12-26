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

    // ---------------- Existing ----------------
    List<Vendor> findByLocationAndStatus(String location, Status status);

    Optional<Vendor> findByUser(User user);

    // ---------------- Search vendors by business name ----------------
    @Query("SELECT v FROM Vendor v WHERE LOWER(v.businessName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Vendor> searchByBusinessName(@Param("name") String name);

    // ---------------- Get approved vendors by location ----------------
    @Query("SELECT v FROM Vendor v WHERE v.location = :location AND v.status = com.eventapp.entity.Status.APPROVED")
    List<Vendor> findApprovedByLocation(@Param("location") String location);
}
