package net.minecraft.src.MultiTexturedDoors.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedDoors.TileEntityMTDoor;

public class PacketHandles implements IPacketHandling {
	@Override
	public void handleTileEntityPacket(PacketUpdate packet,
			EntityPlayer player, World world) {
		if (packet != null && packet instanceof PacketUpdateMTDoor) {
			PacketUpdateMTDoor doorPacket = (PacketUpdateMTDoor) packet;
			if (!doorPacket.targetExists(world))
				return;
			TileEntity tileentity = doorPacket.getTarget(world);
			if ((tileentity != null)
					&& (tileentity instanceof TileEntityMTDoor)) {
				TileEntityMTDoor tileentitymtlever = (TileEntityMTDoor) tileentity;
				tileentitymtlever.handleUpdatePacket(doorPacket, world);
			}
		}
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer player,
			World world) {
	}
}
