package com.finfan.server.service;

import com.finfan.server.entity.AccountEntity;
import com.finfan.server.entity.ProfileEntity;
import com.finfan.server.enums.DuelCategory;
import com.finfan.server.enums.DuelOpponent;
import com.finfan.server.network.packets.dto.incoming.RequestDuelRegister;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Класс отвечающий за нахождение оппонентов во время прихода DuelFinderStart пакета.<br>
 * Игрок отправляет:<br>
 * Тип Поиска - искать среди всех или искать среди своих ±1 ранг<br>
 * Тип Дуэли - обычная дуэль или рейтинговая дуэль
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DuelFinderService {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchOpponentDto implements Comparable<Long> {
        private DuelOpponent duelOpponent;
        private DuelCategory duelCategory;
        private long registerTime;
        private String playerName;
        @ToString.Exclude
        private ProfileEntity profile;

        @Override
        public int compareTo(@NonNull Long right) {
            return Long.compare(registerTime, right);
        }
    }

    private final Map<DuelCategory, Map<DuelOpponent, Set<SearchOpponentDto>>> participants = new HashMap<>();

    @Value("${game.card-duel.finder.max-await-time}")
    private int maxAwaitTime;

    @Scheduled(cron = "*/30 * * * * *") // TODO: в конфигурацию каждые 30 сек.
    public synchronized void createOpponentLists() {
        List<Pair<ProfileEntity, ProfileEntity>> pairs;
        for (DuelCategory category : DuelCategory.values()) {
            Map<DuelOpponent, Set<SearchOpponentDto>> opponents = participants.get(category);
            for (DuelOpponent opponent : DuelOpponent.values()) {
                List<SearchOpponentDto> playersA = opponents.get(opponent).stream().toList();
                List<SearchOpponentDto> playersB = null;
                switch (opponent) {
                    case DEFAULT -> {
                        playersB = new ArrayList<>(opponents.get(DuelOpponent.DEFAULT));
                        pairs = createUniquePairs(playersA, playersB);
                        //FIXME: ГОВНО КОНЧЕННОЕ
                    }
                    case BALANCED -> {

                    }
                }
            }
        }
    }

    // Метод для создания неповторяющихся пар
    private List<Pair<ProfileEntity, ProfileEntity>> createUniquePairs(List<SearchOpponentDto> list1,
                                                                               List<SearchOpponentDto> list2) {

        List<Pair<ProfileEntity, ProfileEntity>> pairs = new ArrayList<>();
        for (int i = 0; i < Math.min(list1.size(), list2.size()); i++) {
            // Исключаем пару с самим собой, если это один и тот же список
            if (list1 == list2 && i == 0) {
                continue;
            }
            pairs.add(Pair.of(list1.get(i).getProfile(), list2.get(i).getProfile()));
        }

        return pairs;
    }

    @PostConstruct
    private void initialize() {
        Arrays.stream(DuelCategory.values()).forEach(cat -> {
            participants.put(cat, Map.of(
                    DuelOpponent.DEFAULT, Collections.synchronizedSet(new HashSet<>()),
                    DuelOpponent.BALANCED, Collections.synchronizedSet(new HashSet<>()),
                    DuelOpponent.HIGHEST, Collections.synchronizedSet(new HashSet<>()),
                    DuelOpponent.LOWEST, Collections.synchronizedSet(new HashSet<>())
            ));
        });
    }

    @EventListener
    protected void onRequestDuelRegister(RequestDuelRegister event) {
        DuelOpponent duelOpponent = event.getDuelOpponent();
        DuelCategory duelCategory = event.getDuelCategory();
        long registerTime = event.getRegisterTime();

        AccountEntity account = event.getGameSession().getAccount();
        ProfileEntity profile = account.getProfile();

        participants.get(duelCategory).get(duelOpponent).add(SearchOpponentDto.builder()
                .duelCategory(duelCategory)
                .duelOpponent(duelOpponent)
                .registerTime(registerTime)
                .playerName(account.getName())
                .profile(profile)
                .build());
        // TODO: смена статуса
    }

}
