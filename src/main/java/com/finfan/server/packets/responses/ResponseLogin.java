package com.finfan.server.packets.responses;

import com.finfan.server.enums.ReceiveLoginResponse;
import com.finfan.server.packets.PacketData;
import com.finfan.server.packets.requests.RequestLogin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class ResponseLogin extends PacketData {

    public static final int PACKET_ID = 0x02;

    private ReceiveLoginResponse response;

    public ResponseLogin() {
        packetId = PACKET_ID;
    }

    @Override
    public int getRequestPacketId() {
        return RequestLogin.PACKET_ID;
    }
}
