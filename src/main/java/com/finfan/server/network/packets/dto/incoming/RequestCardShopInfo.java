package com.finfan.server.network.packets.dto.incoming;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestCardShopInfo extends AbstractIncomePacket {

    public static final int PACKET_ID = 0x0E;

    private long cardId;
    private int atkArrows;

    public RequestCardShopInfo() {
        packetId = PACKET_ID;
    }

}
