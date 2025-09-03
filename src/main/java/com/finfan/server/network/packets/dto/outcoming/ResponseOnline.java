package com.finfan.server.network.packets.dto.outcoming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class ResponseOnline extends AbstractOutcomePacket {

    public static final int PACKET_ID = 0x12;

    private int playerCount;

    public ResponseOnline() {
        packetId = PACKET_ID;
    }

}
