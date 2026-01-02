package com.eventapp.controller;

import com.eventapp.entity.VendorSlot;
import com.eventapp.service.VendorSlotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/vendor-slots")
public class VendorSlotController {

    private final VendorSlotService slotService;

    public VendorSlotController(VendorSlotService slotService) {
        this.slotService = slotService;
    }

    // ================= CREATE SLOT =================

    @Operation(
            summary = "Create vendor slot",
            description = "Vendor creates a time slot for a specific date"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Slot created successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VendorSlot.class)
            )
    )
    @PostMapping("/create")
    public ResponseEntity<VendorSlot> createSlot(
            @RequestParam Long vendorId,
            @RequestParam LocalDate date,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime
    ) {
        VendorSlot slot = slotService.createSlot(vendorId, date, startTime, endTime);
        return ResponseEntity.ok(slot);
    }

    // ================= GET AVAILABLE SLOTS =================

    @Operation(
            summary = "Get available vendor slots",
            description = "Fetch all available slots for a vendor on a selected date"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Available slots fetched successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VendorSlot.class)
            )
    )
    @GetMapping("/available")
    public ResponseEntity<List<VendorSlot>> getAvailableSlots(
            @RequestParam Long vendorId,
            @RequestParam LocalDate date
    ) {
        List<VendorSlot> slots = slotService.getAvailableSlots(vendorId, date);
        return ResponseEntity.ok(slots);
    }
}
