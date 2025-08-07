package com.finfan.server.packets.handlers;

import com.finfan.server.PacketDeserializer;
import com.finfan.server.entity.AccountEntity;
import com.finfan.server.enums.Direction;
import com.finfan.server.enums.ReceiveRegisterResponse;
import com.finfan.server.packets.requests.RequestRegister;
import com.finfan.server.packets.responses.ResponseRegister;
import com.finfan.server.service.AccountService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RequestRegisterHandler implements PacketDeserializer<ResponseRegister> {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final AccountService accountService;

    @Value("${game.base-card-points-price}")
    private int baseCardPointPrice;
    @Value("${game.base-card-points}")
    private int baseCardPoints;
    @Value("${game.name-min-length}")
    private int nameMinLength;
    @Value("${game.name-max-length}")
    private int nameMaxLength;
    @Value("${game.pswd-min-length}")
    private int pswdMinLength;
    @Value("${game.pswd-max-length}")
    private int pswdMaxLength;

    public ResponseRegister handle(RequestRegister requestRegister) {
        ResponseRegister registerResponse = new ResponseRegister();
        ReceiveRegisterResponse validateNameResult = validateName(requestRegister.getName());
        if (validateNameResult != null) {
            registerResponse.setResponse(validateNameResult);
        } else if (!validatePassword(requestRegister.getPassword())) {
            registerResponse.setResponse(ReceiveRegisterResponse.INCORRECT_PASSWORD);
        } else if (!validateTalent(requestRegister.getTalentId())) {
            registerResponse.setResponse(ReceiveRegisterResponse.TALENT_NOT_CHOOSE);
        } else if (!validateCards(requestRegister.getBaseCards())) {
            registerResponse.setResponse(ReceiveRegisterResponse.INCORRECT_CARD_DIRECTIONS);
        } else if (!validateSpentPoints(requestRegister.getBaseCards())) {
            registerResponse.setResponse(ReceiveRegisterResponse.INCORRECT_SPENT_POINTS);
        } else {
            registerResponse.setResponse(ReceiveRegisterResponse.OK);
            accountService.createMaster(requestRegister);
        }

        return registerResponse;
    }

    private boolean validateTalent(int masterTalent) {
        return masterTalent > 0;
    }

    private ReceiveRegisterResponse validateName(String accountName) {
        int length = accountName.length();
        if (length < nameMinLength || length > nameMaxLength) {
            return ReceiveRegisterResponse.INCORRECT_NAME_LENGTH;
        }

        AccountEntity account = accountService.getAccount(accountName);
        if (account != null && accountName.equals(account.getName())) {
            return ReceiveRegisterResponse.ACCOUNT_ALREADY_EXISTS;
        }

        return null;
    }

    private boolean validatePassword(String password) {
        int length = password.length();
        return length >= pswdMinLength && length <= pswdMaxLength;
    }

    private boolean validateCards(Map<Long, Integer> cards) {
        for (Integer mask : cards.values()) {
            if (mask == 0) {
                return false;
            }
        }
        return true;
    }

    private boolean validateSpentPoints(Map<Long, Integer> cards) {
        int spentPoints = 0;
        for (Integer arrowMask : cards.values()) {
            for (Direction dir : Direction.values()) {
                if ((dir.getMask() & arrowMask) != 0) {
                    spentPoints += baseCardPointPrice;
                }
            }
        }
        return spentPoints <= baseCardPoints;
    }

    @SuppressWarnings("unchecked")
    public RequestRegister deserialize(ChannelHandlerContext ctx, ByteBuf buffer) {
        RequestRegister in = new RequestRegister();
        in.setName(readString(buffer));
        in.setPassword(readString(buffer));
        in.setIpAddress(readString(buffer));
        in.setPortrait(buffer.readInt());
        in.setTalentId(buffer.readInt());

        // Чтение Dictionary<int, int>
        int dictSize = buffer.readInt();
        Map<Long, Integer> cards = new HashMap<>();
        for (int i = 0; i < dictSize; i++) {
            cards.put(buffer.readLong(), buffer.readInt());
        }
        in.setBaseCards(cards);
        return in;
    }

    @Override
    public void serializeToResponse(ResponseRegister data, ByteBuf out) {
        ByteBuf tempBuf = Unpooled.buffer();
        tempBuf.writeInt(data.getPacketId());
        tempBuf.writeInt(data.getResponse().ordinal());
        int length = tempBuf.writerIndex();

        out.writeInt(length);
        out.writeBytes(tempBuf);
    }

    @Override
    public int getPacketId() {
        return RequestRegister.PACKET_ID;
    }

}
