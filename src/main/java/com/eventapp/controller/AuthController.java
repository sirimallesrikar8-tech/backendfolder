package com.eventapp.controller;

import com.eventapp.dto.LoginRequest;
import com.eventapp.dto.LoginResponse;
import com.eventapp.dto.RegisterRequest;
import com.eventapp.dto.VendorResponseDTO;
import com.eventapp.entity.User;
import com.eventapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ---------------- LOGIN ----------------
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    // ---------------- REGISTER ----------------
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    // ---------------- UPLOAD PROFILE PICTURE ----------------
    @PostMapping(
            value = "/upload-profile-picture/{userId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(
            summary = "Upload profile picture",
            description = "Upload profile image for a user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile picture uploaded successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid file"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    public ResponseEntity<VendorResponseDTO> uploadProfilePicture(
            @PathVariable Long userId,
            @Parameter(description = "Profile picture file", required = true)
            @RequestPart("file") MultipartFile file
    ) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Save profile picture
        userService.saveProfilePicture(userId, file);

        // Get updated profile
        VendorResponseDTO profile = userService.getUserProfile(userId);

        return ResponseEntity.ok(profile);
    }

    // ---------------- GET USER PROFILE ----------------
    @GetMapping("/profile/{userId}")
    public ResponseEntity<VendorResponseDTO> getUserProfile(@PathVariable Long userId) {
        VendorResponseDTO profile = userService.getUserProfile(userId);
        return ResponseEntity.ok(profile);
    }
}
