package com.kma.fitnesssmc.ui.main.payment;

import com.kma.fitnesssmc.data.model.Member;
import com.kma.fitnesssmc.data.repository.MemberRepository;
import org.jetbrains.annotations.Nullable;

public class PaymentViewModel {
    private final MemberRepository memberRepository;

    public PaymentViewModel(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public @Nullable Member getMember() {
        return memberRepository.getMember();
    }
}
