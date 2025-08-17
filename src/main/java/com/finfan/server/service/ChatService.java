package com.finfan.server.service;

import com.finfan.server.entity.AccountEntity;
import com.finfan.server.enums.ChatMsgColor;
import com.finfan.server.enums.ChatType;
import com.finfan.server.network.GameSession;
import com.finfan.server.network.GameSessionRegistry;
import com.finfan.server.network.packets.dto.incoming.RequestChatMessageSend;
import com.finfan.server.network.packets.dto.outcoming.ResponseChatMessageSend;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ChatService {

    @Data
    @AllArgsConstructor
    public static class ChatMessage {
        private final String playerName;
        private final String playerMessage;
        private final ChatMsgColor messageColor;
    }

    private final Map<ChatType, List<ChatMessage>> history = new ConcurrentHashMap<>();

    private final AccountService accountService;
    private final GameSessionRegistry gameSessionRegistry;

    @Value("${game.chat-max-message-length}")
    private int chatMaxMessageLength;
    @Value("${game.chat-max-history-messages}")
    private int chatMaxHistoryMessages;

    public ChatService(AccountService accountService, GameSessionRegistry gameSessionRegistry) {
        this.accountService = accountService;
        this.gameSessionRegistry = gameSessionRegistry;
    }

    @PostConstruct
    private void postConstruct() {
        Arrays.stream(ChatType.values()).forEach(type -> history.put(type, new ArrayList<>()));
    }

    public synchronized void addChatMessage(ChatMessage chatMessage) {
        // fixme: не эффективная коллекция, лучше использовать LinkedHashMap с переопределением removeEldestEntry()
        history.get(ChatType.DEFAULT).add(chatMessage);
        log.debug("[+] Добавление нового сообщения чата: {}", chatMessage);
        removeChatMessage();
    }

    private void removeChatMessage() {
        if (history.size() >= chatMaxMessageLength) {
            ChatMessage chatMessage = history.get(ChatType.DEFAULT).removeFirst();
            log.debug("[-] Удаление последнего сообщения чата: {}", chatMessage);
        }
    }

    private void trySendMessage(GameSession gameSession, String name, String message) {
        ResponseChatMessageSend responseChatMessageSend = new ResponseChatMessageSend();
        responseChatMessageSend.setPlayerName(name);
        responseChatMessageSend.setPlayerMessage(message);
        responseChatMessageSend.setColor(ChatMsgColor.NORMAL);
        gameSessionRegistry.multicast(responseChatMessageSend); // TODO: чат для админов делает мультикаст только админам
        addChatMessage(new ChatMessage(name, message, ChatMsgColor.NORMAL));
    }

    @EventListener
    public void onRequestChatMessageSend(RequestChatMessageSend event) {
        long playerId = event.getPlayerId();
        AccountEntity account = accountService.getAccount(playerId);
        AccountEntity senderAccount = event.getGameSession().getAccount();
        if (!Objects.equals(senderAccount.getId(), account.getId())) {
            log.warn("Попытка странного хака, аккаунт который использовался для отсылки сообщения в чат не совпадает с аккаунтом, который реально это делал!");
            log.warn("(!) Аккаунт отправки: {}", senderAccount);
            log.warn("(!) Аккаунт который получен по ID: {}", account);
            return;
        }

        String playerMessage = event.getPlayerMessage();
        if (playerMessage.isBlank()) {
            return;
        }

        if (playerMessage.length() > chatMaxMessageLength) {
            playerMessage = playerMessage.substring(0, chatMaxMessageLength - 1) + "...";
        }

        trySendMessage(event.getGameSession(), account.getName(), playerMessage);
    }

}
