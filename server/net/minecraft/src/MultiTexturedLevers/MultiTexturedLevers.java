package net.minecraft.src.MultiTexturedLevers;

import java.io.File;
import java.net.URISyntaxException;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet;
import net.minecraft.src.Vec3D;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.ServerCore;
import net.minecraft.src.EurysMods.ServerProxy;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.EurysMods.core.IMod;
import net.minecraft.src.MultiTexturedLevers.network.NetworkConnection;
import net.minecraft.src.MultiTexturedLevers.network.PacketHandles;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;

public class MultiTexturedLevers
{
	public static String minecraftDir = EurysCore.getMinecraftDir();
    public static ICore Core;
	private static boolean initialized = false;
	
	public static void initialize()
	{
		if (initialized) return;
		initialized = true;
		Core = new ServerCore(new ServerProxy(), new PacketHandles());
		Core.setModName("MultiTexturedLevers");
		Core.setModChannel("MTL");
    	load();
	}
    
    public static void load()
    {
    	EurysCore.console(Core.getModName(), "Registering items...");
	    ModLoader.registerTileEntity(TileEntityMTLever.class, "mtLever");
    	MTLCore.addItems();
	    EurysCore.console(Core.getModName(), "Naming items...");
	    MTLCore.addNames();
    	EurysCore.console(Core.getModName(), "Registering recipes...");
    	MTLCore.addRecipes();
    }
}
