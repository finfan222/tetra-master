package com.finfan.server.network.packets.dto.outcoming;

import com.finfan.server.entity.CardEntity;
import com.finfan.server.enums.responses.EResponseCardShopBuy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class ResponseCardShopBuy extends AbstractOutcomePacket {

    public static final int PACKET_ID = 0x10;

    private EResponseCardShopBuy responseMessage;
    private CardEntity card;

    public ResponseCardShopBuy() {
        packetId = PACKET_ID;
    }

}
