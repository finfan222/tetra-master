package com.finfan.server.network.packets.dto.incoming;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestPlayerCardList extends AbstractIncomePacket {

    public static final int PACKET_ID = 0x17;

    public RequestPlayerCardList() {
        packetId = PACKET_ID;
    }

}
