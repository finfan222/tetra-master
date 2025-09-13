package com.finfan.server.network.packets.serder.serializer;

import com.finfan.server.entity.projections.CardListProjection;
import com.finfan.server.network.packets.dto.outcoming.ResponsePlayerCardList;
import com.finfan.server.network.packets.serder.PacketSerializer;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

/**
 * 	var size: int = deserializer.read_int()
 * 	for i in range(size):
 * 		var card_id: int = deserializer.read_long()
 * 		var card_res: CardRes = Cards.get_card_res(card_id)
 * 		card_res.unique_id = deserializer.read_long()
 * 		card_res.atk = deserializer.read_int()
 * 		card_res.atk_type = deserializer.read_int()
 * 		card_res.p_def = deserializer.read_int()
 * 		card_res.m_def = deserializer.read_int()
 * 		card_res.atk_arrows = deserializer.read_int()
 * 		card_res.base = deserializer.read_bool()
 */
@Component
public class ResponsePlayerCardListSerializer implements PacketSerializer<ResponsePlayerCardList> {

    @Override
    public void serialize(ResponsePlayerCardList data, ByteBuf out) {
        out.writeInt(data.getPacketId());
        out.writeInt(data.getCards().size());
        for (CardListProjection card : data.getCards()) {
            out.writeLong(card.getCardId());
            out.writeLong(card.getId());
            out.writeInt(card.getAtk());
            out.writeInt(card.getAtkType().ordinal());
            out.writeInt(card.getPDef());
            out.writeInt(card.getMDef());
            out.writeInt(card.getAtkArrows());
            out.writeBoolean(card.getBase());
            if (card.getBuild() != null) {
                out.writeByte(card.getBuild().ordinal());
            } else {
                out.writeByte(-1);
            }
        }
    }

}
