package com.eventapp.service;

import com.eventapp.entity.Vendor;
import com.eventapp.entity.VendorMedia;
import com.eventapp.repository.VendorMediaRepository;
import com.eventapp.repository.VendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class VendorMediaService {

    private final VendorMediaRepository mediaRepository;
    private final VendorRepository vendorRepository;
    private final CloudinaryService cloudinaryService;

    public VendorMediaService(
            VendorMediaRepository mediaRepository,
            VendorRepository vendorRepository,
            CloudinaryService cloudinaryService
    ) {
        this.mediaRepository = mediaRepository;
        this.vendorRepository = vendorRepository;
        this.cloudinaryService = cloudinaryService;
    }

    // ================= UPLOAD IMAGE =================
    public VendorMedia uploadMedia(Long vendorId, MultipartFile file, String caption) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        try {
            // Upload image to Cloudinary
            String imageUrl = cloudinaryService.uploadFile(file);

            // Save media in DB
            VendorMedia media = new VendorMedia();
            media.setVendor(vendor);
            media.setCaption(caption);
            media.setImageUrl(imageUrl);

            return mediaRepository.save(media);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload vendor media to Cloudinary", e);
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

        // Optional: delete from Cloudinary (not required for free tier)
        // You can extend CloudinaryService to delete if needed

        // Delete record from DB
        mediaRepository.delete(media);
    }
}
