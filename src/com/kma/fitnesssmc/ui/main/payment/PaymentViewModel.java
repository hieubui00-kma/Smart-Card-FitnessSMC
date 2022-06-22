package com.kma.fitnesssmc.ui.main.payment;

import com.kma.fitnesssmc.data.model.EpisodePack;
import com.kma.fitnesssmc.data.model.Member;
import com.kma.fitnesssmc.data.repository.EpisodePackRepository;
import com.kma.fitnesssmc.data.repository.MemberRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.smartcardio.CardException;
import java.util.List;

public class PaymentViewModel {
    private final EpisodePackRepository episodePackRepository;

    private final MemberRepository memberRepository;

    public PaymentViewModel(EpisodePackRepository episodePackRepository, MemberRepository memberRepository) {
        this.episodePackRepository = episodePackRepository;
        this.memberRepository = memberRepository;
    }

    public @NotNull List<EpisodePack> getEpisodePacks() {
        return episodePackRepository.getEpisodePacks();
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
