package net.minecraft.src.EurysMods;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_EurysMods;
import net.minecraft.src.EurysMods.core.IProxy;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.NetworkMod;

public class ClientProxy implements IProxy
{
	public boolean isClient(World world)
	{
		return world.isRemote;
	}
	
	public boolean isServer(World world)
	{
		return false;
	}
	
	public boolean isSingleplayer(World world)
	{
	    return !world.isRemote;
	}
	  
	public PacketPayload getTileEntityPacket(TileEntity te, int[] dataInt, float[] dataFloat, String[] dataString)
	{
	    return null;
	}
	  
	public void sendPacketToServer(Packet250CustomPayload packet)
	{
	}

	@Override
	public PacketPayload getTileEntityPayload(int[] dataInt, float[] dataFloat,
			String[] dataString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendPacket(EntityPlayer entityplayer, Packet packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendPacketToAll(Packet packet, int x, int y, int z,
			int maxDistance, NetworkMod mod) {
		// TODO Auto-generated method stub
		
	}
}