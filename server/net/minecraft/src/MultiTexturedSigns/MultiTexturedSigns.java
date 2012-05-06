package net.minecraft.src.MultiTexturedSigns;

import java.io.File;
import java.net.URISyntaxException;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.ServerCore;
import net.minecraft.src.EurysMods.ServerProxy;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.EurysMods.core.IMod;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedSigns.network.NetworkConnection;
import net.minecraft.src.MultiTexturedSigns.network.PacketHandles;
import net.minecraft.src.MultiTexturedSigns.network.PacketOpenGui;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;

public class MultiTexturedSigns
{
	public static String minecraftDir = EurysCore.getMinecraftDir();
    public static ICore MTS;
	private static boolean initialized = false;

	public static void displaymtsGuiEditSign(EntityPlayer entityplayer, TileEntityMTSign tileentitymtsign)
    {
		EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;
    	PacketOpenGui gui = new PacketOpenGui(tileentitymtsign.xCoord, tileentitymtsign.yCoord, tileentitymtsign.zCoord);
		entityplayermp.playerNetServerHandler.netManager.addToSendQueue(gui.getPacket());
    }
	
	public static void initialize()
	{
		if (initialized) return;
		initialized = true;
    	MTS = new ServerCore(new ServerProxy(), new PacketHandles());
    	MTS.setModName("MultiTexturedSigns");
    	MTS.setModChannel("MTS");
    	load();
	}
    
    public static void load()
    {
    	EurysCore.console(MTS.getModName(), "Registering items...");
		ModLoader.registerTileEntity(TileEntityMTSign.class, "mtSign");
    	MTSCore.addItems();
		EurysCore.console(MTS.getModName(), "Naming items...");
    	MTSCore.addNames();
    	EurysCore.console(MTS.getModName(), "Registering recipes...");
    	MTSCore.addRecipes();
    }
}