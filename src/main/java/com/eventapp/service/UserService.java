package com.eventapp.service;

import com.eventapp.dto.LoginRequest;
import com.eventapp.dto.LoginResponse;
import com.eventapp.dto.RegisterRequest;
import com.eventapp.entity.*;
import com.eventapp.repository.AdminRepository;
import com.eventapp.repository.UserRepository;
import com.eventapp.repository.VendorRepository;
import com.eventapp.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;   // ✅ correct usage

    // ---------------- REGISTER ----------------
    public LoginResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);

        if (request.getRole() == Role.VENDOR) {

            if (request.getBusinessName() == null || request.getBusinessName().isEmpty()
                    || request.getCategory() == null || request.getCategory().isEmpty()
                    || request.getLocation() == null || request.getLocation().isEmpty()) {
                throw new RuntimeException("Vendor details are required");
            }

            Vendor vendor = new Vendor();
            vendor.setUser(user);
            vendor.setBusinessName(request.getBusinessName());
            vendor.setCategory(request.getCategory());
            vendor.setLocation(request.getLocation());
            vendor.setPhone(request.getPhone());
            vendor.setStatus(Status.PENDING);

            vendorRepository.save(vendor);

        } else if (request.getRole() == Role.ADMIN) {

            Admin admin = new Admin();
            admin.setUser(user);
            adminRepository.save(admin);
        }

        // ✅ Generate JWT
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().toString()
        );

        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().toString(),
                token
        );
    }

    // ---------------- LOGIN ----------------
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())
        ) {
            throw new RuntimeException("Invalid password");
        }

        if (user.getRole() == Role.VENDOR) {

            Vendor vendor = vendorRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Vendor profile not found"));

            if (vendor.getStatus() == Status.PENDING) {
                throw new RuntimeException("Vendor approval pending");
            }
        }

        // ✅ Generate JWT
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().toString()
        );

        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().toString(),
                token
        );
    }

    // ---------------- VENDOR MANAGEMENT ----------------
    public Vendor approveVendor(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        vendor.setStatus(Status.APPROVED);
        return vendorRepository.save(vendor);
    }

    public Vendor rejectVendor(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        vendor.setStatus(Status.REJECTED);
        return vendorRepository.save(vendor);
    }

    public Vendor updateVendorStatus(Long vendorId, Status status) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        vendor.setStatus(status);
        return vendorRepository.save(vendor);
    }

    public List<Vendor> getVendorsByStatus(Status status) {
        return vendorRepository.findAll()
                .stream()
                .filter(v -> v.getStatus() == status)
                .toList();
    }
}
