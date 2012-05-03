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
		if (packet != null && packet instanceof PacketOpenGui)
		{
			PacketOpenGui openGui = (PacketOpenGui)packet;
			World world = ModLoader.getMinecraftInstance().theWorld;
			if(!openGui.targetExists(world)) return;
			TileEntity tileentity = openGui.getTarget(world);
			//player.addChatMessage("Packet Received");
	        if ((tileentity != null) && (tileentity instanceof TileEntityMTSign))
	        {
	            TileEntityMTSign tileentitymtsign = (TileEntityMTSign)tileentity;
	            MultiTexturedSigns.displaymtsGuiEditSign(entityplayer, tileentitymtsign);
	        }
		}
	}
}
