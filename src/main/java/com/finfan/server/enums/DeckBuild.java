package com.finfan.server.enums;

import lombok.Getter;

@Getter
public enum DeckBuild {
    A(false),
    B(false),
    C(false),
    D(true),
    E(true);

    private final boolean withTalent;

    DeckBuild(boolean withTalent) {
        this.withTalent = withTalent;
    }

    public static DeckBuild getById(int id) {
        return DeckBuild.values()[id];
    }

}
