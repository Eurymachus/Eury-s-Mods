package net.minecraft.src.MultiTexturedLevers;

import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3D;
import net.minecraft.src.mod_MultiTexturedLevers;
import net.minecraft.src.EurysMods.ClientProxy;
import net.minecraft.src.EurysMods.ClientCore;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.EurysMods.core.IMod;
import net.minecraft.src.MultiTexturedLevers.network.NetworkConnection;
import net.minecraft.src.MultiTexturedLevers.network.PacketHandles;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.MinecraftForgeClient;

public class MultiTexturedLevers
{
	public static String minecraftDir = Minecraft.getMinecraftDir().toString();
	public static int mtLeverBlockRenderID;
    public static ICore Core;
	private static boolean initialized = false;
	
	public static void initialize()
	{
		if (initialized) return;
		initialized = true;
		Core = new ClientCore(new ClientProxy(), new PacketHandles());
		Core.setModName("MultiTexturedLevers");
		Core.setModChannel("MTL");
    	load();
	}
    
    public static void load()
    {
    	MinecraftForgeClient.preloadTexture(Core.getItemSheet());
    	mtLeverBlockRenderID = ModLoader.getUniqueBlockModelID(mod_MultiTexturedLevers.instance, true);
	    ModLoader.registerTileEntity(TileEntityMTLever.class, "mtLever");
    	EurysCore.console(Core.getModName(), "Registering items...");
    	MTLCore.addItems();
	    EurysCore.console(Core.getModName(), "Naming items...");
	    MTLCore.addNames();
    	EurysCore.console(Core.getModName(), "Registering recipes...");
    	MTLCore.addRecipes();
    }
}
