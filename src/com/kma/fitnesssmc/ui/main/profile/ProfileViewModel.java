package com.kma.fitnesssmc.ui.main.profile;

import com.kma.fitnesssmc.data.model.Member;
import com.kma.fitnesssmc.data.repository.MemberRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

public class ProfileViewModel {
    private final MemberRepository memberRepository;

    private File fileAvatar;

    public ProfileViewModel(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public @Nullable Member getMember() {
        return memberRepository.getMember();
    }

    public @Nullable Image getAvatar() {
        return null;
    }

    public @Nullable Image setAvatar(@NotNull File fileAvatar) {
        try {
            URL url = fileAvatar.toURI().toURL();

            this.fileAvatar = fileAvatar;
            return new ImageIcon(url).getImage();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public @Nullable String updateMemberProfile(String fullName, Date dateOfBirth, String phoneNumber) {
        String errorMessage = validate(fullName, dateOfBirth, phoneNumber);

        if (errorMessage != null) {
            return errorMessage;
        }

        return memberRepository.updateProfile(fullName, dateOfBirth, phoneNumber) ? null : "Member profile update failed!";
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
}
