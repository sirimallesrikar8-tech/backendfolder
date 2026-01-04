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
    private UserRepository userRepository; // ✅ Added to check user existence

    // ---------------- POST A REVIEW FOR A VENDOR ----------------
    @PostMapping("/{vendorId}/reviews")
    public ResponseEntity<?> addReview(
            @PathVariable Long vendorId,
            @RequestBody ReviewRequestDTO reviewRequest
    ) {
        try {
            // ✅ Validate that the user exists using UserRepository
            if (!userRepository.existsById(reviewRequest.getUserId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User not found with ID: " + reviewRequest.getUserId());
            }

            // Call the service to save the review
            VendorReview savedReview = vendorReviewService.addReview(vendorId, reviewRequest);

            // Map to DTO and return
            return ResponseEntity.ok(new VendorReviewDTO(
                    savedReview.getId(),
                    savedReview.getRating(),
                    savedReview.getReview(),
                    savedReview.getUserId(),
                    savedReview.getCreatedAt()
            ));

        } catch (IllegalArgumentException ex) {
            // For invalid rating
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (RuntimeException ex) {
            // For vendor not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            // Any unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong: " + ex.getMessage());
        }
    }

    // ------------------ REST OF YOUR CONTROLLER STAYS THE SAME ------------------
    @GetMapping("/{vendorId}")
    public ResponseEntity<VendorResponseDTO> getVendorById(@PathVariable Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        return ResponseEntity.ok(mapToVendorResponse(vendor));
    }

    @GetMapping("/{vendorId}/reviews")
    public ResponseEntity<List<VendorReviewDTO>> getReviews(@PathVariable Long vendorId) {
        List<VendorReviewDTO> reviews = vendorReviewService.getReviews(vendorId)
                .stream()
                .map(this::mapToReviewDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{vendorId}/rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long vendorId) {
        return ResponseEntity.ok(vendorReviewService.getAverageRating(vendorId));
    }

    // ---------------- MAPPING METHODS ----------------
    private VendorResponseDTO mapToVendorResponse(Vendor vendor) {
        VendorResponseDTO dto = new VendorResponseDTO();
        dto.setId(vendor.getId());
        dto.setBusinessName(vendor.getBusinessName());
        dto.setCategory(vendor.getCategory());
        dto.setPhone(vendor.getPhone());
        dto.setLocation(vendor.getLocation());
        dto.setProfileImage(vendor.getProfileImage());
        dto.setCoverImage(vendor.getCoverImage());
        dto.setStatus(vendor.getStatus().name());

        if (vendor.getReviews() != null) {
            dto.setReviews(vendor.getReviews()
                    .stream().map(this::mapToReviewDTO).collect(Collectors.toList()));
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
