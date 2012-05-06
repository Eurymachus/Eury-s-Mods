package net.minecraft.src.MultiTexturedDoors;

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
import net.minecraft.src.mod_MultiTexturedDoors;
import net.minecraft.src.EurysMods.ClientCore;
import net.minecraft.src.EurysMods.ClientProxy;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.EurysMods.core.IMod;
import net.minecraft.src.MultiTexturedDoors.network.NetworkConnection;
import net.minecraft.src.MultiTexturedDoors.network.PacketHandles;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.MinecraftForgeClient;

public class MultiTexturedDoors
{
	public static String minecraftDir = Minecraft.getMinecraftDir().toString();
	public static int mtDoorBlockRenderID;
    public static ICore Core;
	private static boolean initialized = false;

    public static void initialize()
    {
		if (initialized) return;
		initialized = true;
    	Core = new ClientCore(new ClientProxy(), new PacketHandles());
    	Core.setModName("MultiTexturedDoors");
    	Core.setModChannel("MTD");
    	load();
    }
    
    public static void load()
    {
    	MinecraftForgeClient.preloadTexture(Core.getBlockSheet());
    	mtDoorBlockRenderID = ModLoader.getUniqueBlockModelID(mod_MultiTexturedDoors.instance, true);
        ModLoader.registerTileEntity(TileEntityMTDoor.class, "mtDoor");
    	EurysCore.console(Core.getModName(), "Registering Items...");
    	MTDCore.addItems();
    	EurysCore.console(Core.getModName(), "Naming Items...");
    	MTDCore.addNames();
    	EurysCore.console(Core.getModName(), "Registering Recipes...");
    	MTDCore.addRecipes();
    }
}
