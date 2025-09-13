package com.finfan.server.service;

import com.finfan.server.GameUtil;
import com.finfan.server.Rand;
import com.finfan.server.entity.AccountEntity;
import com.finfan.server.entity.CardEntity;
import com.finfan.server.entity.ProfileEntity;
import com.finfan.server.entity.dictionaries.CardTemplateEntity;
import com.finfan.server.entity.dto.CardDto;
import com.finfan.server.entity.projections.CardCollectionProjection;
import com.finfan.server.entity.projections.CardListProjection;
import com.finfan.server.enums.AtkType;
import com.finfan.server.enums.DeckBuild;
import com.finfan.server.enums.responses.EResponseCardBuildSave;
import com.finfan.server.enums.responses.EResponseCardGrowth;
import com.finfan.server.network.packets.dto.incoming.RequestCardBuildSave;
import com.finfan.server.network.packets.dto.incoming.RequestPlayerCardList;
import com.finfan.server.network.packets.dto.outcoming.ResponseCardBuildSave;
import com.finfan.server.network.packets.dto.outcoming.ResponsePlayerCardList;
import com.finfan.server.repository.CardRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {

    private final EntityManager entityManager;
    private final CardRepository cardRepository;
    private final CardTemplateService cardTemplateService;

    public CardEntity getCard(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    public List<CardEntity> getAllCardsByProfileId(Long id) {
        return cardRepository.findAllByProfileId(id);
    }

    public List<CardListProjection> getAllByProfileIdAsProjection(Long profileId) {
        return cardRepository.findAllByProfileIdAsProjection(profileId);
    }

    public List<CardListProjection> getAllByProfileIdAndCardIdAsProjection(Long profileId, Long cardId) {
        return cardRepository.findAllByProfileIdAndCardIdAsProjection(profileId, cardId);
    }

    public List<CardCollectionProjection> getAllCardIdsAndTheirCountByProfileIdAsProjection(Long profileId) {
        return cardRepository.findAllCardIdsAndTheirCountByProfileIdAsProjection(profileId);
    }

    public int getCount(Long profileId, Long cardTemplateId) {
        return cardRepository.getCountByProfileIdAndTemplateId(profileId, cardTemplateId);
    }

    public List<CardEntity> getAllByProfileIdAndCardId(Long profileId, Long cardId) {
        return cardRepository.findAllByProfileIdAndCardId(profileId, cardId);
    }

    @Transactional
    public void deleteById(Long id) {
        cardRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllByIds(List<Long> ids) {
        cardRepository.deleteAllByIds(ids);
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
            card.setBase(true);
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

    @Transactional
    public CardEntity createCard(ProfileEntity profile, CardDto dto) {
        CardTemplateEntity template = cardTemplateService.getTemplate(dto.getCardId());
        CardEntity card = new CardEntity();
        card.setAtkArrows(dto.getAtkArrows());
        card.setAtk(dto.getAtk());
        card.setAtkType(dto.getAtkType());
        card.setPDef(dto.getPDef());
        card.setMDef(dto.getMDef());
        card.setRateLvlAtk(template.getRateLvlAtk());
        card.setRateLvlAtkTypeToA(template.getRateLvlAtkTypeToA());
        card.setRateLvlAtkTypeToX(template.getRateLvlAtkTypeToX());
        card.setRateLvlMDef(template.getRateLvlMDef());
        card.setRateLvlPDef(template.getRateLvlPDef());
        card.setBase(dto.isBase());
        card.setValuable(GameUtil.valuate(card.getAtk(), card.getPDef(), card.getMDef(), card.getAtkType(), card.getAtkArrows()));
        card.setTemplate(template);
        card.setProfile(profile);
        return cardRepository.save(card);
    }

    @Transactional
    public void updateCard(CardDto dto) {
        CardEntity card = getCard(dto.getId());
        if (card != null) {
            card.setAtk(dto.getAtk());
            card.setAtkType(dto.getAtkType());
            card.setMDef(dto.getMDef());
            card.setPDef(dto.getPDef());
            card.setAtkArrows(dto.getAtkArrows());
            card.setBase(dto.isBase());
            card.setBuild(dto.getBuild());
            cardRepository.save(card);
        }
    }

    /**
     * Расти могут только карты победители (участвовавшие в бою в ПОБЕДНОМ матче)<br>
     * Если ранг карты уже достиг максимума, то она не растет<br>
     * Вырасти может только один параметр, который отбирается случайным образом<br>
     * КЛиент не передает ID карта, при начале схватки - выбранные карты созхрраняются в предматчевом состоянии!
     *
     * @param profileId ID профиля, у которого взращиваются карты (клиент отдает accountId где мы получаем профиль)
     * @param cardIds   ID карт, которые участвовали в бою, клиент отдает UniqueID
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
                    } else if (Rand.calcChance(rateLvlAtkTypeToX, 100)) {
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

    @EventListener
    protected void onRequestPlayerCardList(RequestPlayerCardList event) {
        AccountEntity account = event.getGameSession().getAccount();
        ProfileEntity profile = account.getProfile();
        ResponsePlayerCardList responsePlayerCardList = new ResponsePlayerCardList();
        responsePlayerCardList.setCards(getAllByProfileIdAsProjection(profile.getId()));
        event.getGameSession().sendPacket(responsePlayerCardList);
    }

    @Transactional
    @EventListener
    protected void onRequestCardBuildSave(RequestCardBuildSave event) {
        ResponseCardBuildSave responseCardBuildSave = new ResponseCardBuildSave();
        try {
            // если игрок убрал все карты из билда, значит он его чистит, разрешаем это дело
            byte build = event.getBuild();
            if ((event.getCards() == null || event.getCards().isEmpty()) && build >= 0) {
                List<CardEntity> buildCards = cardRepository.findAllByProfileIdAndBuild(event.getGameSession().getAccount().getProfile().getId(),
                        DeckBuild.values()[build]);
                buildCards.forEach(card -> card.setBuild(null));
                cardRepository.saveAll(buildCards);

                List<CardDto> list = buildCards.stream().map(card -> {
                    CardDto dto = new CardDto();
                    dto.setId(card.getId());
                    dto.setCardId(card.getTemplate().getId());
                    dto.setBuild(null);
                    return dto;
                }).toList();

                responseCardBuildSave.setModifiedCards(list);
                responseCardBuildSave.setResponse(EResponseCardBuildSave.OK_CLEAR);
                return;
            }

            List<CardEntity> modifyingCards = cardRepository.findAllById(event.getCards().stream().map(CardDto::getId).toList());
            // поиск дубликатов (если каким-то чудом были подменены айдишники карт
            Map<Long, List<CardDto>> collect = event.getCards().stream()
                    .collect(Collectors.groupingBy(CardDto::getId))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue().size() > 1)
                    .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

            // если игрок имеет волшебную силу и протащил одинаковые unique_id карт и попытался их передать
            if (collect.values().stream().anyMatch(list -> list.size() > 1)) {
                responseCardBuildSave.setResponse(EResponseCardBuildSave.BUILD_CANNOT_CONTAIN_SAME_CARDS);
                return;
            }

            if (event.getCards().size() < 5) {
                responseCardBuildSave.setResponse(EResponseCardBuildSave.BUILD_MUST_HAVE_AT_LEAST_5_CARDS);
                return;
            }

            // если игрок за счет гендальфа закинул более 5 карт или размеры найденных по ID на серваке и переданных клиентом - не совпадают
            if (event.getCards().size() > 5 || modifyingCards.size() != event.getCards().size()) {
                responseCardBuildSave.setResponse(EResponseCardBuildSave.THIS_BUILD_IS_NOT_AVAILABLE);
                return;
            }

            // если игрок волшебным образом использовал карту которая есть на сервере, но не принадлежит его профилю
            Long profileId = event.getGameSession().getAccount().getProfile().getId();
            for (CardEntity next : modifyingCards) {
                if (!Objects.equals(next.getProfile().getId(), profileId)) {
                    responseCardBuildSave.setResponse(EResponseCardBuildSave.THIS_BUILD_IS_NOT_AVAILABLE);
                    return;
                }
            }

            // успешно найдены все карты профиля, теперь сетим им build, сохраняем и ставим ответ OK
            event.getCards().forEach(dto -> modifyingCards.stream()
                    .filter(entity -> Objects.equals(entity.getId(), dto.getId()))
                    .forEach(entity -> {
                        dto.setCardId(entity.getTemplate().getId());
                        entity.setBuild(dto.getBuild());
                    }));

            cardRepository.refreshBuilds(profileId, DeckBuild.getById(build), modifyingCards.stream().map(CardEntity::getId).toList());

            responseCardBuildSave.setModifiedCards(event.getCards());
            responseCardBuildSave.setResponse(EResponseCardBuildSave.OK);
        } finally {
            event.getGameSession().sendPacket(responseCardBuildSave);
        }
    }

}
