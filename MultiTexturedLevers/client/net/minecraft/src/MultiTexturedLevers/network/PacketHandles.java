package net.minecraft.src.MultiTexturedLevers.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedLevers.TileEntityMTLever;

public class PacketHandles implements IPacketHandling {
	@Override
	public void handleTileEntityPacket(PacketUpdate packet,
			EntityPlayer entityplayer, World world) {
		if (packet != null && packet instanceof PacketUpdateMTLever) {
			PacketUpdateMTLever leverPacket = (PacketUpdateMTLever) packet;
			if (!leverPacket.targetExists(world))
				return;
			TileEntity tileentity = leverPacket.getTarget(world);
			if ((tileentity != null)
					&& (tileentity instanceof TileEntityMTLever)) {
				TileEntityMTLever tileentitymtlever = (TileEntityMTLever) tileentity;
				tileentitymtlever.handleUpdatePacket(leverPacket, world);
			}
		}
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world) {
	}
}
