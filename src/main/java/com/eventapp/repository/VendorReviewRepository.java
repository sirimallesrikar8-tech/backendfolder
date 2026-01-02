package com.eventapp.repository;

import com.eventapp.entity.Vendor;
import com.eventapp.entity.VendorReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VendorReviewRepository extends JpaRepository<VendorReview, Long> {

    List<VendorReview> findByVendor(Vendor vendor);

    @Query("SELECT AVG(v.rating) FROM VendorReview v WHERE v.vendor = :vendor")
    Double findAverageRating(Vendor vendor);
}
