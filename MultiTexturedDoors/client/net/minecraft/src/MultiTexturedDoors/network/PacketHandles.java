package net.minecraft.src.MultiTexturedDoors.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketTileEntity;
import net.minecraft.src.EurysMods.network.PacketTileEntityMT;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedDoors.TileEntityMTDoor;

public class PacketHandles implements IPacketHandling {
	@Override
	public void handleTileEntityPacket(PacketTileEntity packet,
			EntityPlayer entityplayer, World world) {
		if (packet != null && packet.targetExists(world)) {
			TileEntity tileentity = packet.getTileEntity(world);
			if ((tileentity != null)
					&& (tileentity instanceof TileEntityMTDoor)) {
				TileEntityMTDoor tileentitymtdoor = (TileEntityMTDoor) tileentity;
				tileentitymtdoor.handleUpdatePacket(world,
						(PacketTileEntityMT) packet);
			}
		}
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world) {
	}
}
