package net.minecraft.src.EurysMods.core;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet;
import net.minecraft.src.World;
import net.minecraft.src.forge.NetworkMod;

public interface IProxy {
	public boolean isClient = false;

	public void sendPacket(EntityPlayer entityplayer, Packet packet);

	public void sendPacketToAll(Packet packet, int x, int y, int z,
			int maxDistance, NetworkMod mod);

	public World getWorld(NetworkManager network);

	public EntityPlayer getPlayer(NetworkManager network);
}