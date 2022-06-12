package com.kma.fitnesssmc.ui.main.payment;

import com.kma.fitnesssmc.data.model.Member;
import com.kma.fitnesssmc.data.repository.MemberRepository;
import com.kma.fitnesssmc.util.EpisodePack;
import org.jetbrains.annotations.Nullable;

import javax.smartcardio.CardException;

public class PaymentViewModel {
    private final MemberRepository memberRepository;

    public PaymentViewModel(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public @Nullable Member getMember() {
        return memberRepository.getMember();
    }

    public @Nullable String payment(EpisodePack episodePack, String PIN) {
        if (PIN == null || PIN.isBlank()) {
            return "Enter your PIN";
        }

        try {
            return memberRepository.payment(episodePack, PIN) ? null : "Payment has failed!";
        } catch (RuntimeException e) {
            return e.getMessage();
        } catch (CardException e) {
            e.printStackTrace();
            return "Error! An error occurred. Please try again later.";
        }
    }
}
