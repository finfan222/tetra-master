package com.finfan.server.packets.handlers;

import com.finfan.server.PacketDeserializer;
import com.finfan.server.entity.AccountEntity;
import com.finfan.server.enums.Permission;
import com.finfan.server.enums.ReceiveLoginResponse;
import com.finfan.server.packets.requests.RequestLogin;
import com.finfan.server.packets.responses.ResponseLogin;
import com.finfan.server.service.AccountService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RequestLoginHandler implements PacketDeserializer<ResponseLogin> {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final AccountService accountService;

    public ResponseLogin handle(RequestLogin requestLogin) {
        ResponseLogin responseLogin = new ResponseLogin();
        String acc = requestLogin.getAccount();
        AccountEntity account = accountService.getAccount(acc);
        if (account == null) {
            responseLogin.setResponse(ReceiveLoginResponse.INCORRECT_ACCOUNT_NAME_OR_PASSWORD);
        } else {
            if (account.getPermission() == Permission.BANNED) {
                responseLogin.setResponse(ReceiveLoginResponse.BANNED);
            } else {
                String pass = requestLogin.getPassword();
                String inputPassword = accountService.createSalt(acc, pass);
                String serverPassword = account.getPassword();
                if (!inputPassword.equals(serverPassword)) {
                    responseLogin.setResponse(ReceiveLoginResponse.INCORRECT_ACCOUNT_NAME_OR_PASSWORD);
                } else {
                    responseLogin.setResponse(ReceiveLoginResponse.OK);
                }
            }
        }

        return responseLogin;
    }

    @SuppressWarnings("unchecked")
    public RequestLogin deserialize(ChannelHandlerContext ctx, ByteBuf buffer) {
        RequestLogin in = new RequestLogin();
        in.setAccount(readString(buffer));
        in.setPassword(readString(buffer));
        in.setIpAddress(readString(buffer));
        in.setLastAccess(LocalDateTime.parse(readString(buffer)));
        return in;
    }

    @Override
    public void serializeToResponse(ResponseLogin data, ByteBuf out) {
        ByteBuf tempBuf = Unpooled.buffer();
        tempBuf.writeInt(data.getPacketId());
        tempBuf.writeInt(data.getResponse().ordinal());
        int length = tempBuf.writerIndex();

        out.writeInt(length);
        out.writeBytes(tempBuf);
    }

    @Override
    public int getPacketId() {
        return RequestLogin.PACKET_ID;
    }

}
