package com.eventapp.controller;

import com.eventapp.dto.VendorResponseDTO;
import com.eventapp.entity.Status;
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

    // ---------------- SEARCH VENDORS BY BUSINESS NAME ----------------
    @GetMapping("/name")
    public ResponseEntity<List<VendorResponseDTO>> searchByName(@RequestParam String name) {
        List<VendorResponseDTO> response;
        try {
            List<Vendor> vendors = vendorRepository.searchApprovedVendorsByName(name, Status.APPROVED);
            response = vendors.stream().map(this::mapToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            // Log the exception if needed
            e.printStackTrace();
            response = List.of(); // return empty list instead of 500
        }
        return ResponseEntity.ok(response);
    }

    // ---------------- SEARCH VENDORS BY LOCATION ----------------
    @GetMapping("/location")
    public ResponseEntity<List<VendorResponseDTO>> searchByLocation(@RequestParam String location) {
        List<VendorResponseDTO> response;
        try {
            List<Vendor> vendors = vendorRepository.findApprovedVendorsByLocation(location, Status.APPROVED);
            response = vendors.stream().map(this::mapToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            // Log the exception if needed
            e.printStackTrace();
            response = List.of(); // return empty list instead of 500
        }
        return ResponseEntity.ok(response);
    }

    // ---------------- MAPPING METHOD ----------------
    private VendorResponseDTO mapToDTO(Vendor vendor) {
        VendorResponseDTO dto = new VendorResponseDTO();
        dto.setId(vendor.getId());
        dto.setBusinessName(vendor.getBusinessName() != null ? vendor.getBusinessName() : "");
        dto.setCategory(vendor.getCategory() != null ? vendor.getCategory() : "");
        dto.setLocation(vendor.getLocation() != null ? vendor.getLocation() : "");
        dto.setStatus(vendor.getStatus() != null ? vendor.getStatus().name() : "");

        if (vendor.getUser() != null) {
            dto.setName(vendor.getUser().getName() != null ? vendor.getUser().getName() : "");
            dto.setEmail(vendor.getUser().getEmail() != null ? vendor.getUser().getEmail() : "");
            dto.setPhone(vendor.getUser().getPhone() != null ? vendor.getUser().getPhone() : "");
        }

        return dto;
    }
}
