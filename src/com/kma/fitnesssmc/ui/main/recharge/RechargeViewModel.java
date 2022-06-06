package com.kma.fitnesssmc.ui.main.recharge;

import com.kma.fitnesssmc.data.repository.MemberRepository;
import org.jetbrains.annotations.Nullable;

import javax.smartcardio.CardException;

public class RechargeViewModel {
    private final MemberRepository memberRepository;

    public RechargeViewModel(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public @Nullable String recharge(long remainingBalance) {
        if (remainingBalance <= 0) {
            return "Enter your recharge.";
        }

        try {
            return memberRepository.recharge(remainingBalance) ? null : "Recharge failed!";
        } catch (CardException e) {
            e.printStackTrace();
            return "Error! An error occurred. Please try again later.";
        }
    }
}
