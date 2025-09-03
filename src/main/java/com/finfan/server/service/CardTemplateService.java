package com.finfan.server.service;

import com.finfan.server.Rand;
import com.finfan.server.entity.dictionaries.CardTemplateEntity;
import com.finfan.server.repository.CardTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardTemplateService {

    private final CardTemplateRepository cardTemplateRepository;

    public CardTemplateEntity getTemplate(Long id) {
        return cardTemplateRepository.findById(id).orElse(null);
    }

    public List<CardTemplateEntity> getAll() {
        return cardTemplateRepository.findAll();
    }

    public CardTemplateEntity getRandomTemplate() {
        return Rand.get(getAll());
    }

}
