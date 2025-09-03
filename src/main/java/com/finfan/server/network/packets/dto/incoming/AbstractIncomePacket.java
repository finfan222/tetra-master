package com.finfan.server.network.packets.dto.incoming;

import com.finfan.server.network.GameSession;
import com.finfan.server.network.packets.IgnorableByPacket;
import com.finfan.server.network.packets.dto.PacketData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
public class AbstractIncomePacket extends PacketData {

    @IgnorableByPacket
    @ToString.Exclude
    private GameSession gameSession;

}
