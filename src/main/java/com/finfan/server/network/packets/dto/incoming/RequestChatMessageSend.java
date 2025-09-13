package com.finfan.server.network.packets.dto.incoming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Packet struct:<br>
 * write_long(player_id)<br>
 * write_string(player_message)<br>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class RequestChatMessageSend extends AbstractIncomePacket {

    public static final int PACKET_ID = 0x09;

    private String playerMessage;

    public RequestChatMessageSend() {
        packetId = PACKET_ID;
    }

}
