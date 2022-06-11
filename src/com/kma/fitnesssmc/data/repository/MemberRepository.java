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
import java.util.Random;

import static com.kma.fitnesssmc.util.Constants.*;

public class MemberRepository {
    private final SessionManager sessionManager;

    public MemberRepository(@NotNull SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public Integer verify(@NotNull String pin) throws CardException {
        CommandAPDU verifyCommand = new CommandAPDU(0x00, INS_VERIFY_MEMBER, 0x00, 0x00, pin.getBytes());
        ResponseAPDU response = sessionManager.transmit(verifyCommand);

        if (response.getSW1() == 0x90 && response.getSW2() == 0x00) {
            return null;
        }

        return (int) response.getData()[0];
    }

    public @Nullable Member createMember(
        @NotNull String fullName,
        @NotNull Date dateOfBirth,
        @NotNull String phoneNumber,
        byte[] avatar
    ) throws CardException {
        // Initialization new member
        String memberID = createMemberID();
        Date now = Calendar.getInstance().getTime();
        Member member = new Member();

        member.setID(memberID);
        member.setFullName(fullName);
        member.setDateOfBirth(dateOfBirth);
        member.setPhoneNumber(phoneNumber);
        member.setAvatar(avatar);
        member.setExpirationDate(now);
        member.setRemainingBalance(0);

        // Parse new member to data bytes
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String profileData = new String(member.getProfileData());
        String nowFormatted = dateFormat.format(now);
        String remainingBalance = String.valueOf(0L);
        byte[] data = (
            (char) memberID.length() + memberID
            + profileData
            + (char) nowFormatted.length() + nowFormatted
            + (char) remainingBalance.length() + remainingBalance
        ).getBytes();

        // Transmit create member command
        CommandAPDU createCommand = new CommandAPDU(0x00, INS_CREATE_MEMBER, 0x00, 0x00, data);
        ResponseAPDU createResponse = sessionManager.transmit(createCommand);

        if (createResponse.getSW1() != 0x90 || createResponse.getSW2() != 0x00) {
            return null;
        }

        updateAvatar(avatar);
        return member;
    }

    private @NotNull String createMemberID() {
        Random random = new Random();
        int max = 99999999;
        int min = 10000000;
        int memberID = random.nextInt((max - min) + 1) + min;

        return String.valueOf(memberID);
    }

    public @Nullable Member getMember() {
        try {
            CommandAPDU getCommand = new CommandAPDU(0x00, INS_GET_MEMBER, P1_PROFILE, 0x00);
            ResponseAPDU response = sessionManager.transmit(getCommand);

            if (response.getSW1() != 0x90 || response.getSW2() != 0x00) {
                return null;
            }

            Member member = parseData(response.getData());

            if (member == null) {
                return null;
            }

            byte[] avatar = getAvatar();

            member.setAvatar(avatar);
            return member;
        } catch (CardException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private @Nullable Member parseData(byte[] rawData) throws ParseException {
        if (rawData.length < 1) {  // Data is empty because member isn't initialized
            return null;
        }

        Member member = new Member();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int offset = 0;
        byte[] data;

        data = Arrays.copyOfRange(rawData, offset + 1, rawData[offset] + 1);
        String memberID = new String(data);

        offset += memberID.length() + 1;
        data = Arrays.copyOfRange(rawData, offset + 1, offset + rawData[offset] + 1);
        String fullName = new String(data);

        offset += fullName.length() + 1;
        data = Arrays.copyOfRange(rawData, offset + 1, offset + rawData[offset] + 1);
        String dateOfBirth = new String(data);

        offset += dateOfBirth.length() + 1;
        data = Arrays.copyOfRange(rawData, offset + 1, offset + rawData[offset] + 1);
        String phoneNumber = new String(data);

        offset += phoneNumber.length() + 1;
        data = Arrays.copyOfRange(rawData, offset + 1, offset + rawData[offset] + 1);
        String expirationDate = new String(data);

        offset += expirationDate.length() + 1;
        data = Arrays.copyOfRange(rawData, offset + 1, offset + rawData[offset] + 1);
        long remainingBalance = Long.parseLong(new String(data));

        member.setID(memberID);
        member.setFullName(fullName);
        member.setDateOfBirth(dateFormat.parse(dateOfBirth));
        member.setPhoneNumber(phoneNumber);
        member.setExpirationDate(dateFormat.parse(expirationDate));
        member.setRemainingBalance(remainingBalance);
        return member;
    }

    public byte[] getAvatar() throws CardException {
        CommandAPDU getCommand = new CommandAPDU(0x00, INS_GET_MEMBER, P1_AVATAR, 0x00);
        ResponseAPDU response = sessionManager.transmit(getCommand);

        return response.getSW1() == 0x90 && response.getSW2() == 0x00 ? response.getData() : null;
    }

    public @Nullable Long getRemainingBalance() throws CardException {
        CommandAPDU getCommand = new CommandAPDU(0x00, INS_GET_MEMBER, P1_REMAINING_BALANCE, 0x00);
        ResponseAPDU response = sessionManager.transmit(getCommand);

        if (response.getSW1() != 0x90 || response.getSW2() != 0x00) {
            return null;
        }

        return Long.parseLong(new String(response.getData()));
    }

    public Integer updatePin(
        @NotNull String currentPIN,
        @NotNull String newPIN
    ) throws CardException {
        String data = (char) currentPIN.length() + currentPIN + (char) newPIN.length() + newPIN;
        CommandAPDU updateCommand = new CommandAPDU(0x00, INS_UPDATE_MEMBER, P1_PIN, 0x00, data.getBytes());
        ResponseAPDU response = sessionManager.transmit(updateCommand);

        if (response.getSW1() == 0x90 && response.getSW2() == 0x00) {
            return null;
        }

        return (int) response.getData()[0];
    }

    public boolean updateProfile(
        @NotNull String fullName,
        @NotNull Date dateOfBirth,
        @NotNull String phoneNumber,
        byte[] avatar
    ) throws CardException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOfBirthFormatted = dateFormat.format(dateOfBirth);
        byte[] data = (
            (char) fullName.length() + fullName +
            (char) dateOfBirthFormatted.length() + dateOfBirthFormatted +
            (char) phoneNumber.length() + phoneNumber
        ).getBytes();

        CommandAPDU updateCommand = new CommandAPDU(0x00, INS_UPDATE_MEMBER, P1_PROFILE, 0x00, data);
        ResponseAPDU response = sessionManager.transmit(updateCommand);

        updateAvatar(avatar);
        return response.getSW1() == 0x90 && response.getSW2() == 0x00;
    }

    private void updateAvatar(byte[] avatar) throws CardException {
        CommandAPDU updateCommand = new CommandAPDU(0x00, INS_UPDATE_MEMBER, P1_AVATAR, 0x00, avatar);

        sessionManager.transmit(updateCommand);
    }

    public boolean recharge(long balance) throws CardException {
        Long currentRemainingBalance = getRemainingBalance();

        if (currentRemainingBalance == null) {
            return false;
        }

        byte[] data = String.valueOf(currentRemainingBalance + balance).getBytes();
        CommandAPDU updateCommand = new CommandAPDU(0x00, INS_UPDATE_MEMBER, P1_REMAINING_BALANCE, 0x00, data);
        ResponseAPDU response = sessionManager.transmit(updateCommand);

        return response.getSW1() == 0x90 && response.getSW2() == 0x00;
    }
}
