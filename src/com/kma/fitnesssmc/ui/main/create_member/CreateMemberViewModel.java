package com.kma.fitnesssmc.ui.main.create_member;

import com.kma.fitnesssmc.data.model.Member;
import com.kma.fitnesssmc.data.repository.MemberRepository;
import com.kma.fitnesssmc.util.Bytes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

public class CreateMemberViewModel {
    private final MemberRepository memberRepository;

    private File fileAvatar;

    public CreateMemberViewModel(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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

    public @Nullable String validate(
        @Nullable String fullName,
        @Nullable Date dateOfBirth,
        @Nullable String phoneNumber,
        @Nullable String newPin,
        @Nullable String confirmNewPin
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

        if (newPin == null || newPin.isBlank()) {
            return "Enter your new PIN.";
        }

        if (newPin.length() != 6) {
            return "New PIN is invalid!";
        }

        if (confirmNewPin == null || confirmNewPin.isBlank()) {
            return "Enter your confirm new PIN.";
        }

        if (confirmNewPin.equals(newPin)) {
            return null;
        }

        return "Confirm new PIN is not match!";
    }

    public @Nullable Member createMember(
        @NotNull String fullName,
        @NotNull Date dateOfBirth,
        @NotNull String phoneNumber,
        @NotNull String newPin
    ) {
        byte[] avatar = fileAvatar != null ? Bytes.fromFile(fileAvatar) : null;

        return memberRepository.createMember(fullName, dateOfBirth, phoneNumber, avatar, newPin);
    }
}
