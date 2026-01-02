package com.eventapp.repository;

import com.eventapp.entity.VendorMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorMediaRepository extends JpaRepository<VendorMedia, Long> {

    // Instagram-style vendor gallery
    List<VendorMedia> findByVendor_IdOrderByCreatedAtDesc(Long vendorId);
}
