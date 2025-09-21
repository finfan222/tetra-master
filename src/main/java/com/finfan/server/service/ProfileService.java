package com.finfan.server.service;

import com.finfan.server.entity.AccountEntity;
import com.finfan.server.entity.ProfileEntity;
import com.finfan.server.enums.Rank;
import com.finfan.server.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final ObjectProvider<AccountService> accountServiceProvider;

    @Transactional
    public void save(ProfileEntity entity) {
        profileRepository.save(entity);
    }

    public ProfileEntity getProfileByAccountId(Long id) {
        return profileRepository.findById(id).orElse(null);
    }

    // TODO: кэшировать вызов онлайн обновляя его раз в 5 минут
    public List<ProfileEntity> getProfiles(Boolean online, int page, int size) {
        return accountServiceProvider.getObject()
                .getAccounts(online, page, size)
                .stream()
                .map(AccountEntity::getProfile)
                .toList();
    }

    @Transactional
    public boolean subGil(ProfileEntity profile, int count) {
        Long myGil = profile.getGil();
        if (myGil == null || myGil < count) {
            return false;
        }

        long result = myGil - count;
        profile.setGil(result);
        save(profile);
        return true;
    }

    @Transactional
    public void addGil(ProfileEntity profile, int count) {
        Long myGil = profile.getGil();
        long result = myGil + count;
        profile.setGil(result);
        save(profile);
    }

    @Transactional
    public void subRating(ProfileEntity profile, int sub) {
        Integer myRating = profile.getRating();
        if (myRating - sub <= 0) {
            myRating = 0;
        }

        int newRating = myRating - sub;
        profile.setRating(newRating);

        if (Rank.isNextRankReached(profile.getRank(), profile.getRating())) {
            profile.setRank(profile.getRank().next());
            log.debug("Ранг профиля {} повышен до {}", profile.getAccount().getName(), profile.getRank());
        }

        save(profile);
        log.debug("{} очков рейтинга отнято у {}, общий рейтинг = {}", sub, profile.getAccount().getName(), newRating);
    }

    @Transactional
    public void addRating(ProfileEntity profile, int add) {
        Integer myRating = profile.getRating();
        if (myRating + add > Short.MAX_VALUE) {
            myRating = (int) Short.MAX_VALUE;
        }

        int newRating = myRating + add;
        profile.setRating(newRating);

        if (Rank.isPreviousRankReached(profile.getRank(), profile.getRating())) {
            profile.setRank(profile.getRank().previous());
            log.debug("Ранг профиля {} снижен до {}", profile.getAccount().getName(), profile.getRank());
        }

        save(profile);
        log.debug("{} очков рейтинга добавлено для {}, общий рейтинг = {}", add, profile.getAccount().getName(), newRating);
    }
}
