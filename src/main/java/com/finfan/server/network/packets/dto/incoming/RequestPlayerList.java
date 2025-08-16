package com.finfan.server.network.packets.dto.incoming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class RequestPlayerList extends AbstractIncomePacket {

    public static final int PACKET_ID = 0x05;

    private int listPage;

    public RequestPlayerList() {
        packetId = PACKET_ID;
    }

}
