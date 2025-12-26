package com.eventapp.controller;

import com.eventapp.entity.Status;
import com.eventapp.entity.Vendor;
import com.eventapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/vendor")
public class AdminController {

    @Autowired
    private UserService userService;

    // ---------------- APPROVE VENDOR ----------------
    @PostMapping("/{vendorId}/approve")
    public Vendor approveVendor(@PathVariable Long vendorId) {
        return userService.approveVendor(vendorId);
    }

    // ---------------- REJECT VENDOR ----------------
    @PostMapping("/{vendorId}/reject")
    public Vendor rejectVendor(@PathVariable Long vendorId) {
        return userService.rejectVendor(vendorId);
    }

    // ---------------- UPDATE VENDOR STATUS ----------------
    @PatchMapping("/{vendorId}/status")
    public Vendor updateVendorStatus(@PathVariable Long vendorId, @RequestParam Status status) {
        return userService.updateVendorStatus(vendorId, status);
    }

    // ---------------- LIST VENDORS BY STATUS ----------------
    @GetMapping("/status/{status}")
    public List<Vendor> getVendorsByStatus(@PathVariable Status status) {
        return userService.getVendorsByStatus(status);
    }
}
