package com.eventapp.dto;

public class VendorDetailsDTO {

    private String gstNumber;
    private String panOrTan;
    private String aadharNumber; // optional

    public VendorDetailsDTO() {
    }

    // ---------- Getters & Setters ----------
    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getPanOrTan() {
        return panOrTan;
    }

    public void setPanOrTan(String panOrTan) {
        this.panOrTan = panOrTan;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }
}
