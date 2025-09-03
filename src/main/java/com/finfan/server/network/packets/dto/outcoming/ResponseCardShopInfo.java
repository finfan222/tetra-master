package com.finfan.server.network.packets.dto.outcoming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class ResponseCardShopInfo extends AbstractOutcomePacket {

    public static final int PACKET_ID = 0x0D;

    private long myGil;
    private boolean cardInStock;
    private int cardCount;
    private boolean cardIsUnique;

    public ResponseCardShopInfo() {
        packetId = PACKET_ID;
    }

}
