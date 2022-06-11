package com.kma.fitnesssmc.util;

public class Constants {
    public static final String APPLICATION_NAME = "FitnessSMC";

    public static final int WIDTH_FRAME = 880;
    public static final int HEIGHT_FRAME = 600;

    public static final byte[] AID = new byte[]{0x66, 0x69, 0x74, 0x6E, 0x65, 0x73, 0x73, 0x73, 0x6D, 0x63, 0x00};

    public static final int INS_VERIFY_MEMBER = 0x00;
    public static final int INS_CREATE_MEMBER = 0x01;
    public static final int INS_GET_MEMBER = 0x02;
    public static final int INS_UPDATE_MEMBER = 0x03;

    public static final int P1_PROFILE = 0x00;
    public static final int P1_AVATAR = 0x03;
    public static final int P1_REMAINING_BALANCE = 0x05;
    public static final int P1_PIN = 0x06;

    public static final String ERROR_MESSAGE_CARD_HAS_BLOCKED = "Card has blocked!";
}
