package com.eventapp.service.impl;

import com.eventapp.dto.ReviewRequestDTO;
import com.eventapp.entity.User;
import com.eventapp.entity.Vendor;
import com.eventapp.entity.VendorReview;
import com.eventapp.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository; // ✅ Added to check user existence

    // ================= ADD REVIEW =================
    @Override
    public VendorReview addReview(Long vendorId, ReviewRequestDTO request) {

        // 1️⃣ Validate rating
        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        // 2️⃣ Validate vendor exists
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        // 3️⃣ Validate user exists
        if (!userRepository.existsById(request.getUserId())) {
            throw new RuntimeException("User not found with ID: " + request.getUserId());
        }

        // 4️⃣ Create and save review
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
