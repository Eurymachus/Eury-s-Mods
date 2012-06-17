package net.minecraft.src.EurysMods.network;

import net.minecraft.src.EntityPlayer;

public interface IPacketHandling {
	public void handleTileEntityPacket(PacketUpdate packet, EntityPlayer var2);

	public void handleGuiPacket(PacketUpdate packet, EntityPlayer var2);
}