package com.eventapp.service;

import com.eventapp.dto.LoginRequest;
import com.eventapp.dto.RegisterRequest; // <-- Changed here
import com.eventapp.entity.*;
import com.eventapp.repository.AdminRepository;
import com.eventapp.repository.UserRepository;
import com.eventapp.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ---------------- Signup ----------------
    public String signup(RegisterRequest request) { // <-- Changed here
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists!";
        }

        // Create User
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepository.save(user);

        // Create role-specific entity
        if (request.getRole() == Role.VENDOR) {
            Vendor vendor = new Vendor();
            vendor.setUser(user);
            vendor.setStatus(Status.PENDING); // admin approval needed
            vendorRepository.save(vendor);
        } else if (request.getRole() == Role.ADMIN) {
            Admin admin = new Admin();
            admin.setUser(user);
            adminRepository.save(admin);
        }

        return "Signup successful!";
    }

    // ---------------- Login ----------------
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return "Invalid password!";
        }

        // You can generate JWT token here in future
        return "Login successful! Role: " + user.getRole();
    }
}
