package com.eventapp.controller;

import com.eventapp.entity.SlotStatus;
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

    // ================= DELETE VENDOR SLOT =================
    @Operation(
            summary = "Delete a vendor slot",
            description = "Delete a vendor's slot by slotId"
    )
    @DeleteMapping("/delete/{slotId}")
    public ResponseEntity<?> deleteVendorSlot(@PathVariable Long slotId) {
        try {
            slotService.deleteVendorSlot(slotId);
            return ResponseEntity.ok().body("Slot deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= EDIT/UPDATE VENDOR SLOT =================
    @Operation(
            summary = "Edit vendor slot",
            description = "Update a vendor's slot details by slotId"
    )
    @PutMapping("/edit/{slotId}")
    public ResponseEntity<?> updateVendorSlot(
            @PathVariable Long slotId,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime,
            @RequestParam(required = false) SlotStatus status
    ) {
        try {
            VendorSlot updatedSlot = slotService.updateVendorSlot(slotId, date, startTime, endTime, status);
            return ResponseEntity.ok(updatedSlot);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
