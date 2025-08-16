package com.finfan.server.network.packets.dto.incoming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class RequestPlayerInfo extends AbstractIncomePacket {

    public static final int PACKET_ID = 0x07;

    private long playerId;

    public RequestPlayerInfo() {
        packetId = PACKET_ID;
    }

}
