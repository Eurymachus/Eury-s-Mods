package net.minecraft.src.MultiTexturedDoors.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedDoors.TileEntityMTDoor;

public class PacketHandles implements IPacketHandling
{
	@Override
	public void handleTileEntityPacket(PacketUpdate packet, EntityPlayer player)
	{
		if (packet != null && packet instanceof PacketUpdateMTDoor)
		{
			PacketUpdateMTDoor doorPacket = (PacketUpdateMTDoor)packet;
	        World world = player.worldObj;
	        if (!doorPacket.targetExists(world)) return;
	        TileEntity tileentity = doorPacket.getTarget(world);
	        if ((tileentity != null) && (tileentity instanceof TileEntityMTDoor))
	        {
	            TileEntityMTDoor tileentitymtlever = (TileEntityMTDoor)tileentity;
	            tileentitymtlever.handleUpdatePacket(doorPacket, world);
	        }
		}
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer player)
	{
	}
}
