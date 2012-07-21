package net.minecraft.src.MultiTexturedBeds.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedBeds.TileEntityMTBed;

public class PacketHandles implements IPacketHandling {
	@Override
	public void handleTileEntityPacket(PacketUpdate packet,
			EntityPlayer entityplayer, World world) {
		if (packet != null && packet instanceof PacketUpdateMTBed) {
			PacketUpdateMTBed bedPacket = (PacketUpdateMTBed) packet;
			if (!bedPacket.targetExists(world))
				return;
			TileEntity tileentity = bedPacket.getTarget(world);
			if ((tileentity != null) && (tileentity instanceof TileEntityMTBed)) {
				TileEntityMTBed tileentitymtbed = (TileEntityMTBed) tileentity;
				tileentitymtbed.handleUpdatePacket(bedPacket, world);
			}
		}
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world) {
	}
}
