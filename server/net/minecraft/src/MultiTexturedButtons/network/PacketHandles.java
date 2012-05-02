package net.minecraft.src.MultiTexturedButtons.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedButtons.TileEntityMTButton;

public class PacketHandles implements IPacketHandling
{
	@Override
    public void handleTileEntityPacket(PacketUpdate packet, EntityPlayer player)
	{
		if (packet != null && packet instanceof PacketUpdateMTButton)
		{
			PacketUpdateMTButton buttonPacket = (PacketUpdateMTButton)packet;
	        EntityPlayerMP entityplayermp = null;
	        World worldserver = player.worldObj;
	        if (!buttonPacket.targetExists(worldserver)) return;
            TileEntity tileentity = buttonPacket.getTarget(worldserver);
            if ((tileentity != null) && (tileentity instanceof TileEntityMTButton))
            {
            	TileEntityMTButton mtstileentitybutton = (TileEntityMTButton)tileentity;
            	mtstileentitybutton.handleUpdatePacket(buttonPacket, worldserver);
            }
		}
    }

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer var2)
	{
	}
}
