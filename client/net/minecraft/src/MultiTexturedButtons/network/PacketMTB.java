package net.minecraft.src.MultiTexturedButtons.network;

import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedButtons.MultiTexturedButtons;

public class PacketMTB extends PacketUpdate
{
    public PacketMTB(int packetId)
    {
    	super(packetId);
        this.channel = MultiTexturedButtons.MTBCore.getModChannel();
    }
}
