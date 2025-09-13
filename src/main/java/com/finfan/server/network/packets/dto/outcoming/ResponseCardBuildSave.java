package com.finfan.server.network.packets.dto.outcoming;

import com.finfan.server.entity.dto.CardDto;
import com.finfan.server.enums.responses.EResponseCardBuildSave;
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
public class ResponseCardBuildSave extends AbstractOutcomePacket {

    public static final int PACKET_ID = 0x14;

    private EResponseCardBuildSave response;
    private List<CardDto> modifiedCards;

    public ResponseCardBuildSave() {
        packetId = PACKET_ID;
    }

}
