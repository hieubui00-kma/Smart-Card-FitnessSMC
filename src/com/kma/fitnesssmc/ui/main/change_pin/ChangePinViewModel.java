package com.kma.fitnesssmc.ui.main.change_pin;

import com.kma.fitnesssmc.data.manager.SessionManager;
import com.kma.fitnesssmc.data.repository.MemberRepository;
import org.jetbrains.annotations.Nullable;

import javax.smartcardio.CardException;

import static com.kma.fitnesssmc.util.Constants.ERROR_MESSAGE_CARD_HAS_BLOCKED;

public class ChangePinViewModel {
    private final MemberRepository memberRepository;

    private final SessionManager sessionManager;

    public ChangePinViewModel(MemberRepository memberRepository, SessionManager sessionManager) {
        this.memberRepository = memberRepository;
        this.sessionManager = sessionManager;
    }

    public @Nullable String changePIN(String currentPin, String newPin, String confirmNewPin) {
        String errorMessage = validate(currentPin, newPin, confirmNewPin);

        if (errorMessage != null) {
            return errorMessage;
        }

        try {
            Integer retriesRemaining = memberRepository.updatePin(currentPin, newPin);

            if (retriesRemaining == null) {
                return null;
            }

            if (retriesRemaining == 0) {
                disconnect();
                return ERROR_MESSAGE_CARD_HAS_BLOCKED;
            }

            return "Your current PIN is incorrect!";
        } catch (CardException e) {
            e.printStackTrace();
            return "Error! An error occurred. Please try again later.";
        }
    }

    private @Nullable String validate(
        @Nullable String currentPin,
        @Nullable String newPin,
        @Nullable String confirmNewPin
    ) {
        if (currentPin == null || currentPin.isBlank()) {
            return "Enter your current PIN.";
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

    private void disconnect() {
        try {
            sessionManager.disconnect();
        } catch (CardException e) {
            e.printStackTrace();
        }
    }
}
