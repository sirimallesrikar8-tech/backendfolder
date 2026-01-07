package com.eventapp.service;

import com.eventapp.entity.Vendor;
import com.eventapp.entity.VendorMedia;
import com.eventapp.repository.VendorMediaRepository;
import com.eventapp.repository.VendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    // ✅ MUST match WebConfig: file:uploads/
    private static final String UPLOAD_BASE_DIR = "uploads";
    private static final String VENDOR_MEDIA_DIR = "vendor-media";

    public VendorMediaService(
            VendorMediaRepository mediaRepository,
            VendorRepository vendorRepository
    ) {
        this.mediaRepository = mediaRepository;
        this.vendorRepository = vendorRepository;
    }

    // ================= UPLOAD IMAGE =================
    public VendorMedia uploadMedia(Long vendorId, MultipartFile file, String caption) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        try {
            // uploads/vendor-media
            Path uploadPath = Paths.get(UPLOAD_BASE_DIR, VENDOR_MEDIA_DIR);
            Files.createDirectories(uploadPath);

            String safeFileName =
                    UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path filePath = uploadPath.resolve(safeFileName);

            // Save file
            file.transferTo(filePath.toFile());

            VendorMedia media = new VendorMedia();
            media.setVendor(vendor);
            media.setCaption(caption);

            // ✅ PUBLIC URL (served by WebConfig)
            media.setImageUrl("/uploads/vendor-media/" + safeFileName);

            return mediaRepository.save(media);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload vendor media", e);
        }
    }

    // ================= GET VENDOR GALLERY =================
    public List<VendorMedia> getVendorMedia(Long vendorId) {
        return mediaRepository.findByVendor_IdOrderByCreatedAtDesc(vendorId);
    }

    // ================= DELETE MEDIA =================
    public void deleteMedia(Long mediaId) {

        VendorMedia media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        try {
            // Convert public URL → filesystem path
            String relativePath = media.getImageUrl().replace("/uploads/", "");
            Path filePath = Paths.get(UPLOAD_BASE_DIR, relativePath);
            Files.deleteIfExists(filePath);
        } catch (IOException ignored) {}

        mediaRepository.delete(media);
    }
}
