package com.kma.fitnesssmc.data.model;

public class EpisodePack {
    private int episodePackID;

    private String name;

    private long price;

    private int duration;

    public EpisodePack() {
    }

    public EpisodePack(int episodePackID, String name, long price, int duration) {
        this.episodePackID = episodePackID;
        this.name = name;
        this.price = price;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return name + " - " + duration + " month - " + String.format("%,d VND", price);
    }

    public int getEpisodePackID() {
        return episodePackID;
    }

    public void setEpisodePackID(int episodePackID) {
        this.episodePackID = episodePackID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
