package com.finfan.server.network.packets.dto.outcoming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * ClientData:<br>
 * player.player_id = temp_buffer.read_long()<br>
 * player.player_name = temp_buffer.read_string()<br>
 * player.player_rating = temp_buffer.read_int()<br>
 * player.player_gil = temp_buffer.read_long()<br>
 * player.player_win = temp_buffer.read_int()<br>
 * player.player_loss = temp_buffer.read_int()<br>
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
    private long gil;
    private int win;
    private int loss;

    public ResponsePlayerInfo() {
        packetId = PACKET_ID;
    }

}
