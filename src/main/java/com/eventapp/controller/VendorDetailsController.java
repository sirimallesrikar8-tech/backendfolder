package com.eventapp.controller;

import com.eventapp.dto.VendorDetailsDTO;
import com.eventapp.entity.User;
import com.eventapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/vendor/details")
public class VendorDetailsController {

    @Autowired
    private UserRepository userRepository;

    // ---------------- GET Vendor Details ----------------
    @GetMapping("/{userId}")
    public ResponseEntity<?> getVendorDetails(@PathVariable Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Vendor not found with ID: " + userId);
        }

        User user = userOpt.get();
        VendorDetailsDTO dto = new VendorDetailsDTO();
        dto.setGstNumber(user.getGstNumber());
        dto.setPanOrTan(user.getPanOrTan());
        dto.setAadharNumber(user.getAadharNumber());

        return ResponseEntity.ok(dto);
    }

    // ---------------- POST Vendor Details ----------------
    @PostMapping("/{userId}")
    public ResponseEntity<?> addVendorDetails(
            @PathVariable Long userId,
            @RequestBody VendorDetailsDTO detailsDTO
    ) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Vendor not found with ID: " + userId);
        }

        User user = userOpt.get();
        user.setGstNumber(detailsDTO.getGstNumber());
        user.setPanOrTan(detailsDTO.getPanOrTan());
        user.setAadharNumber(detailsDTO.getAadharNumber());

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Vendor details added successfully");
    }

    // ---------------- PUT / EDIT Vendor Details ----------------
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateVendorDetails(
            @PathVariable Long userId,
            @RequestBody VendorDetailsDTO detailsDTO
    ) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Vendor not found with ID: " + userId);
        }

        User user = userOpt.get();
        user.setGstNumber(detailsDTO.getGstNumber());
        user.setPanOrTan(detailsDTO.getPanOrTan());
        user.setAadharNumber(detailsDTO.getAadharNumber());

        userRepository.save(user);
        return ResponseEntity.ok("Vendor details updated successfully");
    }

    // ---------------- DELETE Vendor Details ----------------
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteVendorDetails(@PathVariable Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Vendor not found with ID: " + userId);
        }

        User user = userOpt.get();
        user.setGstNumber(null);
        user.setPanOrTan(null);
        user.setAadharNumber(null);

        userRepository.save(user);
        return ResponseEntity.ok("Vendor details deleted successfully");
    }
}
