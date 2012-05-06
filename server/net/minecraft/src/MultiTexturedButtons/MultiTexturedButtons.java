package net.minecraft.src.MultiTexturedButtons;

import java.io.File;
import java.net.URISyntaxException;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.ServerCore;
import net.minecraft.src.EurysMods.ServerProxy;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.EurysMods.core.IMod;
import net.minecraft.src.MultiTexturedButtons.network.NetworkConnection;
import net.minecraft.src.MultiTexturedButtons.network.PacketHandles;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;

public class MultiTexturedButtons
{
	public static String minecraftDir = EurysCore.getMinecraftDir();
    public static ICore Core;
    
	private static boolean initialized = false;
	
	public static void initialize()
	{
		if (initialized) return;
		initialized = true;
    	Core = new ServerCore(new ServerProxy(), new PacketHandles());
    	Core.setModName("MultiTexturedButtons");
    	Core.setModChannel("MTB");
    	load();
	}
    
    public static void load()
    {
    	EurysCore.console(Core.getModName(), "Registering items...");
    	MTBCore.addItems();
	    EurysCore.console(Core.getModName(), "Naming items...");
	    MTBCore.addNames();
    	EurysCore.console(Core.getModName(), "Registering recipes...");
    	MTBCore.addRecipes();
    }
}
