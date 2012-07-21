package net.minecraft.src.MultiTexturedSigns.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedSigns.TileEntityMTSign;

public class PacketHandles implements IPacketHandling {
	@Override
	public void handleTileEntityPacket(PacketUpdate packet,
			EntityPlayer entityplayer, World world) {
		if (packet != null && packet instanceof PacketUpdateMTSign) {
			PacketUpdateMTSign signPacket = (PacketUpdateMTSign) packet;
			EntityPlayerMP entityplayermp = (EntityPlayerMP) entityplayer;
			if (signPacket.targetExists(world)) {
				TileEntity tileentity = signPacket.getTarget(world);
				if ((tileentity != null)
						&& (tileentity instanceof TileEntityMTSign)) {
					TileEntityMTSign tileentitymtsign = (TileEntityMTSign) tileentity;
					tileentitymtsign.handleUpdatePacket(signPacket, world);
				}
			}
		}
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer player,
			World world) {
	}
}
