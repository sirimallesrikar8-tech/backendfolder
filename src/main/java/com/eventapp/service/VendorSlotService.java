package com.eventapp.service;

import com.eventapp.entity.SlotStatus;
import com.eventapp.entity.Vendor;
import com.eventapp.entity.VendorSlot;
import com.eventapp.repository.VendorRepository;
import com.eventapp.repository.VendorSlotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VendorSlotService {

    private final VendorSlotRepository slotRepository;
    private final VendorRepository vendorRepository;

    public VendorSlotService(
            VendorSlotRepository slotRepository,
            VendorRepository vendorRepository
    ) {
        this.slotRepository = slotRepository;
        this.vendorRepository = vendorRepository;
    }

    // ---------------- VENDOR CREATES AVAILABILITY SLOT ----------------
    public VendorSlot createSlot(
            Long vendorId,
            LocalDate date,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        VendorSlot slot = new VendorSlot();
        slot.setVendor(vendor);
        slot.setDate(date);
        slot.setStartTime(startTime);
        slot.setEndTime(endTime);
        slot.setStatus(SlotStatus.AVAILABLE);

        return slotRepository.save(slot);
    }

    // ---------------- GET AVAILABLE SLOTS FOR A VENDOR ON A DATE ----------------
    public List<VendorSlot> getAvailableSlots(Long vendorId, LocalDate date) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        return slotRepository.findByVendorAndDateAndStatus(
                vendor,
                date,
                SlotStatus.AVAILABLE
        );
    }
}
