package com.eventapp.controller;

import com.eventapp.dto.ReviewRequestDTO;
import com.eventapp.dto.VendorResponseDTO;
import com.eventapp.dto.VendorReviewDTO;
import com.eventapp.entity.Vendor;
import com.eventapp.entity.VendorReview;
import com.eventapp.repository.UserRepository;
import com.eventapp.repository.VendorRepository;
import com.eventapp.service.VendorReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private VendorReviewService vendorReviewService;

    @Autowired
    private UserRepository userRepository;

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

            return ResponseEntity.ok(mapToReviewDTO(savedReview));

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong");
        }
    }

    // ---------------- GET SINGLE VENDOR ----------------
    @GetMapping("/{vendorId}")
    @Transactional
    public ResponseEntity<VendorResponseDTO> getVendorById(@PathVariable Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        return ResponseEntity.ok(mapToVendorResponse(vendor));
    }

    // ---------------- GET ALL REVIEWS ----------------
    @GetMapping("/{vendorId}/reviews")
    public ResponseEntity<List<VendorReviewDTO>> getReviews(@PathVariable Long vendorId) {
        return ResponseEntity.ok(
                vendorReviewService.getReviews(vendorId)
                        .stream()
                        .map(this::mapToReviewDTO)
                        .collect(Collectors.toList())
        );
    }

    // ---------------- GET AVERAGE RATING ----------------
    @GetMapping("/{vendorId}/rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long vendorId) {
        return ResponseEntity.ok(vendorReviewService.getAverageRating(vendorId));
    }

    // ---------------- MAPPING METHODS ----------------
    private VendorResponseDTO mapToVendorResponse(Vendor vendor) {
        VendorResponseDTO dto = new VendorResponseDTO();

        dto.setId(vendor.getId());
        dto.setBusinessName(
                vendor.getBusinessName() != null ? vendor.getBusinessName() : ""
        );
        dto.setCategory(
                vendor.getCategory() != null ? vendor.getCategory() : ""
        );
        dto.setLocation(
                vendor.getLocation() != null ? vendor.getLocation() : ""
        );
        dto.setStatus(
                vendor.getStatus() != null ? vendor.getStatus().name() : ""
        );

        if (vendor.getUser() != null) {
            dto.setName(
                    vendor.getUser().getName() != null ? vendor.getUser().getName() : ""
            );
            dto.setEmail(
                    vendor.getUser().getEmail() != null ? vendor.getUser().getEmail() : ""
            );
            dto.setPhone(
                    vendor.getUser().getPhone() != null ? vendor.getUser().getPhone() : ""
            );
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
