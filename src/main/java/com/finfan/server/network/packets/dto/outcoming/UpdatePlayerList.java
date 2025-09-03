package com.finfan.server.network.packets.dto.outcoming;

import com.finfan.server.enums.responses.EResponseRegister;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdatePlayerList extends AbstractOutcomePacket {

    public static final int PACKET_ID = 0x07;

    private EResponseRegister response;

    public UpdatePlayerList() {
        this.packetId = PACKET_ID;
    }

}
