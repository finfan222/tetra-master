package com.finfan.server.network.packets.dto.outcoming;

import com.finfan.server.entity.CardShopEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class ResponseCardShop extends AbstractOutcomePacket {

    public static final int PACKET_ID = 0x0C;

    private LocalDate nextUpdateDate;
    private List<CardShopEntity> shopCards;

    public ResponseCardShop() {
        packetId = PACKET_ID;
    }

    //TODO: RARITY INSERT???

}
