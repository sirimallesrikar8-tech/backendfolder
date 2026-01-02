package com.eventapp.service;

import com.eventapp.entity.Vendor;
import com.eventapp.entity.VendorMedia;
import com.eventapp.repository.VendorMediaRepository;
import com.eventapp.repository.VendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class VendorMediaService {

    private final VendorMediaRepository mediaRepository;
    private final VendorRepository vendorRepository;

    // ✅ Absolute safe upload directory
    private static final String UPLOAD_DIR =
            System.getProperty("user.dir") + "/uploads/vendor-media/";

    public VendorMediaService(
            VendorMediaRepository mediaRepository,
            VendorRepository vendorRepository
    ) {
        this.mediaRepository = mediaRepository;
        this.vendorRepository = vendorRepository;
    }

    // ---------------- UPLOAD IMAGE ----------------
    public VendorMedia uploadMedia(Long vendorId, MultipartFile file, String caption) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        try {
            // ✅ Ensure directory exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            Files.createDirectories(uploadPath);

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // ✅ Save file
            file.transferTo(filePath.toFile());

            VendorMedia media = new VendorMedia();
            media.setVendor(vendor);
            media.setImageUrl("/uploads/vendor-media/" + fileName);
            media.setCaption(caption);

            return mediaRepository.save(media);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload media", e);
        }
    }

    // ---------------- GET VENDOR GALLERY ----------------
    public List<VendorMedia> getVendorMedia(Long vendorId) {
        return mediaRepository.findByVendor_IdOrderByCreatedAtDesc(vendorId);
    }

    // ---------------- DELETE MEDIA ----------------
    public void deleteMedia(Long mediaId) {

        VendorMedia media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        try {
            String filePath = System.getProperty("user.dir") + media.getImageUrl();
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException ignored) {}

        mediaRepository.delete(media);
    }
}
