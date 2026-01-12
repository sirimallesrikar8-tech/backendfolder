package com.eventapp.controller;

import com.eventapp.dto.ProfileInfoRequest;
import com.eventapp.entity.ProfileInfo;
import com.eventapp.service.ProfileInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor/profile")
public class ProfileInfoController {

    private final ProfileInfoService profileInfoService;

    public ProfileInfoController(ProfileInfoService profileInfoService) {
        this.profileInfoService = profileInfoService;
    }

    // CREATE / UPDATE
    @PostMapping("/{userId}")
    public ResponseEntity<ProfileInfo> createOrUpdateProfile(
            @PathVariable Long userId,
            @RequestBody ProfileInfoRequest request
    ) {
        return ResponseEntity.ok(
                profileInfoService.saveProfileInfo(userId, request)
        );
    }

    // GET
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileInfo> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(
                profileInfoService.getProfileInfo(userId)
        );
    }

    // DELETE
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long userId) {
        profileInfoService.deleteProfileInfo(userId);
        return ResponseEntity.noContent().build();
    }
}
