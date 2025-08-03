package com.finfan.server.service;

import com.finfan.server.entity.dictionaries.TalentEntity;
import com.finfan.server.repository.TalentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TalentService {

    private final TalentRepository talentRepository;

    public TalentEntity getTalent(long id) {
        return talentRepository.findById(id).orElse(null);
    }

}
