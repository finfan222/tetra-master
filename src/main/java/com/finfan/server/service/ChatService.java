package com.finfan.server.service;

import com.finfan.server.entity.AccountEntity;
import com.finfan.server.entity.ProfileEntity;
import com.finfan.server.enums.ChatMsgColor;
import com.finfan.server.enums.ChatType;
import com.finfan.server.enums.Permission;
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

    private final GameSessionRegistry gameSessionRegistry;
    private final ProfileService profileService;

    @Value("${game.chat-max-message-length}")
    private int chatMaxMessageLength;
    @Value("${game.chat-max-history-messages}")
    private int chatMaxHistoryMessages;

    public ChatService(GameSessionRegistry gameSessionRegistry, ProfileService profileService) {
        this.gameSessionRegistry = gameSessionRegistry;
        this.profileService = profileService;
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

    private void sendChatMessage(GameSession gameSession, String name, String message) {
        // FIXME: сделать систему команд по Account.getPermission()
        ResponseChatMessageSend responseChatMessageSend = new ResponseChatMessageSend();
        responseChatMessageSend.setPlayerName(name);
        responseChatMessageSend.setPlayerMessage(message);
        if (message.startsWith("/gil")) {
            AccountEntity account = gameSession.getAccount();
            if (account.getPermission() == Permission.ADMIN) {
                ProfileEntity profile = account.getProfile();
                String[] split = message.split(" ");
                if (split.length == 2) {
                    String str = split[1];
                    if (str.length() > 10) {
                        str = str.substring(0, 10);
                    }
                    int gil = Integer.parseInt(str);
                    profileService.addGil(profile, gil);
                    log.debug("Добавлено {} гилей для профиля {}!", gil, account.getName());
                    responseChatMessageSend.setColor(ChatMsgColor.ADMIN);
                    gameSession.sendPacket(responseChatMessageSend);
                }
            }
        } else {
            responseChatMessageSend.setColor(ChatMsgColor.NORMAL);
            gameSessionRegistry.multicast(responseChatMessageSend);
        }
        addChatMessage(new ChatMessage(name, message, responseChatMessageSend.getColor()));
    }

    @EventListener
    public void onRequestChatMessageSend(RequestChatMessageSend event) {
        AccountEntity account = event.getGameSession().getAccount();
        String playerMessage = event.getPlayerMessage();
        if (playerMessage.isBlank()) {
            return;
        }

        if (playerMessage.length() > chatMaxMessageLength) {
            playerMessage = playerMessage.substring(0, chatMaxMessageLength - 1) + "...";
        }

        sendChatMessage(event.getGameSession(), account.getName(), playerMessage);
    }

}
