package com.kma.fitnesssmc.ui.main.profile;

import com.kma.fitnesssmc.data.model.Member;
import com.kma.fitnesssmc.data.repository.MemberRepository;
import com.kma.fitnesssmc.util.Bytes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.smartcardio.CardException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class ProfileViewModel {
    private final MemberRepository memberRepository;

    private byte[] avatar;

    public ProfileViewModel(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public @Nullable Member getMember() {
        return memberRepository.getMember();
    }

    public @Nullable String updateMemberProfile(String fullName, Date dateOfBirth, String phoneNumber) {
        String errorMessage = validate(fullName, dateOfBirth, phoneNumber);

        if (errorMessage != null) {
            return errorMessage;
        }

        try {
            return memberRepository.updateProfile(fullName, dateOfBirth, phoneNumber, avatar) ? null : "Member profile update failed!";
        } catch (CardException e) {
            e.printStackTrace();
            return "Error! An error occurred. Please try again later.";
        }
    }

    private @Nullable String validate(
        @Nullable String fullName,
        @Nullable Date dateOfBirth,
        @Nullable String phoneNumber
    ) {
        if (fullName == null || fullName.isBlank()) {
            return "Enter your full name.";
        }

        if (dateOfBirth == null) {
            return "Choose your date of birth.";
        }

        Date now = Calendar.getInstance().getTime();
        if (dateOfBirth.after(now)) {
            return "Date of birth is invalid!";
        }

        if (phoneNumber == null || phoneNumber.isBlank()) {
            return "Enter your phone number.";
        }

        if (phoneNumber.length() != 10) {
            return "Phone number is invalid!";
        }

        return null;
    }

    public @Nullable Image setAvatar(byte[] data) {
        this.avatar = data;
        return new ImageIcon(avatar).getImage();
    }

    public @Nullable Image setAvatar(@NotNull File fileAvatar) {
        this.avatar = Bytes.fromFile(fileAvatar);
        return new ImageIcon(avatar).getImage();
    }
}
