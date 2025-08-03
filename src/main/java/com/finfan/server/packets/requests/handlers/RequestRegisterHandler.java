package com.finfan.server.packets.requests.handlers;

import com.finfan.server.entity.AccountEntity;
import com.finfan.server.enums.Direction;
import com.finfan.server.enums.ReceiveRegisterResponse;
import com.finfan.server.packets.responses.ReceiveRegister;
import com.finfan.server.packets.requests.RequestRegister;
import com.finfan.server.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RequestRegisterHandler {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final AccountService accountService;

    @Value("${game.base-card-points-price}")
    private int baseCardPointPrice;
    @Value("${game.base-card-points}")
    private int baseCardPoints;
    @Value("${game.name-min-length}")
    private int nameMinLength;
    @Value("${game.name-max-length}")
    private int nameMaxLength;
    @Value("${game.pswd-min-length}")
    private int pswdMinLength;
    @Value("${game.pswd-max-length}")
    private int pswdMaxLength;

    @EventListener
    public void receive(RequestRegister requestRegister) {
        ReceiveRegister registerResponse = new ReceiveRegister();
        ReceiveRegisterResponse validateNameResult = validateName(requestRegister.getName());
        if (validateNameResult != null) {
            registerResponse.setResponse(validateNameResult);
        } else if (!validatePassword(requestRegister.getPassword())) {
            registerResponse.setResponse(ReceiveRegisterResponse.INCORRECT_PASSWORD);
        } else if (!validateTalent(requestRegister.getTalentId())) {
            registerResponse.setResponse(ReceiveRegisterResponse.TALENT_NOT_CHOOSE);
        } else if (!validateCards(requestRegister.getBaseCards())) {
            registerResponse.setResponse(ReceiveRegisterResponse.INCORRECT_CARD_DIRECTIONS);
        } else if (!validateSpentPoints(requestRegister.getBaseCards())) {
            registerResponse.setResponse(ReceiveRegisterResponse.INCORRECT_SPENT_POINTS);
        } else {
            registerResponse.setResponse(ReceiveRegisterResponse.OK);
            accountService.createMaster(requestRegister);
        }

        applicationEventPublisher.publishEvent(registerResponse);
    }

    private boolean validateTalent(int masterTalent) {
        return masterTalent > 0;
    }

    private ReceiveRegisterResponse validateName(String accountName) {
        int length = accountName.length();
        if (length < nameMinLength || length > nameMaxLength) {
            return ReceiveRegisterResponse.INCORRECT_NAME_LENGTH;
        }

        AccountEntity account = accountService.getAccount(accountName);
        if (account != null && accountName.equals(account.getName())) {
            return ReceiveRegisterResponse.ACCOUNT_ALREADY_EXISTS;
        }

        return null;
    }

    private boolean validatePassword(String password) {
        int length = password.length();
        return length >= pswdMinLength && length <= pswdMaxLength;
    }

    private boolean validateCards(Map<Long, Integer> cards) {
        for (Integer mask : cards.values()) {
            if (mask == 0) {
                return false;
            }
        }
        return true;
    }

    private boolean validateSpentPoints(Map<Long, Integer> cards) {
        int spentPoints = 0;
        for (Integer arrowMask : cards.values()) {
            for (Direction dir : Direction.values()) {
                if ((dir.getMask() & arrowMask) != 0) {
                    spentPoints += baseCardPointPrice;
                }
            }
        }
        return spentPoints <= baseCardPoints;
    }
}
