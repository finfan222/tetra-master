package com.finfan.server.network.packets.dto.incoming;

import com.finfan.server.enums.DuelCategory;
import com.finfan.server.enums.DuelOpponent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class RequestDuelRegister extends AbstractIncomePacket {

    public static final int PACKET_ID = 0x19;

    private DuelOpponent duelOpponent;
    private DuelCategory duelCategory;

    public RequestDuelRegister() {
        packetId = PACKET_ID;
    }

}
