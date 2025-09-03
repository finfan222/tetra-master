package com.finfan.server.network.packets.dto.incoming;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestOnline extends AbstractIncomePacket {

    public static final int PACKET_ID = 0x11;

    public RequestOnline() {
        packetId = PACKET_ID;
    }

}
