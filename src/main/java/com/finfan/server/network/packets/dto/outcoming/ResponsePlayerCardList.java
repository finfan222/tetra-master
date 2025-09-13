package com.finfan.server.network.packets.dto.outcoming;

import com.finfan.server.entity.projections.CardListProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * int[long,long,int,int,int,int,int]
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class ResponsePlayerCardList extends AbstractOutcomePacket {

    public static final int PACKET_ID = 0x18;

    private List<CardListProjection> cards;

    public ResponsePlayerCardList() {
        packetId = PACKET_ID;
    }

}
