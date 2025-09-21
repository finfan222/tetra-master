package com.finfan.server.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public enum Rank {

    NOVICE(0),
    SEEKER(100),
    WAYFARER(500),
    GUARDIAN(1200),
    VETERAN(3400),
    LEGEND(6000),
    MASTER(9999);

    private final int requiredRating;

    Rank(int requiredRating) {
        this.requiredRating = requiredRating;
    }

    public Rank next() {
        if (ordinal() < values().length - 1) {
            return values()[ordinal() + 1];
        } else {
            return MASTER;
        }
    }

    public Rank previous() {
        if (ordinal() > 0) {
            return values()[ordinal() - 1];
        } else {
            return NOVICE;
        }
    }

    public static boolean isNextRankReached(Rank currentRank, int currentRating) {
        if (currentRank == MASTER) {
            return false;
        }

        try {
            Rank nextRank = values()[currentRank.ordinal() + 1];
            if (currentRating >= nextRank.requiredRating) {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("Текущий ранг {} не имеет следующего, выход за границы values() класса.", currentRank, e);
        }

        return false;
    }


    public static boolean isPreviousRankReached(Rank currentRank, int currentRating) {
        if (currentRank == NOVICE) {
            return false;
        }

        try {
            Rank previousRank = values()[currentRank.ordinal() - 1];
            if (currentRating <= previousRank.requiredRating) {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("Текущий ранг {} не имеет предыдущего, выход за границы values() класса.", currentRank, e);
        }

        return false;
    }
}
