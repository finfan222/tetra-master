package com.finfan.server.network.packets.dto.incoming;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestCardShopBuy extends AbstractIncomePacket {

    public static final int PACKET_ID = 0x0F;

    private long shopCardId;

    public RequestCardShopBuy() {
        packetId = PACKET_ID;
    }

}
