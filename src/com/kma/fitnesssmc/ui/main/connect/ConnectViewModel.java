package com.kma.fitnesssmc.ui.main.connect;

import com.kma.fitnesssmc.data.manager.SessionManager;
import com.kma.fitnesssmc.data.model.Member;
import com.kma.fitnesssmc.data.repository.MemberRepository;
import org.jetbrains.annotations.Nullable;

import javax.smartcardio.CardException;

public class ConnectViewModel {
    private final SessionManager sessionManager;

    private final MemberRepository memberRepository;

    public ConnectViewModel(SessionManager sessionManager, MemberRepository memberRepository) {
        this.sessionManager = sessionManager;
        this.memberRepository = memberRepository;
    }

    public @Nullable String connect(String pin) {
        String errorMessage = validate(pin);

        if (errorMessage != null) {
            return errorMessage;
        }

        try {
            sessionManager.connect();
            if (memberRepository.verify(pin)) {
                return null;
            }
        } catch (CardException e) {
            e.printStackTrace();
        }
        return "Card connection failed!";
    }

    private @Nullable String validate(@Nullable String pin) {
        if (pin == null || pin.isBlank()) {
            return "Enter your PIN";
        }

        if (pin.length() != 6) {
            return "Your PIN is invalid!";
        }

        return null;
    }

    public @Nullable Member getMember() {
        return memberRepository.getMember();
    }
}
