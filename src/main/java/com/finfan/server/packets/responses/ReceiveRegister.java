package com.finfan.server.packets.responses;

import com.finfan.server.enums.ReceiveRegisterResponse;
import com.finfan.server.packets.PacketData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReceiveRegister extends PacketData {

    public static final int PACKET_ID = 0x04;

    private ReceiveRegisterResponse response;

    public ReceiveRegister() {
        this.packetId = PACKET_ID;
    }
}
