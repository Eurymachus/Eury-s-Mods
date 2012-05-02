package net.minecraft.src.EurysMods.core;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.forge.NetworkMod;

public interface IProxy
{
	public boolean isClient(World world);
	public boolean isServer(World world);
	public boolean isSingleplayer(World world);
	public PacketPayload getTileEntityPacket(TileEntity te, int[] dataInt, float[] dataFloat, String[] dataString);
	PacketPayload getTileEntityPayload(int[] dataInt, float[] dataFloat,
			String[] dataString);
	void sendPacket(EntityPlayer entityplayer, Packet packet);
	void sendPacketToAll(Packet packet, int x, int y, int z, int maxDistance,
			NetworkMod mod);
}