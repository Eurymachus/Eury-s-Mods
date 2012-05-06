package net.minecraft.src.MultiTexturedButtons.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedButtons.MultiTexturedButtons;
import net.minecraft.src.forge.packets.ForgePacket;

public abstract class PacketMTB extends PacketUpdate
{
    public PacketMTB(int packetId)
    {
    	super(packetId);
        this.channel = MultiTexturedButtons.Core.getModChannel();
    }
}
