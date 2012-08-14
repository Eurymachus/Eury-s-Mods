package net.minecraft.src.PaintingChooser.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketUpdate;

public interface IPaintingPacketHandling extends IPacketHandling {
	public void handlePacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world);
}
