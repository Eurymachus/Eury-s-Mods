package net.minecraft.src.MultiTexturedPPlates;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3D;
import net.minecraft.src.EurysMods.ClientProxy;
import net.minecraft.src.EurysMods.ClientCore;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.EurysMods.core.IMod;
import net.minecraft.src.MultiTexturedPPlates.network.NetworkConnection;
import net.minecraft.src.MultiTexturedPPlates.network.PacketHandles;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.MinecraftForgeClient;

public class MultiTexturedPPlates
{
	public static String minecraftDir = Minecraft.getMinecraftDir().toString();
    public static ICore Core;
	private static boolean initialized = false;
	
	public static void initialize()
	{
		if (initialized) return;
		initialized = true;
		Core = new ClientCore(new ClientProxy(), new PacketHandles());
		Core.setModName("MultiTexturedPlates");
		Core.setModChannel("MTP");
    	load();
	}
    
    public static void load()
    {
    	EurysCore.console(Core.getModName(), "Registering items...");
	    ModLoader.registerTileEntity(TileEntityMTPPlate.class, "mtPPlate");
    	MTPCore.addItems();
	    EurysCore.console(Core.getModName(), "Naming items...");
	    MTPCore.addNames();
    	EurysCore.console(Core.getModName(), "Registering recipes...");
    	MTPCore.addRecipes();
    }
}
