package com.finfan.server.service;

import com.finfan.server.entity.ProfileEntity;
import com.finfan.server.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Transactional
    public void save(ProfileEntity entity) {
        profileRepository.save(entity);
    }

    public ProfileEntity getProfileByAccountId(Long id) {
        return profileRepository.findById(id).orElse(null);
    }

}
