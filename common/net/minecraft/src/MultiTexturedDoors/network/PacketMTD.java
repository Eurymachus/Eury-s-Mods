package net.minecraft.src.MultiTexturedDoors.network;

import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedDoors.MultiTexturedDoors;

public class PacketMTD extends PacketUpdate
{
    public PacketMTD(int packetId)
    {
    	super(packetId);
        this.channel = MultiTexturedDoors.MTDCore.getModChannel();
    }
}
