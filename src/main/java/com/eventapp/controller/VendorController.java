package com.eventapp.controller;

import com.eventapp.dto.VendorResponseDTO;
import com.eventapp.dto.VendorReviewDTO;
import com.eventapp.entity.Status;
import com.eventapp.entity.Vendor;
import com.eventapp.entity.VendorReview;
import com.eventapp.repository.VendorRepository;
import com.eventapp.service.UserService;
import com.eventapp.service.VendorReviewService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // ---------------------------------------------------
    // ✅ GET SINGLE VENDOR PROFILE
    // ---------------------------------------------------
    @GetMapping("/{vendorId}")
    public ResponseEntity<VendorResponseDTO> getVendorById(@PathVariable Long vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        return ResponseEntity.ok(mapToVendorResponse(vendor));
    }

    // ---------------------------------------------------
    // ⭐ GET ALL REVIEWS OF A VENDOR
    // ---------------------------------------------------
    @GetMapping("/{vendorId}/reviews")
    public ResponseEntity<List<VendorReviewDTO>> getReviews(
            @PathVariable Long vendorId
    ) {

        List<VendorReviewDTO> reviews =
                vendorReviewService.getReviews(vendorId)
                        .stream()
                        .map(this::mapToReviewDTO)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(reviews);
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
    // EXISTING APIs
    // ---------------------------------------------------
    @GetMapping("/status/{status}")
    public ResponseEntity<List<VendorResponseDTO>> getVendorsByStatus(
            @PathVariable String status
    ) {

        Status st = Status.valueOf(status.toUpperCase());
        List<Vendor> vendors = userService.getVendorsByStatus(st);

        return ResponseEntity.ok(
                vendors.stream()
                        .map(this::mapToVendorResponse)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<VendorResponseDTO>> searchVendors(
            @RequestParam String name
    ) {

        return ResponseEntity.ok(
                vendorRepository.searchByBusinessName(name)
                        .stream()
                        .map(this::mapToVendorResponse)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/location")
    public ResponseEntity<List<VendorResponseDTO>> getVendorsByLocation(
            @RequestParam String location
    ) {

        return ResponseEntity.ok(
                vendorRepository.findApprovedByLocation(location)
                        .stream()
                        .map(this::mapToVendorResponse)
                        .collect(Collectors.toList())
        );
    }

    // ---------------------------------------------------
    // 🔁 MAPPING METHODS (VERY IMPORTANT)
    // ---------------------------------------------------

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
            dto.setReviews(
                    vendor.getReviews()
                            .stream()
                            .map(this::mapToReviewDTO)
                            .collect(Collectors.toList())
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
