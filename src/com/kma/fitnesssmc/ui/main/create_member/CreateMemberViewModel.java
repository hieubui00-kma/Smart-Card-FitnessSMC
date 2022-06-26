package com.kma.fitnesssmc.ui.main.create_member;

import com.kma.fitnesssmc.data.repository.MemberRepository;
import com.kma.fitnesssmc.util.Bytes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.smartcardio.CardException;
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

    public @Nullable String createMember(String fullName, Date dateOfBirth, String phoneNumber) {
        String errorMessage = validate(fullName, dateOfBirth, phoneNumber);

        if (errorMessage != null) {
            return errorMessage;
        }

        try {
            if (memberRepository.createMember(fullName, dateOfBirth, phoneNumber)) {
                byte[] avatar = fileAvatar != null ? Bytes.fromFile(fileAvatar) : null;

                memberRepository.updateAvatar(avatar);
                return null;
            }
            return "New member creation failed!";
        } catch (CardException e) {
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
}
