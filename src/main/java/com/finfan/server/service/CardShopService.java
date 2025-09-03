package com.finfan.server.service;

import com.finfan.server.GameUtil;
import com.finfan.server.Rand;
import com.finfan.server.entity.AccountEntity;
import com.finfan.server.entity.CardEntity;
import com.finfan.server.entity.CardShopEntity;
import com.finfan.server.entity.ProfileEntity;
import com.finfan.server.entity.dictionaries.CardTemplateEntity;
import com.finfan.server.enums.Direction;
import com.finfan.server.enums.responses.EResponseCardShopBuy;
import com.finfan.server.network.GameSession;
import com.finfan.server.network.packets.dto.incoming.RequestCardShop;
import com.finfan.server.network.packets.dto.incoming.RequestCardShopBuy;
import com.finfan.server.network.packets.dto.incoming.RequestCardShopInfo;
import com.finfan.server.network.packets.dto.outcoming.ResponseCardShop;
import com.finfan.server.network.packets.dto.outcoming.ResponseCardShopBuy;
import com.finfan.server.network.packets.dto.outcoming.ResponseCardShopInfo;
import com.finfan.server.repository.CardShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardShopService {

    private final CardShopRepository cardShopRepository;
    private final CardTemplateService cardTemplateService;
    private final CardService cardService;
    private final AccountService accountService;
    private final ProfileService profileService;

    private List<CardShopEntity> shopCards;
    @Value("${game.shop.card-generate-count}")
    private int cardGenerateCount;
    @Value("${game.shop.card-generate-arrow-chances}")
    private int[] cardGenerateArrowChances;
    @Value("${game.shop.generate-card-deck-cron}")
    private String nextUpdateDateCron;

    private volatile boolean isClosed;

    @Scheduled(cron = "${game.shop.generate-card-deck-cron}")
    @Transactional
    public synchronized void generate() {
        isClosed = true;
        try {
            cardShopRepository.deleteAll();
            List<CardShopEntity> list = new ArrayList<>();
            for (int i = 0; i < cardGenerateCount; i++) {
                CardTemplateEntity template = cardTemplateService.getRandomTemplate();
                CardShopEntity cardShopEntity = new CardShopEntity();
                cardShopEntity.setTemplate(template);
                cardShopEntity.setAtkArrows(generateArrows());
                cardShopEntity.setPrice(GameUtil.valuate(template.getAtk(), template.getPDef(), template.getMDef(), template.getAtkType(), cardShopEntity.getAtkArrows()));
                list.add(cardShopEntity);
                log.debug("Карта для магазина {} сгенерирована: {}", cardShopEntity.getTemplate().getName(), cardShopEntity);
            }
            shopCards = cardShopRepository.saveAll(list);
        } finally {
            isClosed = false;
        }
    }

    private int generateArrows() {
        List<Direction> directions = new ArrayList<>(Arrays.asList(Direction.values()));
        int generated = 0;
        log.debug("=== НАЧАЛО: Генерация направлений атаки ===");
        for (int i = 0; i < Direction.SIZE; i++) {
            Direction direction = Rand.get(directions);
            if (Rand.calcChance(cardGenerateArrowChances[i], 100)) {
                generated |= direction.getMask();
                log.debug("Сгенерирована стрелка атаки: {} с шансом {}%", direction, cardGenerateArrowChances[i]);
                directions.remove(direction);
            }
        }
        log.debug("=== ЗАВЕРШЕНО: Генерация направлений атаки ===");
        return generated;
    }

    private boolean checkUnique(CardEntity myCard, int arrowsToCheck) {
        return Objects.equals(myCard.getAtkArrows(), arrowsToCheck);
    }

    public List<CardShopEntity> getAll() {
        return cardShopRepository.findAll();
    }

    @EventListener
    public void onRequestCardShop(RequestCardShop event) {
        shopCards = getAll();
        if (shopCards.isEmpty()) {
            generate();
        }

        ResponseCardShop responseCardShop = new ResponseCardShop();
        responseCardShop.setNextUpdateDate(GameUtil.getNextExecutionTime(nextUpdateDateCron).toLocalDate());
        responseCardShop.setShopCards(shopCards);
        sendCardShop(event.getGameSession(), responseCardShop);
    }

    public void sendCardShop(GameSession gameSession, ResponseCardShop responseCardShop) {
        gameSession.sendPacket(responseCardShop);
    }

    @EventListener
    public void onRequestCardShopInfo(RequestCardShopInfo event) {
        sendCardShopInfo(event.getGameSession(), event.getCardId(), event.getAtkArrows());
    }

    public void sendCardShopInfo(GameSession gameSession, long cardTemplateId, int atkArrows) {
        ProfileEntity profile = gameSession.getAccount().getProfile();
        ResponseCardShopInfo responseCardShopInfo = new ResponseCardShopInfo();
        responseCardShopInfo.setMyGil(profile.getGil());
        int count = cardService.getCount(profile.getId(), cardTemplateId);
        responseCardShopInfo.setCardCount(count);
        responseCardShopInfo.setCardInStock(count > 0);
        List<CardEntity> cards = cardService.getAllByProfileIdAndCardId(profile.getId(), cardTemplateId);
        boolean isUnique = cards.stream().anyMatch(e -> checkUnique(e, atkArrows)) || cards.isEmpty();
        responseCardShopInfo.setCardIsUnique(isUnique);
        gameSession.sendPacket(responseCardShopInfo);
    }

    @EventListener
    @Transactional
    public void onRequestCardShopBuy(RequestCardShopBuy event) {
        EResponseCardShopBuy responseMessage;
        CardShopEntity shopCard = cardShopRepository.findById(event.getShopCardId()).orElse(null);
        CardEntity newCard = null;

        if (shopCard == null) {
            responseMessage = EResponseCardShopBuy.UNKNOWN_ERROR;
        } else {
            AccountEntity account = event.getGameSession().getAccount();
            ProfileEntity profile = account.getProfile();
            // тратим деньги
            if (profileService.reduceGil(profile, shopCard.getPrice())) {
                newCard = cardService.createCard(profile, shopCard.getTemplate().getId(), shopCard.getAtkArrows());
                accountService.save(account);
                responseMessage = EResponseCardShopBuy.OK;
            } else {
                responseMessage = EResponseCardShopBuy.NOT_ENOUGH_GIL;
            }
        }

        sendCardShopBuy(event.getGameSession(), responseMessage, newCard);
    }

    public void sendCardShopBuy(GameSession gameSession, EResponseCardShopBuy responseMessage, CardEntity card) {
        ResponseCardShopBuy responseCardShopBuy = new ResponseCardShopBuy();
        responseCardShopBuy.setCard(card);
        responseCardShopBuy.setResponseMessage(responseMessage);
        gameSession.sendPacket(responseCardShopBuy);
    }
}
