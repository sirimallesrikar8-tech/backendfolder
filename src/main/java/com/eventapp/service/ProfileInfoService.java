package com.eventapp.service;

import com.eventapp.dto.ProfileInfoRequest;
import com.eventapp.entity.ProfileInfo;
import com.eventapp.entity.User;
import com.eventapp.repository.ProfileInfoRepository;
import com.eventapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileInfoService {

    private final ProfileInfoRepository profileInfoRepository;
    private final UserRepository userRepository;

    public ProfileInfoService(ProfileInfoRepository profileInfoRepository,
                              UserRepository userRepository) {
        this.profileInfoRepository = profileInfoRepository;
        this.userRepository = userRepository;
    }

    // CREATE or UPDATE
    public ProfileInfo saveProfileInfo(Long userId, ProfileInfoRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProfileInfo profileInfo = profileInfoRepository
                .findByUser(user)
                .orElse(new ProfileInfo());

        profileInfo.setUser(user);
        profileInfo.setGstNumber(request.getGstNumber());
        profileInfo.setPanOrTan(request.getPanOrTan());
        profileInfo.setAadharNumber(request.getAadharNumber());

        return profileInfoRepository.save(profileInfo);
    }

    // GET
    public ProfileInfo getProfileInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return profileInfoRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile info not found"));
    }

    // DELETE
    public void deleteProfileInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        profileInfoRepository.deleteByUser(user);
    }
}
