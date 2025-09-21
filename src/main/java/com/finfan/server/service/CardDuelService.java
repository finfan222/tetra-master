package com.finfan.server.service;

import com.finfan.server.entity.AccountEntity;
import com.finfan.server.entity.ProfileEntity;
import com.finfan.server.enums.DuelWinType;
import com.finfan.server.enums.Rank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardDuelService {

    private final ProfileService profileService;

    @Value("${game.card-duel.add-win-rating}")
    private int addWinRating;
    @Value("${game.card-duel.add-loss-rating}")
    private int addLossRating;
    @Value("${game.card-duel.add-draw-rating}")
    private int addDrawRating;
    @Value("${game.card-duel.perfect-multiplier}")
    private double perfectMultiplier;
    @Value("${game.card-duel.comeback-multiplier}")
    private double comebackMultiplier;
    @Value("${game.card-duel.gil-win}")
    private int gilWin;
    @Value("${game.card-duel.gil-loss}")
    private int gilLoss;
    @Value("${game.card-duel.gil-perfect-multiplier}")
    private double gilPerfectMultiplier;
    @Value("${game.card-duel.gil-comeback-multiplier}")
    private double gilComebackMultiplier;

    private int[] calcRating(Rank winner, Rank losser, DuelWinType winType) {
        double winId = winner.ordinal();
        double lossId = losser.ordinal();
        double winCoeff = winId / lossId;
        double addRating = addWinRating / winCoeff;
        if (winType == DuelWinType.PERFECT) {
            addRating *= perfectMultiplier;
        } else if (winType == DuelWinType.COMEBACK) {
            addRating *= comebackMultiplier;
        }
        int winResult = (int) Math.ceil(addRating);

        double lossCoeff = lossId / winId;
        int lossResult = (int) Math.ceil(addLossRating / lossCoeff);

        log.debug("Подсчитано кол-во получаемого рейтинга у победителя {} и отнимание рейтинга у проигравшего {} со следующей комбинацией: Rank.{} vs Rank.{}", winResult, lossResult, winner, losser);

        return new int[] {
                winResult,
                lossResult
        };
    }

    private int[] calcGil(Rank winner, Rank losser, DuelWinType winType) {
        double winId = winner.ordinal();
        double lossId = losser.ordinal();
        int winResult = (int) Math.min(gilWin / (winId / lossId), 999);
        int lossResult = Math.min((int) (gilLoss * (lossId / winId)), 999);
        if (winType == DuelWinType.PERFECT) {
            winResult *= gilPerfectMultiplier;
            lossResult *= gilPerfectMultiplier;
        } else if (winType == DuelWinType.COMEBACK) {
            winResult *= gilComebackMultiplier;
            lossResult *= gilComebackMultiplier;
        }

        return new int[] {
                winResult,
                lossResult
        };
    }

    @Transactional
    public void reward(AccountEntity winAccount, AccountEntity lossAccount, DuelWinType winType) {
        ProfileEntity winner = winAccount.getProfile();
        ProfileEntity loser = lossAccount.getProfile();
        int[] rating = calcRating(winner.getRank(), loser.getRank(), winType);
        int[] gil = calcGil(winner.getRank(), loser.getRank(), winType);

        // Награды для победителя
        profileService.addRating(winner, rating[0]);
        profileService.addGil(winner, gil[0]);

        // Награды для проигравшего
        profileService.subRating(loser, rating[1]);
        profileService.subGil(loser, gil[1]);
    }
}
