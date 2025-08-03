package com.finfan.server.service;

import com.finfan.server.entity.AccountEntity;
import com.finfan.server.entity.CardEntity;
import com.finfan.server.entity.ProfileEntity;
import com.finfan.server.enums.CollectorRank;
import com.finfan.server.enums.Permission;
import com.finfan.server.enums.Rank;
import com.finfan.server.repository.AccountRepository;
import com.finfan.server.enums.Portrait;
import com.finfan.server.packets.requests.RequestRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
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

    private final ApplicationEventPublisher applicationEventPublisher;
    private final AccountRepository accountRepository;
    private final TalentService talentService;
    private final CardService cardService;
    private final CardTemplateService cardTemplateService;
    private final ProfileService profileService;

    @Value("${game.base-gil}")
    private long baseGil;

    public List<AccountEntity> getAccounts() {
        return accountRepository.findAll();
    }

    public AccountEntity getAccount(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public AccountEntity getAccount(String name) {
        return accountRepository.findByName(name).orElse(null);
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
        System.out.println("Master " + account + " was created!");

        ProfileEntity profile = new ProfileEntity();
        profile.setPortrait(Portrait.values()[requestRegister.getPortrait()]);
        profile.setGil(baseGil);
        profile.setWins(0);
        profile.setLosses(0);
        profile.setRating(0);
        profile.setRank(Rank.NOVICE);
        profile.setCollectorRank(CollectorRank.NOVICE);
        profile.setTalent(talentService.getTalent(requestRegister.getTalentId()));
        profile.setAccount(account);
        profile.setCards(cardService.createBaseCards(requestRegister.getBaseCards(), profile));
        System.out.println("Profile " + profile + " was created!");

        account.setProfile(profile);
        save(account);
    }

    private boolean validatePassword(AccountEntity account, String password) {
        String clientPassword = createSalt(account.getName(), password);
        String serverPassword = account.getPassword();
        return serverPassword.equals(clientPassword);
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

}
