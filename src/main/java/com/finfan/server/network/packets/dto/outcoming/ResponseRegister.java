package com.finfan.server.network.packets.dto.outcoming;

import com.finfan.server.enums.responses.EResponseRegister;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResponseRegister extends AbstractOutcomePacket {

    public static final int PACKET_ID = 0x04;

    private EResponseRegister response;

    public ResponseRegister() {
        this.packetId = PACKET_ID;
    }

}
