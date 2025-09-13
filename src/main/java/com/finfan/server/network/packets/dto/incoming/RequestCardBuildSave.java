package com.finfan.server.network.packets.dto.incoming;

import com.finfan.server.entity.dto.CardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * serializer.write_int(cards.size())
 * for card: CardRes in cards:
     * serializer.write_long(card.unique_id)
     * serializer.write_long(card.id)
     * serializer.write_int(card.atk)
     * serializer.write_int(card.atk_type)
     * serializer.write_int(card.p_def)
     * serializer.write_int(card.m_def)
     * serializer.write_bool(card.base)
     * serializer.write_int(card.build_type)
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class RequestCardBuildSave extends AbstractIncomePacket {

    public static final int PACKET_ID = 0x13;

    private byte build;
    private List<CardDto> cards;

    public RequestCardBuildSave() {
        packetId = PACKET_ID;
    }

}
