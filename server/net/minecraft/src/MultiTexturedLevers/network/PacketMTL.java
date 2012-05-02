package net.minecraft.src.MultiTexturedLevers.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedLevers.MultiTexturedLevers;

public class PacketMTL extends PacketUpdate
{
    public PacketMTL(int packetId)
    {
    	super(packetId);
        this.channel = MultiTexturedLevers.MTLCore.getModChannel();
    }
}
