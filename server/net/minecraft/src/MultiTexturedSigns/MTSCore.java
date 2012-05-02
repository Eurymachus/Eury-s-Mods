package net.minecraft.src.MultiTexturedSigns;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedSigns.network.PacketOpenGui;

public class MTSCore
{
	public static String version = "v3.0";

	public static void initialize()
	{
		new MultiTexturedSigns().initialize();
	}

	public static void displaymtsGuiEditSign(EntityPlayer entityplayer, PacketUpdate pu)
    {
		if (entityplayer != null && entityplayer instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP)entityplayer;
	    	if (pu != null && pu instanceof PacketOpenGui)
	    	{
		    	//ModLoader.getMinecraftServerInstance().log("Sending Packet");
		    	player.playerNetServerHandler.netManager.addToSendQueue(pu.getPacket());
	    	}
		}
    }
}
