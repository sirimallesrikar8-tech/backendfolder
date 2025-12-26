package com.eventapp.service;

import com.eventapp.entity.Status;
import com.eventapp.entity.Vendor;
import com.eventapp.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    // Register a new vendor
    public Vendor registerVendor(Vendor vendor) {
        // Default status is PENDING
        vendor.setStatus(Status.PENDING);
        return vendorRepository.save(vendor);
    }

    // Find all approved vendors in a given location
    public List<Vendor> getVendorsByLocation(String location) {
        return vendorRepository.findByLocationAndStatus(location, Status.APPROVED);
    }

    // Simple login by businessName and phone
    public Optional<Vendor> loginVendor(String businessName, String phone) {
        return vendorRepository.findByBusinessNameAndPhoneAndStatus(businessName, phone, Status.APPROVED);
    }
}
