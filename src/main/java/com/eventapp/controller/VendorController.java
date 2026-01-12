package com.eventapp.controller;

import com.eventapp.dto.ReviewRequestDTO;
import com.eventapp.dto.VendorResponseDTO;
import com.eventapp.dto.VendorReviewDTO;
import com.eventapp.entity.Status;
import com.eventapp.entity.Vendor;
import com.eventapp.entity.VendorReview;
import com.eventapp.repository.UserRepository;
import com.eventapp.repository.VendorRepository;
import com.eventapp.service.UserService;
import com.eventapp.service.VendorReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private UserRepository userRepository; // ✅ For checking user existence

    // ---------------- POST A REVIEW FOR A VENDOR ----------------
    @PostMapping("/{vendorId}/reviews")
    public ResponseEntity<?> addReview(
            @PathVariable Long vendorId,
            @RequestBody ReviewRequestDTO reviewRequest
    ) {
        try {
            if (!userRepository.existsById(reviewRequest.getUserId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User not found with ID: " + reviewRequest.getUserId());
            }

            VendorReview savedReview = vendorReviewService.addReview(vendorId, reviewRequest);

            return ResponseEntity.ok(new VendorReviewDTO(
                    savedReview.getId(),
                    savedReview.getRating(),
                    savedReview.getReview(),
                    savedReview.getUserId(),
                    savedReview.getCreatedAt()
            ));

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong: " + ex.getMessage());
        }
    }

    // ---------------- GET SINGLE VENDOR ----------------
    @GetMapping("/{vendorId}")
    public ResponseEntity<VendorResponseDTO> getVendorById(@PathVariable Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        return ResponseEntity.ok(mapToVendorResponse(vendor));
    }

    // ---------------- GET ALL REVIEWS ----------------
    @GetMapping("/{vendorId}/reviews")
    public ResponseEntity<List<VendorReviewDTO>> getReviews(@PathVariable Long vendorId) {
        List<VendorReviewDTO> reviews = vendorReviewService.getReviews(vendorId)
                .stream()
                .map(this::mapToReviewDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviews);
    }

    // ---------------- GET AVERAGE RATING ----------------
    @GetMapping("/{vendorId}/rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long vendorId) {
        return ResponseEntity.ok(vendorReviewService.getAverageRating(vendorId));
    }

    // ---------------- GET VENDORS BY STATUS ----------------
    @GetMapping("/status/{status}")
    public ResponseEntity<List<VendorResponseDTO>> getVendorsByStatus(@PathVariable String status) {
        Status st = Status.valueOf(status.toUpperCase());
        List<Vendor> vendors = userService.getVendorsByStatus(st);
        return ResponseEntity.ok(
                vendors.stream()
                        .map(this::mapToVendorResponse)
                        .collect(Collectors.toList())
        );
    }

    // ---------------- SEARCH VENDORS BY NAME ----------------
    @GetMapping("/search")
    public ResponseEntity<List<VendorResponseDTO>> searchVendors(@RequestParam String name) {
        return ResponseEntity.ok(
                vendorRepository.searchByBusinessName(name)
                        .stream()
                        .map(this::mapToVendorResponse)
                        .collect(Collectors.toList())
        );
    }

    // ---------------- GET VENDORS BY LOCATION ----------------
    @GetMapping("/location")
    public ResponseEntity<List<VendorResponseDTO>> getVendorsByLocation(@RequestParam String location) {
        return ResponseEntity.ok(
                vendorRepository.findApprovedByLocation(location)
                        .stream()
                        .map(this::mapToVendorResponse)
                        .collect(Collectors.toList())
        );
    }

    // ---------------- MAPPING METHODS ----------------
    private VendorResponseDTO mapToVendorResponse(Vendor vendor) {
        VendorResponseDTO dto = new VendorResponseDTO();

        dto.setId(vendor.getId());
        dto.setBusinessName(vendor.getBusinessName());
        dto.setCategory(vendor.getCategory());
        dto.setLocation(vendor.getLocation());
        dto.setProfileImage(vendor.getProfileImage());
        dto.setCoverImage(vendor.getCoverImage());
        dto.setStatus(vendor.getStatus().name());

        // ✅ Map user-related fields
        if (vendor.getUser() != null) {
            dto.setName(vendor.getUser().getName());
            dto.setEmail(vendor.getUser().getEmail());
            dto.setPhone(vendor.getUser().getPhone());
            // Removed old KYC fields
        }

        // ✅ Map reviews if any
        if (vendor.getReviews() != null) {
            dto.setReviews(vendor.getReviews()
                    .stream()
                    .map(this::mapToReviewDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private VendorReviewDTO mapToReviewDTO(VendorReview review) {
        return new VendorReviewDTO(
                review.getId(),
                review.getRating(),
                review.getReview(),
                review.getUserId(),
                review.getCreatedAt()
        );
    }
}
