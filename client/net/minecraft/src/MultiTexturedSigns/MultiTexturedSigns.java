package net.minecraft.src.MultiTexturedSigns;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntitySpecialRenderer;
import net.minecraft.src.mod_MultiTexturedSigns;
import net.minecraft.src.EurysMods.ClientProxy;
import net.minecraft.src.EurysMods.ClientCore;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.EurysMods.core.IMod;
import net.minecraft.src.MultiTexturedSigns.network.NetworkConnection;
import net.minecraft.src.MultiTexturedSigns.network.PacketHandles;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.MinecraftForgeClient;

public class MultiTexturedSigns
{
	public static String minecraftDir = Minecraft.getMinecraftDir().toString();
	public static TileEntitySpecialRenderer mtsTileEntitySignRenderer = new TileEntityMTSignRenderer();
    public static ICore MTS;
	private static boolean initialized = false;
	
	public static String ironSign = "item/mtsIronSign.png";
	public static String goldSign = "item/mtsGoldSign.png";
	public static String diamondSign = "item/mtsDiamondSign.png";
	
	public static String getSignTexture(int textureData)
	{
        switch(textureData)
        {
        case 0:
        	return MTS.getModDir() + ironSign;
        case 1:
        	return  MTS.getModDir() + goldSign;
        case 2:
        	return  MTS.getModDir() + diamondSign;
        default:
        	return  MTS.getModDir() + ironSign;
        }
	}

	public static void displaymtsGuiEditSign(EntityPlayer entityplayer, TileEntityMTSign tileentitymtsign)
    {
		ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Opening GUI");
        ModLoader.openGUI(entityplayer, new GuiEditMTSign(tileentitymtsign));
    }
    
	public static void initialize()
	{
		if (initialized) return;
		initialized = true;
    	MTS = new ClientCore(new ClientProxy(), new PacketHandles());
    	MTS.setModName("MultiTexturedSigns");
    	MTS.setModChannel("MTS");
    	load();
	}
    
    public static void load()
    {
	    MinecraftForgeClient.preloadTexture(MTS.getBlockSheet());
	    MinecraftForgeClient.preloadTexture(MTS.getItemSheet());
    	MinecraftForgeClient.preloadTexture(getSignTexture(0));
    	MinecraftForgeClient.preloadTexture(getSignTexture(1));
    	MinecraftForgeClient.preloadTexture(getSignTexture(2));
    	EurysCore.console(MTS.getModName(), "Registering items...");
    	MTSCore.addItems();
		ModLoader.registerTileEntity(TileEntityMTSign.class, "mtSign", mtsTileEntitySignRenderer);
		EurysCore.console(MTS.getModName(), "Naming items...");
    	MTSCore.addNames();
    	EurysCore.console(MTS.getModName(), "Registering recipes...");
    	MTSCore.addRecipes();
    }
}
