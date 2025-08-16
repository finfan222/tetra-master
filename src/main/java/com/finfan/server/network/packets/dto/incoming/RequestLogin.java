package com.finfan.server.network.packets.dto.incoming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class RequestLogin extends AbstractIncomePacket {

    public static final int PACKET_ID = 0x01;

    private String account;
    private String password;
    private String ipAddress;
    private LocalDateTime lastAccess;

    public RequestLogin() {
        packetId = PACKET_ID;
    }

}
