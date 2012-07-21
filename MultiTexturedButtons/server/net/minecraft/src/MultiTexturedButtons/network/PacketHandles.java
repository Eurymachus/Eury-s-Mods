package net.minecraft.src.MultiTexturedButtons.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedButtons.TileEntityMTButton;

public class PacketHandles implements IPacketHandling {
	@Override
	public void handleTileEntityPacket(PacketUpdate packet,
			EntityPlayer entityplayer, World world) {
		if (packet != null && packet instanceof PacketUpdateMTButton) {
			PacketUpdateMTButton buttonPacket = (PacketUpdateMTButton) packet;
			if (!buttonPacket.targetExists(world))
				return;
			TileEntity tileentity = buttonPacket.getTarget(world);
			if ((tileentity != null)
					&& (tileentity instanceof TileEntityMTButton)) {
				TileEntityMTButton mtstileentitybutton = (TileEntityMTButton) tileentity;
				mtstileentitybutton.handleUpdatePacket(buttonPacket, world);
			}
		}
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world) {
	}
}
