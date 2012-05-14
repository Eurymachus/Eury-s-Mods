package net.minecraft.src.MultiTexturedPPlates.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedPPlates.MultiTexturedPPlates;

public class PacketMTP extends PacketUpdate
{
    public PacketMTP(int packetId)
    {
    	super(packetId);
        this.channel = MultiTexturedPPlates.Core.getModChannel();
    }
}
