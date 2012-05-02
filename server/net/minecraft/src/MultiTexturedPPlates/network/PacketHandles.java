package net.minecraft.src.MultiTexturedPPlates.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedPPlates.TileEntityMTPPlate;

public class PacketHandles implements IPacketHandling
{
	@Override
	public void handleTileEntityPacket(PacketUpdate packet, EntityPlayer player)
	{
		if (packet != null && packet instanceof PacketUpdateMTPPlate)
		{
			PacketUpdateMTPPlate platePacket = (PacketUpdateMTPPlate)packet;
	        World world = player.worldObj;
	        if (!platePacket.targetExists(world)) return;
	        TileEntity tileentity = platePacket.getTarget(world);
	        if ((tileentity != null) && (tileentity instanceof TileEntityMTPPlate))
	        {
	            TileEntityMTPPlate tileentitymtpplate = (TileEntityMTPPlate)tileentity;
	            tileentitymtpplate.handleUpdatePacket(platePacket, world);
	        }
		}
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer player)
	{
	}
}
