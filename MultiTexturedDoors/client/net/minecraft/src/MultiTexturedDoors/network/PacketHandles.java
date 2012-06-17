package net.minecraft.src.MultiTexturedDoors.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedDoors.TileEntityMTDoor;

public class PacketHandles implements IPacketHandling {
	@Override
	public void handleTileEntityPacket(PacketUpdate packet,
			EntityPlayer entityplayer) {
		if (packet != null && packet instanceof PacketUpdateMTDoor) {
			PacketUpdateMTDoor doorPacket = (PacketUpdateMTDoor) packet;
			World world = ModLoader.getMinecraftInstance().theWorld;
			if (!doorPacket.targetExists(world))
				return;
			TileEntity tileentity = doorPacket.getTarget(world);
			if ((tileentity != null)
					&& (tileentity instanceof TileEntityMTDoor)) {
				TileEntityMTDoor tileentitymtdoor = (TileEntityMTDoor) tileentity;
				tileentitymtdoor.handleUpdatePacket(doorPacket, world);
			}
		}
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer entityplayer) {
	}
}
