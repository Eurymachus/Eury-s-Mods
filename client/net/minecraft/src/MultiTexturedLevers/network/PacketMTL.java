package net.minecraft.src.MultiTexturedLevers.network;

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
