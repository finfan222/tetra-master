package com.finfan.server.enums;

import lombok.Getter;

@Getter
public enum AtkType {
    P(0),
    M(0),
    X(800),
    A(1800);

    private final int valuable;

    AtkType(int valuable) {
        this.valuable = valuable;
    }

}
