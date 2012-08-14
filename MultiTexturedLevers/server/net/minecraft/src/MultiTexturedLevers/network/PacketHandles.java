package net.minecraft.src.MultiTexturedLevers.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketTileEntity;
import net.minecraft.src.EurysMods.network.PacketTileEntityMT;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedLevers.TileEntityMTLever;

public class PacketHandles implements IPacketHandling {
	@Override
	public void handleTileEntityPacket(PacketTileEntity packet,
			EntityPlayer player, World world) {
		if (packet != null && packet.targetExists(world)) {
			TileEntity tileentity = packet.getTileEntity(world);
			if ((tileentity != null)
					&& (tileentity instanceof TileEntityMTLever)) {
				TileEntityMTLever tileentitymtlever = (TileEntityMTLever) tileentity;
				tileentitymtlever.handleUpdatePacket(world,
						(PacketTileEntityMT) packet);
			}
		}
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer player,
			World world) {
	}
}
