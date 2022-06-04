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

    public @Nullable String connect(@Nullable String pin) {
        if (pin == null || pin.isBlank()) {
            return "Enter your PIN";
        }

        if (pin.length() != 6) {
            return "Your PIN is invalid!";
        }

        try {
            if (sessionManager.connect() == null) return "Card connection failed!";
        } catch (CardException e) {
            e.printStackTrace();
            return "Card connection failed!";
        }

        return memberRepository.verify(pin) ? null : "PIN code is incorrect!";
    }

    public @Nullable Member getMember() {
        return memberRepository.getMember();
    }
}
