package com.kma.fitnesssmc.data.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Member {
    private String ID;

    private String fullName;

    private Date dateOfBirth;

    private String phoneNumber;

    private byte[] avatar;

    private Date expirationDate;

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOfBirthFormatted = dateFormat.format(dateOfBirth);
        String expirationDateFormatted = dateFormat.format(expirationDate);

        return "{ID: \"" + ID + "\", fullName: \"" + fullName + "\", dateOfBirth: " + dateOfBirthFormatted + ", phoneNumber: \"" + phoneNumber + "\", expirationDate: " + expirationDateFormatted + "}";
    }

    public byte[] getProfileData() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOfBirthFormatted = dateFormat.format(dateOfBirth);
        String data = (char) fullName.length() + fullName
            + (char) dateOfBirthFormatted.length() + dateOfBirthFormatted
            + (char) phoneNumber.length() + phoneNumber;

        return data.getBytes();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
