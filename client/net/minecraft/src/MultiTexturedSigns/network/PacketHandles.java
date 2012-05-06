package net.minecraft.src.MultiTexturedSigns.network;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedSigns.MTSCore;
import net.minecraft.src.MultiTexturedSigns.MultiTexturedSigns;
import net.minecraft.src.MultiTexturedSigns.TileEntityMTSign;

public class PacketHandles implements IPacketHandling
{
	@Override
	public void handleTileEntityPacket(PacketUpdate packet, EntityPlayer entityplayer)
	{
		if (packet != null && packet instanceof PacketUpdateMTSign)
		{
			PacketUpdateMTSign signPacket = (PacketUpdateMTSign)packet;
	        World world = ModLoader.getMinecraftInstance().theWorld;
	        if (!signPacket.targetExists(world)) return;
	        TileEntity tileentity = signPacket.getTarget(world);
	        if ((tileentity != null) && (tileentity instanceof TileEntityMTSign))
	        {
	            TileEntityMTSign tileentitymtsign = (TileEntityMTSign)tileentity;
	            tileentitymtsign.handleUpdatePacket(signPacket, world);
	        }
		}
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer entityplayer)
	{
		int x = packet.xPosition;
		int y = packet.yPosition;
		int z = packet.zPosition;
		TileEntity tileentity = ModLoader.getMinecraftInstance().theWorld.getBlockTileEntity(x, y, z);
		entityplayer.addChatMessage("X: " + tileentity.xCoord);
		entityplayer.addChatMessage("Y: " + tileentity.yCoord);
		entityplayer.addChatMessage("Z: " + tileentity.zCoord);
	}
}
