package com.finfan.server.service;

import com.finfan.server.entity.CardEntity;
import com.finfan.server.entity.ProfileEntity;
import com.finfan.server.entity.dictionaries.CardTemplateEntity;
import com.finfan.server.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardTemplateService cardTemplateService;

    public void update(CardEntity card) {
        cardRepository.save(card);
    }

    public void updateAll(List<CardEntity> cards) {
        cardRepository.saveAll(cards);
    }

    public CardEntity getCard(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    public List<CardEntity> getAllCardsByProfileId(Long id) {
        return cardRepository.findAllByProfileId(id);
    }

    public int getCount(int cardId) {
        return cardRepository.getCount(cardId);
    }

    @Transactional
    public List<CardEntity> createBaseCards(Map<Long, Integer> baseCards, ProfileEntity profile) {
        return baseCards.entrySet().stream().map(entry -> {
            Long key = entry.getKey();
            Integer value = entry.getValue();
            CardTemplateEntity template = cardTemplateService.getTemplate(key);
            CardEntity card = new CardEntity();
            card.setAtkArrows(value);
            card.setAtk(template.getAtk());
            card.setAtkType(template.getAtkType());
            card.setPDef(template.getPDef());
            card.setMDef(template.getMDef());
            card.setRateLvlAtk(template.getRateLvlAtk());
            card.setRateLvlAtkType(template.getRateLvlAtkType());
            card.setRateLvlMDef(template.getRateLvlMDef());
            card.setRateLvlPDef(template.getRateLvlPDef());
            card.setBase(false);
            card.setTemplate(template);
            card.setProfile(profile);
            return card;
        }).toList();
    }
}
