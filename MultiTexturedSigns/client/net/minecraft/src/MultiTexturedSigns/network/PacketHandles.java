package net.minecraft.src.MultiTexturedSigns.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketTileEntity;
import net.minecraft.src.EurysMods.network.PacketTileEntityMT;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedSigns.GuiEditMTSign;
import net.minecraft.src.MultiTexturedSigns.TileEntityMTSign;

public class PacketHandles implements IPacketHandling {
	@Override
	public void handleTileEntityPacket(PacketTileEntity packet,
			EntityPlayer entityplayer, World world) {
		if (packet != null && packet.targetExists(world)) {
			TileEntity tileentity = packet.getTileEntity(world);
			if ((tileentity != null)
					&& (tileentity instanceof TileEntityMTSign)) {
				TileEntityMTSign tileentitymtsign = (TileEntityMTSign) tileentity;
				tileentitymtsign.handleUpdatePacket(world,
						(PacketTileEntityMT) packet);
			}
		}
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world) {
		int x = packet.xPosition;
		int y = packet.yPosition;
		int z = packet.zPosition;
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);
		if (tileentity != null && tileentity instanceof TileEntityMTSign) {
			TileEntityMTSign tileentitymtsign = (TileEntityMTSign) tileentity;
			ModLoader
					.openGUI(entityplayer, new GuiEditMTSign(tileentitymtsign));
		}
	}
}
