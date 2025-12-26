package com.eventapp.controller;

import com.eventapp.entity.Status;
import com.eventapp.entity.Vendor;
import com.eventapp.repository.VendorRepository;
import com.eventapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private UserService userService;

    // ---------------- GET VENDORS BY STATUS ----------------
    @GetMapping("/status/{status}")
    public ResponseEntity<List<VendorDTO>> getVendorsByStatus(@PathVariable("status") String statusStr) {
        Status status = Status.valueOf(statusStr.toUpperCase());
        List<Vendor> vendors = userService.getVendorsByStatus(status);

        List<VendorDTO> result = vendors.stream()
                .map(VendorDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // ---------------- SEARCH VENDORS BY BUSINESS NAME ----------------
    @GetMapping("/search")
    public ResponseEntity<List<VendorDTO>> searchVendors(@RequestParam("name") String name) {
        List<Vendor> vendors = vendorRepository.searchByBusinessName(name);
        List<VendorDTO> result = vendors.stream()
                .map(VendorDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // ---------------- GET APPROVED VENDORS BY LOCATION ----------------
    @GetMapping("/location")
    public ResponseEntity<List<VendorDTO>> getVendorsByLocation(@RequestParam("location") String location) {
        List<Vendor> vendors = vendorRepository.findApprovedByLocation(location);

        List<VendorDTO> result = vendors.stream()
                .map(VendorDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // ---------------- VENDOR DTO ----------------
    public static class VendorDTO {
        private Long id;
        private String businessName;
        private String category;
        private String location;
        private String phone;
        private String userName;
        private String userEmail;
        private String userRole;

        public VendorDTO(Vendor vendor) {
            this.id = vendor.getId();
            this.businessName = vendor.getBusinessName();
            this.category = vendor.getCategory();
            this.location = vendor.getLocation();
            this.phone = vendor.getPhone();
            this.userName = vendor.getUser().getName();
            this.userEmail = vendor.getUser().getEmail();
            this.userRole = vendor.getUser().getRole().toString();
            // Password is intentionally excluded
        }

        // ---------- Getters ----------
        public Long getId() { return id; }
        public String getBusinessName() { return businessName; }
        public String getCategory() { return category; }
        public String getLocation() { return location; }
        public String getPhone() { return phone; }
        public String getUserName() { return userName; }
        public String getUserEmail() { return userEmail; }
        public String getUserRole() { return userRole; }
    }
}
