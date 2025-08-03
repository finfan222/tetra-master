package com.finfan.server.enums;

import lombok.Getter;

@Getter
public enum Rank {

    NOVICE(0),
    SEEKER(500),
    WAYFARER(1000),
    GUARDIAN(2500),
    VETERAN(5600),
    LEGEND(7400),
    MASTER(9999);

    private final int requiredExp;

    Rank(int requiredExp) {
        this.requiredExp = requiredExp;
    }

}
