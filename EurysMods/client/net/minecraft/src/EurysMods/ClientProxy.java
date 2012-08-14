package net.minecraft.src.EurysMods;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.core.IProxy;
import net.minecraft.src.forge.NetworkMod;

public class ClientProxy implements IProxy {
	public boolean isClient = true;

	@Override
	public void sendPacket(EntityPlayer entityplayer, Packet packet) {
		ModLoader.getMinecraftInstance().getSendQueue().addToSendQueue(packet);
	}

	@Override
	public void sendPacketToAll(Packet packet, int x, int y, int z,
			int maxDistance, NetworkMod mod) {
	}

	@Override
	public World getWorld(NetworkManager network) {
		return ModLoader.getMinecraftInstance().theWorld;
	}

	@Override
	public EntityPlayer getPlayer(NetworkManager network) {
		return ModLoader.getMinecraftInstance().thePlayer;
	}
}