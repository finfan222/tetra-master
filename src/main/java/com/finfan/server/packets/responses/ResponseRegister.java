package com.finfan.server.packets.responses;

import com.finfan.server.enums.ReceiveRegisterResponse;
import com.finfan.server.packets.PacketData;
import com.finfan.server.packets.requests.RequestRegister;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResponseRegister extends PacketData {

    public static final int PACKET_ID = 0x04;

    private ReceiveRegisterResponse response;

    public ResponseRegister() {
        this.packetId = PACKET_ID;
    }

    public int getRequestPacketId() {
        return RequestRegister.PACKET_ID;
    }

}
