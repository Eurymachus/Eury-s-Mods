package net.minecraft.src.MultiTexturedSigns.network;

import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedSigns.MultiTexturedSigns;

public class PacketMTS extends PacketUpdate
{
    public PacketMTS(int packetId)
    {
    	super(packetId);
    	this.channel = MultiTexturedSigns.MTS.getModChannel();
    }
}
