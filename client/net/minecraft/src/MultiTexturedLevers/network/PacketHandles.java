package net.minecraft.src.MultiTexturedLevers.network;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedLevers.TileEntityMTLever;

public class PacketHandles implements IPacketHandling
{
	@Override
	public void handleTileEntityPacket(PacketUpdate packet, EntityPlayer entityplayer)
	{
		if (packet != null && packet instanceof PacketUpdateMTLever)
		{
			PacketUpdateMTLever leverPacket = (PacketUpdateMTLever)packet;
	        World world = ModLoader.getMinecraftInstance().theWorld;
	        if (!leverPacket.targetExists(world)) return;
	        TileEntity tileentity = leverPacket.getTarget(world);
	        if ((tileentity != null) && (tileentity instanceof TileEntityMTLever))
	        {
	            TileEntityMTLever tileentitymtlever = (TileEntityMTLever)tileentity;
	            tileentitymtlever.handleUpdatePacket(leverPacket, world);
	        }
		}
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer entityplayer)
	{
	}
}
