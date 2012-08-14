package net.minecraft.src.Elevators.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketTileEntity;
import net.minecraft.src.EurysMods.network.PacketUpdate;

public interface ElevatorsPacketHandling extends IPacketHandling {

	@Override
	public void handleTileEntityPacket(PacketTileEntity packet,
			EntityPlayer entityplayer, World world);

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world);

	public boolean sendResponseGUIPacket(int guiTransferID, int packetCode,
			String[] floors, boolean[] properties);

	public void handlePacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world);
}
