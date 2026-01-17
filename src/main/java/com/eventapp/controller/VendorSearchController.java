package com.eventapp.controller;

import com.eventapp.dto.VendorResponseDTO;
import com.eventapp.entity.Vendor;
import com.eventapp.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vendors/search")
public class VendorSearchController {

    @Autowired
    private VendorRepository vendorRepository;

    // ================= SEARCH BY NAME =================
    @GetMapping("/name")
    public ResponseEntity<?> searchByName(@RequestParam String name) {

        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Search name cannot be empty");
        }

        List<Vendor> vendors =
                vendorRepository.searchApprovedVendorsByName(name.trim());

        if (vendors.isEmpty()) {
            return ResponseEntity.status(404)
                    .body("No vendors found with name: " + name);
        }

        List<VendorResponseDTO> response = vendors.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // ================= SEARCH BY LOCATION =================
    @GetMapping("/location")
    public ResponseEntity<?> searchByLocation(@RequestParam String location) {

        if (location == null || location.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Location cannot be empty");
        }

        List<Vendor> vendors =
                vendorRepository.searchApprovedVendorsByLocation(location.trim());

        if (vendors.isEmpty()) {
            return ResponseEntity.status(404)
                    .body("No vendors found in location: " + location);
        }

        List<VendorResponseDTO> response = vendors.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // ================= DTO MAPPER =================
    private VendorResponseDTO mapToDTO(Vendor vendor) {

        VendorResponseDTO dto = new VendorResponseDTO();

        dto.setId(vendor.getId());
        dto.setBusinessName(vendor.getBusinessName());
        dto.setCategory(vendor.getCategory());
        dto.setLocation(vendor.getLocation());
        dto.setStatus(vendor.getStatus().name());

        dto.setName(vendor.getUser().getName());
        dto.setEmail(vendor.getUser().getEmail());
        dto.setPhone(vendor.getUser().getPhone());

        return dto;
    }
}
