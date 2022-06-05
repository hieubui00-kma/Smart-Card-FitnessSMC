package com.kma.fitnesssmc.ui.main.change_pin;

import com.kma.fitnesssmc.data.repository.MemberRepository;
import org.jetbrains.annotations.Nullable;

public class ChangePinViewModel {
    private final MemberRepository memberRepository;

    public ChangePinViewModel(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public @Nullable String changePIN(String currentPin, String newPin, String confirmNewPin) {
        String errorMessage = validate(currentPin, newPin, confirmNewPin);

        if (errorMessage != null) {
            return errorMessage;
        }


        return memberRepository.updatePin(currentPin, newPin) ? null : "PIN change failed!";
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
}
