package com.eventapp.controller;

import com.eventapp.entity.Status;
import com.eventapp.entity.Vendor;
import com.eventapp.entity.VendorReview;
import com.eventapp.repository.VendorRepository;
import com.eventapp.service.UserService;
import com.eventapp.service.VendorReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private VendorReviewService vendorReviewService;

    // ---------------------------------------------------
    // ✅ GET SINGLE VENDOR PROFILE
    // ---------------------------------------------------
    @GetMapping("/{vendorId}")
    public ResponseEntity<VendorDTO> getVendorById(@PathVariable Long vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        return ResponseEntity.ok(new VendorDTO(vendor));
    }

    // ---------------------------------------------------
    // ✅ UPDATE VENDOR PROFILE
    // ---------------------------------------------------
    @PutMapping("/{vendorId}")
    public ResponseEntity<VendorDTO> updateVendor(
            @PathVariable Long vendorId,
            @RequestBody VendorUpdateRequest request
    ) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        vendor.setBusinessName(request.getBusinessName());
        vendor.setCategory(request.getCategory());
        vendor.setPhone(request.getPhone());
        vendor.setLocation(request.getLocation());

        vendorRepository.save(vendor);

        return ResponseEntity.ok(new VendorDTO(vendor));
    }

    // ---------------------------------------------------
    // ⭐ ADD RATING & REVIEW
    // ---------------------------------------------------
    @PostMapping("/{vendorId}/reviews")
    public ResponseEntity<ReviewResponseDTO> addReview(
            @PathVariable Long vendorId,
            @RequestBody ReviewRequest request
    ) {

        VendorReview review = vendorReviewService.addReview(vendorId, request);

        return ResponseEntity.ok(new ReviewResponseDTO(review));
    }

    // ---------------------------------------------------
    // ⭐ GET ALL REVIEWS OF A VENDOR (FIXED FOR SWAGGER)
    // ---------------------------------------------------
    @GetMapping("/{vendorId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getReviews(
            @PathVariable Long vendorId
    ) {

        List<ReviewResponseDTO> response =
                vendorReviewService.getReviews(vendorId)
                        .stream()
                        .map(ReviewResponseDTO::new)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // ---------------------------------------------------
    // ⭐ GET AVERAGE RATING OF A VENDOR
    // ---------------------------------------------------
    @GetMapping("/{vendorId}/rating")
    public ResponseEntity<Double> getAverageRating(
            @PathVariable Long vendorId
    ) {
        return ResponseEntity.ok(
                vendorReviewService.getAverageRating(vendorId)
        );
    }

    // ---------------------------------------------------
    // EXISTING APIs (UNCHANGED)
    // ---------------------------------------------------
    @GetMapping("/status/{status}")
    public ResponseEntity<List<VendorDTO>> getVendorsByStatus(
            @PathVariable String status
    ) {

        Status st = Status.valueOf(status.toUpperCase());
        List<Vendor> vendors = userService.getVendorsByStatus(st);

        return ResponseEntity.ok(
                vendors.stream().map(VendorDTO::new).collect(Collectors.toList())
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<VendorDTO>> searchVendors(
            @RequestParam String name
    ) {

        return ResponseEntity.ok(
                vendorRepository.searchByBusinessName(name)
                        .stream()
                        .map(VendorDTO::new)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/location")
    public ResponseEntity<List<VendorDTO>> getVendorsByLocation(
            @RequestParam String location
    ) {

        return ResponseEntity.ok(
                vendorRepository.findApprovedByLocation(location)
                        .stream()
                        .map(VendorDTO::new)
                        .collect(Collectors.toList())
        );
    }

    // ---------------------------------------------------
    // DTOs
    // ---------------------------------------------------

    // ✅ Vendor DTO
    public static class VendorDTO {
        private Long id;
        private String businessName;
        private String category;
        private String location;
        private String phone;
        private String userName;
        private String userEmail;

        public VendorDTO(Vendor vendor) {
            this.id = vendor.getId();
            this.businessName = vendor.getBusinessName();
            this.category = vendor.getCategory();
            this.location = vendor.getLocation();
            this.phone = vendor.getPhone();
            this.userName = vendor.getUser().getName();
            this.userEmail = vendor.getUser().getEmail();
        }

        public Long getId() { return id; }
        public String getBusinessName() { return businessName; }
        public String getCategory() { return category; }
        public String getLocation() { return location; }
        public String getPhone() { return phone; }
        public String getUserName() { return userName; }
        public String getUserEmail() { return userEmail; }
    }

    // ✅ Vendor Update DTO
    public static class VendorUpdateRequest {
        private String businessName;
        private String category;
        private String phone;
        private String location;

        public String getBusinessName() { return businessName; }
        public String getCategory() { return category; }
        public String getPhone() { return phone; }
        public String getLocation() { return location; }
    }

    // ✅ Review Request DTO
    public static class ReviewRequest {
        private Long userId;
        private int rating;
        private String review;

        public Long getUserId() { return userId; }
        public int getRating() { return rating; }
        public String getReview() { return review; }
    }

    // ✅ Review Response DTO (FIXES SWAGGER)
    public static class ReviewResponseDTO {
        private Long id;
        private Long userId;
        private int rating;
        private String review;
        private LocalDateTime createdAt;

        public ReviewResponseDTO(VendorReview review) {
            this.id = review.getId();
            this.userId = review.getUserId();
            this.rating = review.getRating();
            this.review = review.getReview();
            this.createdAt = review.getCreatedAt();
        }

        public Long getId() { return id; }
        public Long getUserId() { return userId; }
        public int getRating() { return rating; }
        public String getReview() { return review; }
        public LocalDateTime getCreatedAt() { return createdAt; }
    }
}
