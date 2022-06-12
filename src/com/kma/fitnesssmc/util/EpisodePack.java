package com.kma.fitnesssmc.util;

public enum EpisodePack {
    COPPER("COPPER", 1, 2_000_000),
    SILVER("SILVER", 3, 5_500_000),
    GOLD("GOLD", 6, 10_500_000),
    DIAMOND("GOLD", 12, 20_000_000);

    private final String name;

    private final int duration;

    private final long price;

    EpisodePack(String name, int duration, long price) {
        this.name = name;
        this.duration = duration;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return name + " - " + duration + " month - " + String.format("%,d VND", price);
    }
}
