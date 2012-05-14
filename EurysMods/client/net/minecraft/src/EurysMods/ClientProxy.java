package net.minecraft.src.EurysMods;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_EurysMods;
import net.minecraft.src.EurysMods.core.IProxy;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.NetworkMod;

public class ClientProxy implements IProxy
{
	
	@Override
	public void sendPacket(EntityPlayer entityplayer, Packet packet)
	{
		ModLoader.getMinecraftInstance().getSendQueue().addToSendQueue(packet);
	}

	@Override
	public void sendPacketToAll(Packet packet, int x, int y, int z,	int maxDistance, NetworkMod mod)
	{
	}
}