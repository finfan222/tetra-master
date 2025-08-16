package com.finfan.server.network.packets.dto.incoming;

import com.finfan.server.network.GameSession;
import com.finfan.server.network.packets.dto.PacketData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AbstractIncomePacket extends PacketData {

    private GameSession gameSession;

}
