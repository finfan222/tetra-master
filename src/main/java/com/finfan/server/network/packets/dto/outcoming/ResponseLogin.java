package com.finfan.server.network.packets.dto.outcoming;

import com.finfan.server.enums.ReceiveLoginResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class ResponseLogin extends AbstractOutcomePacket {

    public static final int PACKET_ID = 0x02;

    private ReceiveLoginResponse response;
    private long id;

    public ResponseLogin() {
        packetId = PACKET_ID;
    }

}
