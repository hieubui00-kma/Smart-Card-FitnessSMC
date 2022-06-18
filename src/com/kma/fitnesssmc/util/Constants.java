package com.kma.fitnesssmc.util;

public class Constants {
    public static final String APPLICATION_NAME = "FitnessSMC";

    public static final int WIDTH_FRAME = 880;
    public static final int HEIGHT_FRAME = 600;

    public static final byte[] AID = new byte[]{0x66, 0x69, 0x74, 0x6E, 0x65, 0x73, 0x73, 0x73, 0x6D, 0x63, 0x00};

    public static final byte INS_AUTHENTICATION = (byte) 0x00;

    public static final byte INS_CREATE = (byte) 0x01;
    public static final byte INS_GET = (byte) 0x02;
    public static final byte INS_UPDATE = (byte) 0x03;

    public static final byte P1_PIN = (byte) 0x00;
    public static final byte P1_MEMBER = (byte) 0x01;
    public static final byte P1_SIGNATURE = (byte) 0x02;

    public static final byte P2_PROFILE = (byte) 0x00;
    public static final byte P2_EXPIRATION_DATE = (byte) 0x04;
    public static final byte P2_REMAINING_BALANCE = (byte) 0x05;
    public static final byte P2_AVATAR = (byte) 0x06;

    public static final String ERROR_MESSAGE_CARD_HAS_BLOCKED = "Card has blocked!";
}
