package com.finfan.server.service;

import com.finfan.server.enums.DuelCategory;
import com.finfan.server.enums.DuelOpponent;
import com.finfan.server.enums.Rank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        private long registerTime;
        private long registerId;
        private DuelOpponent registerCategory;
        private DuelCategory registerType;
        private Rank rank;

        @Override
        public int compareTo(@NonNull Long right) {
            return Long.compare(registerTime, right);
        }
    }

    private final Map<DuelCategory, Set<SearchOpponentDto>> participants = new HashMap<>();

    @Value("${game.card-duel.finder.max-await-time}")
    private int maxAwaitTime;

    @Scheduled(cron = "*/5 * * * * *") // TODO: в конфигурацию каждые 5 сек.
    public void createOpponentLists() {
    }

    /*@EventListener
    public void onRequestDuelFind(RequestDuelFind event) {

        // TODO: смена статуса
    }*/

}
