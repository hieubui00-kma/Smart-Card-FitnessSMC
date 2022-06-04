package com.kma.fitnesssmc.ui.main.home;

import com.kma.fitnesssmc.data.manager.SessionManager;
import com.kma.fitnesssmc.data.model.Member;
import com.kma.fitnesssmc.data.repository.MemberRepository;
import org.jetbrains.annotations.Nullable;

import javax.smartcardio.CardException;

public class HomeViewModel {
    private final SessionManager sessionManager;

    private final MemberRepository memberRepository;

    public HomeViewModel(SessionManager sessionManager, MemberRepository memberRepository) {
        this.sessionManager = sessionManager;
        this.memberRepository = memberRepository;
    }

    public @Nullable Member getMember() {
        return memberRepository.getMember();
    }

    public void disconnect() {
        try {
            sessionManager.disconnect();
        } catch (CardException e) {
            e.printStackTrace();
        }
    }
}
