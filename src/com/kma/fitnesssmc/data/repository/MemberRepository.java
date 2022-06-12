package com.kma.fitnesssmc.data.repository;

import com.kma.fitnesssmc.data.manager.SessionManager;
import com.kma.fitnesssmc.data.model.Member;
import com.kma.fitnesssmc.util.EpisodePack;
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

    /**
     * Authenticate the card that was connected to the application
     *
     * @param PIN the card PIN code
     * @return the retries remaining when authentication has failed or NULL when authentication has successfully
     * @throws CardException if card is not connected
     */
    public Integer authentication(@NotNull String PIN) throws CardException {
        byte[] data = PIN.getBytes();
        CommandAPDU verifyCommand = new CommandAPDU(0x00, INS_AUTHENTICATION_MEMBER, 0x00, 0x00, data);
        ResponseAPDU response = sessionManager.transmit(verifyCommand);

        return response.getSW1() == 0x90 && response.getSW2() == 0x00 ? null : (int) response.getData()[0];
    }

    /**
     * Create a new member for the connected card
     *
     * @param fullName    the full name of new member
     * @param dateOfBirth the date of birth of new member
     * @param phoneNumber the phone number of new member
     * @param avatar      the avatar of new member
     * @return the new member that was created successfully or NULL when create has failed
     * @throws CardException if card is not connected
     */
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
        member.setRemainingBalance(0L);

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

    /**
     * Generate random ID for member
     *
     * @return member ID
     */
    private @NotNull String createMemberID() {
        Random random = new Random();
        int max = 99999999;
        int min = 10000000;
        int memberID = random.nextInt((max - min) + 1) + min;

        return String.valueOf(memberID);
    }

    /**
     * Get member's profile already stored in the connected card
     *
     * @return the member's profile has been stored in the card or NULL when member isn't initialized
     */
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

    /**
     * Parse the member raw data received from the card into a Member object
     *
     * @param rawData the member raw data received from the card
     * @return the member that was parsed from the member raw data
     * @throws ParseException if raw data is invalid
     */
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

    /**
     * Get member's avatar already stored in the connected card
     *
     * @return the member's avatar has been stored in the card or NULL when member isn't initialized or has no data
     * @throws CardException if card is not connected
     */
    public byte[] getAvatar() throws CardException {
        CommandAPDU getCommand = new CommandAPDU(0x00, INS_GET_MEMBER, P1_AVATAR, 0x00);
        ResponseAPDU response = sessionManager.transmit(getCommand);

        return response.getSW1() == 0x90 && response.getSW2() == 0x00 ? response.getData() : null;
    }

    /**
     * Get member's remaining balance already stored in the connected card
     *
     * @return the member's remaining balance has been stored in the card or NULL when member isn't initialized or data is invalid
     * @throws CardException if card is not connected
     */
    public @Nullable Long getRemainingBalance() throws CardException {
        CommandAPDU getCommand = new CommandAPDU(0x00, INS_GET_MEMBER, P1_REMAINING_BALANCE, 0x00);
        ResponseAPDU response = sessionManager.transmit(getCommand);

        return response.getSW1() != 0x90 || response.getSW2() != 0x00 ? null : Long.parseLong(new String(response.getData()));
    }

    /**
     * Update PIN that already stored in the connected card
     *
     * @param currentPIN the existing PIN stored in the card
     * @param newPIN     a new PIN that replaces the existing PIN stored in the card
     * @return the retries remaining when current PIN is incorrect or NULL when update PIN has successfully
     * @throws CardException if card is not connected
     */
    public Integer updatePin(
        @NotNull String currentPIN,
        @NotNull String newPIN
    ) throws CardException {
        byte[] data = ((char) currentPIN.length() + currentPIN + (char) newPIN.length() + newPIN).getBytes();
        CommandAPDU updateCommand = new CommandAPDU(0x00, INS_UPDATE_MEMBER, P1_PIN, 0x00, data);
        ResponseAPDU response = sessionManager.transmit(updateCommand);

        return response.getSW1() == 0x90 && response.getSW2() == 0x00 ? null : (int) response.getData()[0];
    }

    /**
     * Update member's profile that already stored in the connected card
     *
     * @param fullName    the full name of new member
     * @param dateOfBirth the date of birth of new member
     * @param phoneNumber the phone number of new member
     * @param avatar      the avatar of new member
     * @return true when update has successfully otherwise false when update failed
     * @throws CardException if card is not connected
     */
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

    /**
     * Update member's avatar that already stored in the connected card
     *
     * @param avatar the new member's avatar
     * @return true when update has successfully otherwise false when update failed
     * @throws CardException if card is not connected
     */
    private boolean updateAvatar(byte[] avatar) throws CardException {
        CommandAPDU updateCommand = new CommandAPDU(0x00, INS_UPDATE_MEMBER, P1_AVATAR, 0x00, avatar);
        ResponseAPDU response = sessionManager.transmit(updateCommand);

        return response.getSW1() == 0x90 && response.getSW2() == 0x00;
    }

    /**
     * Update member's expiration date in the connected card
     *
     * @param date the new member's expiration date that will update
     * @return true when update has successfully otherwise false when update failed
     * @throws CardException if card is not connected
     */
    private boolean updateExpirationDate(@NotNull Date date) throws CardException {
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
        byte[] data = dateFormat.getBytes();
        CommandAPDU updateCommand = new CommandAPDU(0x00, INS_UPDATE_MEMBER, P1_EXPIRATION_DATE, 0x00, data);
        ResponseAPDU response = sessionManager.transmit(updateCommand);

        return response.getSW1() == 0x90 && response.getSW2() == 0x00;
    }

    /**
     * Update member's remaining balance in the connected card
     *
     * @param remainingBalance the new member's remaining balance that will update
     * @return true when update has successfully otherwise false when update failed
     * @throws CardException if card is not connected
     */
    private boolean updateRemainingBalance(long remainingBalance) throws CardException {
        byte[] data = String.valueOf(remainingBalance).getBytes();
        CommandAPDU updateCommand = new CommandAPDU(0x00, INS_UPDATE_MEMBER, P1_REMAINING_BALANCE, 0x00, data);
        ResponseAPDU response = sessionManager.transmit(updateCommand);

        return response.getSW1() == 0x90 && response.getSW2() == 0x00;
    }

    /**
     * Recharge the balance for member and save to card
     *
     * @param amount amount want to recharge
     * @return true when update has successfully otherwise false when update failed
     * @throws CardException if card is not connected
     */
    public boolean recharge(long amount) throws CardException {
        Long currentRemainingBalance = getRemainingBalance();

        return currentRemainingBalance != null && updateRemainingBalance(currentRemainingBalance + amount);
    }

    /**
     * Payment the Episode Pack for member and save to card
     *
     * @param episodePack the Episode Pack that member want to payment
     * @param PIN the existing PIN stored in the card
     * @return true when update has successfully otherwise false when update failed
     * @throws CardException if card is not connected
     */
    public boolean payment(
        @NotNull EpisodePack episodePack,
        @NotNull String PIN
    ) throws CardException, RuntimeException {
        Integer retriesRemaining = authentication(PIN);

        if (retriesRemaining != null) {
            if (retriesRemaining == 0) {
                throw new RuntimeException(ERROR_MESSAGE_CARD_HAS_BLOCKED);
            }

            throw new RuntimeException("Your PIN is incorrect!");
        }

        Member member = getMember();

        if (member == null) {
            return false;
        }

        if (episodePack.getPrice() > member.getRemainingBalance()) {
            throw new RuntimeException("Your remaining balance is not enough");
        }

        if (!updateRemainingBalance(member.getRemainingBalance() - episodePack.getPrice())) {
            return false;
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(member.getExpirationDate());
        calendar.add(Calendar.MONTH, episodePack.getDuration());
        return updateExpirationDate(calendar.getTime());
    }
}
