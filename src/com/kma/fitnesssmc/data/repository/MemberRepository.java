package com.kma.fitnesssmc.data.repository;

import com.kma.fitnesssmc.data.manager.SessionManager;
import com.kma.fitnesssmc.data.model.Member;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.smartcardio.CardException;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static com.kma.fitnesssmc.util.Constants.*;

public class MemberRepository {
    private final SessionManager sessionManager;

    public MemberRepository(@NotNull SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public boolean verify(@NotNull String pin) {
        try {
            CommandAPDU command = new CommandAPDU(0x00, INS_VERIFY_MEMBER, 0x00, 0x00, pin.getBytes());
            ResponseAPDU response = sessionManager.transmit(command);

            return response.getSW1() == 0x90 && response.getSW2() == 0x00;
        } catch (NullPointerException | CardException e) {
            e.printStackTrace();
            return false;
        }
    }

    public @Nullable Member createMember(
        @NotNull String fullName,
        @NotNull Date dateOfBirth,
        @NotNull String phoneNumber,
        byte[] avatar,
        @NotNull String newPin
    ) {
        try {
            Date now = Calendar.getInstance().getTime();
            Member member = new Member();

            member.setFullName(fullName);
            member.setDateOfBirth(dateOfBirth);
            member.setPhoneNumber(phoneNumber);
            member.setAvatar(avatar);
            member.setExpirationDate(now);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nowFormatted = dateFormat.format(now);
            String data = new String(member.getProfileData()) + (char) nowFormatted.length() + nowFormatted;
            CommandAPDU createCommand = new CommandAPDU(0x00, INS_CREATE_MEMBER, 0x00, 0x00, data.getBytes());
            ResponseAPDU createResponse = sessionManager.transmit(createCommand);

            return createResponse.getSW1() == 0x90 && createResponse.getSW2() == 0x00 ? member : null;
        } catch (NullPointerException | CardException e) {
            e.printStackTrace();
            return null;
        }
    }

    public @Nullable Member getMember() {
        try {
            CommandAPDU getCommand = new CommandAPDU(0x00, INS_GET_MEMBER, 0x00, 0x00);
            ResponseAPDU response = sessionManager.transmit(getCommand);

            return response.getSW1() == 0x90 && response.getSW2() == 0x00 ? parseMemberData(response.getData()) : null;
        } catch (CardException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private @Nullable Member parseMemberData(byte[] data) throws ParseException {
        if (data.length < 1) {  // Data is empty because member isn't initialized
            return null;
        }

        Member member = new Member();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int offset = 0;

        String fullName = new String(Arrays.copyOfRange(data, offset + 1, data[offset] + 1));

        offset += fullName.length() + 1;
        String dateOfBirth = new String(Arrays.copyOfRange(data, offset + 1, offset + data[offset] + 1));

        offset += dateOfBirth.length() + 1;
        String phoneNumber = new String(Arrays.copyOfRange(data, offset + 1, offset + data[offset] + 1));

        offset += phoneNumber.length() + 1;
        String expirationDate = new String(Arrays.copyOfRange(data, offset + 1, offset + data[offset] + 1));

        member.setFullName(fullName);
        member.setDateOfBirth(dateFormat.parse(dateOfBirth));
        member.setPhoneNumber(phoneNumber);
        member.setExpirationDate(dateFormat.parse(expirationDate));
        return member;
    }

    public boolean updateProfile(
        @NotNull String fullName,
        @NotNull Date dateOfBirth,
        @NotNull String phoneNumber
    ) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOfBirthFormatted = dateFormat.format(dateOfBirth);
        byte[] data = (
            (char) fullName.length() + fullName +
            (char) dateOfBirthFormatted.length() + dateOfBirthFormatted +
            (char) phoneNumber.length() + phoneNumber
        ).getBytes();

        try {
            CommandAPDU createCommand = new CommandAPDU(0x00, INS_UPDATE_MEMBER, P1_PROFILE, 0x00, data);
            ResponseAPDU response = sessionManager.transmit(createCommand);

            return response.getSW1() == 0x90 && response.getSW2() == 0x00;
        } catch (NullPointerException | CardException e) {
            e.printStackTrace();
            return false;
        }
    }
}
