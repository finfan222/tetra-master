package com.finfan.server.service;

import com.finfan.server.entity.AccountEntity;
import com.finfan.server.entity.ProfileEntity;
import com.finfan.server.enums.CollectorRank;
import com.finfan.server.enums.Direction;
import com.finfan.server.enums.Permission;
import com.finfan.server.enums.Portrait;
import com.finfan.server.enums.Rank;
import com.finfan.server.enums.responses.EResponseRegister;
import com.finfan.server.events.network.GameSessionInactive;
import com.finfan.server.network.packets.dto.incoming.RequestRegister;
import com.finfan.server.network.packets.dto.outcoming.ResponseRegister;
import com.finfan.server.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final TalentService talentService;
    private final CardService cardService;
    private final CardTemplateService cardTemplateService;
    private final ProfileService profileService;
    private final ObjectProvider<LobbyService> lobbyService;

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
    @Value("${game.base-gil}")
    private long baseGil;

    public AccountEntity getAccount(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public AccountEntity getAccount(String name) {
        return accountRepository.findByName(name).orElse(null);
    }

    public List<AccountEntity> getAccounts(int page, int size) {
        return accountRepository.findAll(PageRequest.of(page, size)).stream().toList();
    }

    public synchronized List<AccountEntity> getAccounts(Boolean online, int page, int size) {
        return accountRepository.findAllByOnline(online, PageRequest.of(page, size)).stream().toList();
    }

    @Transactional
    public void updateOnline(AccountEntity account, Boolean value) {
        account.setOnline(value);
        accountRepository.save(account);
    }

    @Transactional
    public void save(AccountEntity account) {
        accountRepository.save(account);
    }

    @Transactional
    public void createMaster(RequestRegister requestRegister) {
        AccountEntity account = new AccountEntity();
        account.setName(requestRegister.getName());
        account.setPassword(createSalt(requestRegister.getName(), requestRegister.getPassword()));
        account.setPermission(Permission.PLAYER);
        account.setLastIpAddress(requestRegister.getIpAddress());
        account.setLastAccess(LocalDateTime.now());
        account.setPremium(false);
        account.setEmail(requestRegister.getEmail());
        account.setOnline(false);
        System.out.println("Master " + account + " was created!");

        ProfileEntity profile = new ProfileEntity();
        profile.setPortrait(Portrait.values()[requestRegister.getPortrait()]);
        profile.setGil(baseGil);
        profile.setWins(0);
        profile.setLosses(0);
        profile.setRating(0);
        profile.setRank(Rank.NOVICE);
        profile.setCollectorRank(CollectorRank.BEGINNER);
        profile.setTalent(talentService.getTalent(requestRegister.getTalentId()));
        profile.setAccount(account);
        profile.setCards(cardService.createBaseCards(requestRegister.getBaseCards(), profile));
        System.out.println("Profile " + profile + " was created!");

        account.setProfile(profile);
        save(account);
    }

    public String createSalt(String account, String password) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        String hashPassword = new String(Base64.getEncoder().encode(password.getBytes()));
        return new String(Base64.getEncoder().encode(digest.digest((account + hashPassword).getBytes(StandardCharsets.UTF_8))));
    }

    @EventListener
    public void onRequestRegister(RequestRegister requestRegister) {
        ResponseRegister registerResponse = new ResponseRegister();
        EResponseRegister validateNameResult = validateName(requestRegister.getName());
        if (validateNameResult != null) {
            registerResponse.setResponse(validateNameResult);
        } else if (!validatePassword(requestRegister.getPassword())) {
            registerResponse.setResponse(EResponseRegister.INCORRECT_PASSWORD);
        } else if (!validateTalent(requestRegister.getTalentId())) {
            registerResponse.setResponse(EResponseRegister.TALENT_NOT_CHOOSE);
        } else if (!validateCards(requestRegister.getBaseCards())) {
            registerResponse.setResponse(EResponseRegister.INCORRECT_CARD_DIRECTIONS);
        } else if (!validateSpentPoints(requestRegister.getBaseCards())) {
            registerResponse.setResponse(EResponseRegister.INCORRECT_SPENT_POINTS);
        } else if (!validateEmail(requestRegister.getEmail())) {
            registerResponse.setResponse(EResponseRegister.INCORRECT_EMAIL);
        } else {
            registerResponse.setResponse(EResponseRegister.OK);
            createMaster(requestRegister);
        }

        requestRegister.getGameSession().sendPacket(registerResponse);
    }

    private boolean validateTalent(int masterTalent) {
        return masterTalent > 0;
    }

    private EResponseRegister validateName(String accountName) {
        int length = accountName.length();
        if (length < nameMinLength || length > nameMaxLength) {
            return EResponseRegister.INCORRECT_NAME_LENGTH;
        }

        AccountEntity account = getAccount(accountName);
        if (account != null && accountName.equals(account.getName())) {
            return EResponseRegister.ACCOUNT_ALREADY_EXISTS;
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

    private boolean validateEmail(String email) {
        return !email.isEmpty() && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    @EventListener
    public void onGameSessionInactive(GameSessionInactive event) {
        updateOnline(event.getGameSession().getAccount(), false);
        lobbyService.getObject().updateOnline();
        lobbyService.getObject().sendPlayerList();
    }
}
