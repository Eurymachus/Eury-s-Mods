package net.minecraft.src.MultiTexturedButtons.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedButtons.TileEntityMTButton;

public class PacketHandles implements IPacketHandling {
	@Override
	public void handleTileEntityPacket(PacketUpdate packet,
			EntityPlayer entityplayer) {
		if (packet != null && packet instanceof PacketUpdateMTButton) {
			PacketUpdateMTButton buttonPacket = (PacketUpdateMTButton) packet;
			World world = ModLoader.getMinecraftInstance().theWorld;
			if (!buttonPacket.targetExists(world))
				return;
			TileEntity tileentity = buttonPacket.getTarget(world);
			if ((tileentity != null)
					&& (tileentity instanceof TileEntityMTButton)) {
				TileEntityMTButton tileentitymtbutton = (TileEntityMTButton) tileentity;
				tileentitymtbutton.handleUpdatePacket(buttonPacket, world);
			}
		}
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer entityplayer) {
	}
}
