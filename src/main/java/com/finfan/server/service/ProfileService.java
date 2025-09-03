package com.finfan.server.service;

import com.finfan.server.entity.AccountEntity;
import com.finfan.server.entity.ProfileEntity;
import com.finfan.server.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public boolean reduceGil(ProfileEntity profile, int count) {
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
}
