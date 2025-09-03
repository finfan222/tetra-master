package com.finfan.server.service;

import com.finfan.server.GameUtil;
import com.finfan.server.Rand;
import com.finfan.server.entity.CardEntity;
import com.finfan.server.entity.ProfileEntity;
import com.finfan.server.entity.dictionaries.CardTemplateEntity;
import com.finfan.server.enums.AtkType;
import com.finfan.server.enums.responses.EResponseCardGrowth;
import com.finfan.server.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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

    public int getCount(Long profileId, Long cardTemplateId) {
        return cardRepository.getCountByProfileIdAndTemplateId(profileId, cardTemplateId);
    }

    public List<CardEntity> getAllByProfileIdAndCardId(Long profileId, Long cardId) {
        return cardRepository.findAllByProfileIdAndCardId(profileId, cardId);
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
            card.setRateLvlAtkTypeToA(template.getRateLvlAtkTypeToA());
            card.setRateLvlAtkTypeToX(template.getRateLvlAtkTypeToX());
            card.setRateLvlMDef(template.getRateLvlMDef());
            card.setRateLvlPDef(template.getRateLvlPDef());
            card.setBase(false);
            card.setValuable(GameUtil.valuate(card.getAtk(), card.getPDef(), card.getMDef(), card.getAtkType(), card.getAtkArrows()));
            card.setTemplate(template);
            card.setProfile(profile);
            return card;
        }).toList();
    }

    @Transactional
    public CardEntity createCard(ProfileEntity profile, Long cardTemplateId, int atkArrows) {
        CardTemplateEntity template = cardTemplateService.getTemplate(cardTemplateId);
        CardEntity card = new CardEntity();
        card.setAtkArrows(atkArrows);
        card.setAtk(template.getAtk());
        card.setAtkType(template.getAtkType());
        card.setPDef(template.getPDef());
        card.setMDef(template.getMDef());
        card.setRateLvlAtk(template.getRateLvlAtk());
        card.setRateLvlAtkTypeToA(template.getRateLvlAtkTypeToA());
        card.setRateLvlAtkTypeToX(template.getRateLvlAtkTypeToX());
        card.setRateLvlMDef(template.getRateLvlMDef());
        card.setRateLvlPDef(template.getRateLvlPDef());
        card.setBase(false);
        card.setValuable(GameUtil.valuate(card.getAtk(), card.getPDef(), card.getMDef(), card.getAtkType(), card.getAtkArrows()));
        card.setTemplate(template);
        card.setProfile(profile);
        return cardRepository.save(card);
    }

    /**
     * Расти могут только карты победители (участвовавшие в бою в ПОБЕДНОМ матче)<br>
     * Если ранг карты уже достиг максимума, то она не растет<br>
     * Вырасти может только один параметр, который отбирается случайным образом<br>
     * КЛиент не передает ID карта, при начале схватки - выбранные карты созхрраняются в предматчевом состоянии!
     *
     * @param profileId ID профиля, у которого взращиваются карты (клиент отдает accountId где мы получаем профиль)
     * @param cardIds ID карт, которые участвовали в бою, клиент отдает UniqueID
     * @return возвращаем результат, после чего - проигрываем нужную анимацию и все дела в клиенте
     */
    public List<EResponseCardGrowth> tryGrowth(Long profileId, List<Long> cardIds) {
        List<CardEntity> cards = cardRepository.findAllByProfileIdAndWhereInIds(profileId, cardIds);
        if (cards.isEmpty()) {
            return Collections.emptyList();
        }

        List<EResponseCardGrowth> results = new ArrayList<>();
        for (CardEntity card : cards) {
            EResponseCardGrowth result = EResponseCardGrowth.NONE;
            Float rateLvlAtk = card.getRateLvlAtk();
            Float rateLvlAtkTypeToA = card.getRateLvlAtkTypeToA();
            Float rateLvlAtkTypeToX = card.getRateLvlAtkTypeToX();
            Float rateLvlMDef = card.getRateLvlMDef();
            Float rateLvlPDef = card.getRateLvlPDef();

            int typeToIncrease = Rand.get(4);
            switch (typeToIncrease) {
                case 0:
                    if (card.getAtk() == 0x0F) {
                        break;
                    }

                    if (Rand.calcChance(rateLvlAtk / card.getAtk(), 100)) {
                        card.setAtk(card.getAtk() + 1);
                        result = EResponseCardGrowth.ATK_INCREASE;
                    }
                    break;
                case 1:
                    if (card.getMDef() == 0x0F) {
                        break;
                    }

                    if (Rand.calcChance(rateLvlMDef / card.getMDef(), 100)) {
                        card.setMDef(card.getMDef() + 1);
                        result = EResponseCardGrowth.MDEF_INCREASE;
                    }
                    break;
                case 2:
                    if (card.getPDef() == 0x0F) {
                        break;
                    }

                    if (Rand.calcChance(rateLvlPDef / card.getPDef(), 100)) {
                        card.setPDef(card.getPDef() + 1);
                        result = EResponseCardGrowth.PDEF_INCREASE;
                    }
                    break;
                case 3:
                    if (card.getAtkType() == AtkType.A) {
                        break;
                    }

                    if (card.getAtkType() == AtkType.X) {
                        if (Rand.calcChance(rateLvlAtkTypeToA, 100)) {
                            card.setAtkType(AtkType.A);
                            result = EResponseCardGrowth.TYPE_TO_A_INCREASE;
                        }
                    }
                    else if (Rand.calcChance(rateLvlAtkTypeToX, 100)) {
                        card.setAtkType(AtkType.X);
                        result = EResponseCardGrowth.TYPE_TO_X_INCREASE;
                    }
                    break;
            }

            // Рассчитываем по новой ценность данной карты после ее роста
            if (result != EResponseCardGrowth.NONE) {
                GameUtil.valuate(card.getAtk(), card.getPDef(), card.getMDef(), card.getAtkType(), card.getAtkArrows());
            }

            results.add(result);
        }

        return results;
    }

}
