package com.eventapp.service.impl;

import com.eventapp.dto.ReviewRequestDTO;
import com.eventapp.entity.Vendor;
import com.eventapp.entity.VendorReview;
import com.eventapp.repository.VendorRepository;
import com.eventapp.repository.VendorReviewRepository;
import com.eventapp.service.VendorReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VendorReviewServiceImpl implements VendorReviewService {

    @Autowired
    private VendorReviewRepository vendorReviewRepository;

    @Autowired
    private VendorRepository vendorRepository;

    // ================= ADD REVIEW =================
    @Override
    public VendorReview addReview(Long vendorId, ReviewRequestDTO request) {

        // ✅ Validate rating (VERY IMPORTANT)
        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        VendorReview review = new VendorReview();
        review.setVendor(vendor);
        review.setUserId(request.getUserId());
        review.setRating(request.getRating());
        review.setReview(request.getReview());
        review.setCreatedAt(LocalDateTime.now());

        return vendorReviewRepository.save(review);
    }

    // ================= GET REVIEWS =================
    @Override
    public List<VendorReview> getReviews(Long vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        return vendorReviewRepository.findByVendor(vendor);
    }

    // ================= GET AVERAGE RATING =================
    @Override
    public Double getAverageRating(Long vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        List<VendorReview> reviews = vendorReviewRepository.findByVendor(vendor);

        return reviews.stream()
                .mapToInt(VendorReview::getRating)
                .filter(rating -> rating > 0)
                .average()
                .orElse(0.0);
    }
}
