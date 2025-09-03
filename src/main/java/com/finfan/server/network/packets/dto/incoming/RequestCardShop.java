package com.finfan.server.network.packets.dto.incoming;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestCardShop extends AbstractIncomePacket {

    public static final int PACKET_ID = 0x0B;

    public RequestCardShop() {
        packetId = PACKET_ID;
    }

}
