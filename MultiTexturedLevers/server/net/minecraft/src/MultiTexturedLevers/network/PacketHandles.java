package net.minecraft.src.MultiTexturedLevers.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedLevers.TileEntityMTLever;

public class PacketHandles implements IPacketHandling {
	@Override
	public void handleTileEntityPacket(PacketUpdate packet,
			EntityPlayer player, World world) {
		if (packet != null && packet instanceof PacketUpdateMTLever) {
			PacketUpdateMTLever leverPacket = (PacketUpdateMTLever) packet;
			EntityPlayerMP entityplayermp = null;
			World worldserver = player.worldObj;
			if (!leverPacket.targetExists(worldserver))
				return;
			TileEntity tileentity = leverPacket.getTarget(worldserver);
			if ((tileentity != null)
					&& (tileentity instanceof TileEntityMTLever)) {
				TileEntityMTLever tileentitymtlever = (TileEntityMTLever) tileentity;
				tileentitymtlever.handleUpdatePacket(leverPacket, worldserver);
			}
		}
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer player,
			World world) {
	}
}
