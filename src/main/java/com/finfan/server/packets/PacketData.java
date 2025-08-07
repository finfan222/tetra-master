package com.finfan.server.packets;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class PacketData implements Serializable {

    private static final long serialVersionUID = 1L;

    protected int packetId;

    public PacketData() {
        this.packetId = 0x00;
    }

    public int getRequestPacketId() {
        return -1;
    }
}