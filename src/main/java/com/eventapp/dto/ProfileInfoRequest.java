package com.eventapp.dto;

public class ProfileInfoRequest {

    private String gstNumber;
    private String panOrTan;
    private String aadharNumber;
    private String profilePicture;

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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
