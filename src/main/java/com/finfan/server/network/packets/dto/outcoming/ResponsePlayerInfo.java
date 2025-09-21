package com.finfan.server.network.packets.dto.outcoming;

import com.finfan.server.enums.Portrait;
import com.finfan.server.enums.Rank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * ClientData:<br>
 * player.player_id = deserializer.read_long()
 * player.player_name = deserializer.read_string()
 * player.player_rating = deserializer.read_int()
 * player.player_rank = Enum.Rank.values()[deserializer.read_int()]
 * player.player_gil = deserializer.read_long()
 * player.player_win = deserializer.read_int()
 * player.player_draw = deserializer.read_int()
 * player.player_loss = deserializer.read_int()
 * player.player_portrait = Enum.Portrait.values()[deserializer.read_int()]
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class ResponsePlayerInfo extends AbstractOutcomePacket {

    public static final int PACKET_ID = 0x08;

    private long id;
    private String name;
    private int rating;
    private Rank rank;
    private long gil;
    private int win;
    private int loss;
    private int draw;
    private Portrait portrait;

    public ResponsePlayerInfo() {
        packetId = PACKET_ID;
    }

}
