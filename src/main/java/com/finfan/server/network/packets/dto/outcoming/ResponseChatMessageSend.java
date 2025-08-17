package com.finfan.server.network.packets.dto.outcoming;

import com.finfan.server.enums.ChatMsgColor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Packet struct:<br>
 * {@link String} player_name<br>
 * {@link String} player_message<br>
 * {@link Integer} color<br>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class ResponseChatMessageSend extends AbstractOutcomePacket {

    public static final int PACKET_ID = 0x0A;

    private String playerName;
    private String playerMessage;
    private ChatMsgColor color;

    public ResponseChatMessageSend() {
        packetId = PACKET_ID;
    }

}
