package com.finfan.server.network.packets.dto.incoming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class RequestRegister extends AbstractIncomePacket {

    public static final int PACKET_ID = 0x03;

    private String name;
    private String password;
    private String ipAddress;
    private int portrait;
    private int talentId;
    private Map<Long, Integer> baseCards;
    private String email;

    public RequestRegister() {
        packetId = PACKET_ID;
    }

}
