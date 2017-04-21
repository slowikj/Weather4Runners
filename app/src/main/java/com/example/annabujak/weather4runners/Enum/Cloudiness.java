package com.example.annabujak.weather4runners.Enum;

/**
 * Created by pawel.bujak on 21.04.2017.
 */

public enum Cloudiness {
    Big(0),
    Medium(1),
    Small(2),
    Sunny(3);
    private final int value;
    private Cloudiness(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static Cloudiness fromId(int id) {
        for (Cloudiness type : Cloudiness.values()) {
            if (type.getValue() == id) {
                return type;
            }
        }
        return null;
    }
}
