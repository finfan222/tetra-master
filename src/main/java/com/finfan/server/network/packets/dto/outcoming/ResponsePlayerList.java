package com.finfan.server.network.packets.dto.outcoming;

import com.finfan.server.entity.ProfileEntity;
import com.finfan.server.network.packets.dto.incoming.RequestPlayerList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResponsePlayerList extends AbstractOutcomePacket {

    public static final int PACKET_ID = 0x06;

    private List<ProfileEntity> profiles;

    public ResponsePlayerList() {
        this.packetId = PACKET_ID;
    }

    public int getRequestPacketId() {
        return RequestPlayerList.PACKET_ID;
    }

}
