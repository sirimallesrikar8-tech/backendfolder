package com.eventapp.controller;

import com.eventapp.entity.VendorMedia;
import com.eventapp.service.VendorMediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-media")
public class VendorMediaController {

    private final VendorMediaService mediaService;

    public VendorMediaController(VendorMediaService mediaService) {
        this.mediaService = mediaService;
    }

    // ================= UPLOAD IMAGE =================

    @Operation(
            summary = "Upload vendor media",
            description = "Upload an image for a vendor with optional caption"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Image uploaded successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VendorMedia.class)
            )
    )
    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<VendorMedia> uploadMedia(
            @Parameter(description = "Vendor ID", required = true)
            @RequestParam Long vendorId,

            @Parameter(description = "Image file", required = true)
            @RequestPart("file") MultipartFile file,

            @Parameter(description = "Optional image caption")
            @RequestParam(required = false) String caption
    ) {
        VendorMedia media = mediaService.uploadMedia(vendorId, file, caption);
        return ResponseEntity.ok(media);
    }

    // ================= GET VENDOR GALLERY =================

    @Operation(
            summary = "Get vendor media",
            description = "Fetch all images uploaded by a vendor"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Vendor media fetched successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VendorMedia.class)
            )
    )
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<VendorMedia>> getVendorMedia(
            @PathVariable Long vendorId
    ) {
        return ResponseEntity.ok(mediaService.getVendorMedia(vendorId));
    }

    // ================= DELETE IMAGE =================

    @Operation(
            summary = "Delete vendor media",
            description = "Delete an uploaded vendor image"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Image deleted successfully"
    )
    @DeleteMapping("/{mediaId}")
    public ResponseEntity<String> deleteMedia(@PathVariable Long mediaId) {
        mediaService.deleteMedia(mediaId);
        return ResponseEntity.ok("Image deleted successfully");
    }
}
