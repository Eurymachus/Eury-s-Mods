package net.minecraft.src.MultiTexturedButtons;

import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet;
import net.minecraft.src.EurysMods.ClientProxy;
import net.minecraft.src.EurysMods.ClientCore;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.EurysMods.core.IMod;
import net.minecraft.src.MultiTexturedButtons.network.NetworkConnection;
import net.minecraft.src.MultiTexturedButtons.network.PacketHandles;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;

public class MultiTexturedButtons implements IMod
{
	public static String minecraftDir = Minecraft.getMinecraftDir().toString();
	public static File configFile = new File(minecraftDir, "config/MultiTexturedButtons.cfg");
	public static Configuration configuration = new Configuration(configFile);
	public static int BlockMTButtonID;
    public static Block BlockMTButton;
    public static ICore MTBCore;
    
	private static boolean initialized = false;
	
	public void initialize()
	{
		if (initialized) return;
		initialized = true;
    	MTBCore = new ClientCore(new ClientProxy(), new PacketHandles());
    	MTBCore.setModName("MultiTexturedButtons");
    	MTBCore.setModChannel("MTB");
    	load();
	}
    
    public void load()
    {
    	MinecraftForge.registerConnectionHandler(new NetworkConnection());
		BlockMTButtonID =  configurationProperties();
    	EurysCore.console(MTBCore.getModName(), "Registering items...");
    	addItems();
	    EurysCore.console(MTBCore.getModName(), "Naming items...");
    	addNames();
    	EurysCore.console(MTBCore.getModName(), "Registering recipes...");
    	addRecipes();
    }
    
    public void addItems()
    {
		BlockMTButton = new BlockMTButton(BlockMTButtonID, TileEntityMTButton.class, 0.5F, Block.soundStoneFootstep, true, true).setBlockName("mtButton");
	    ModLoader.registerBlock(BlockMTButton, ItemMTButtons.class);
	    ModLoader.registerTileEntity(TileEntityMTButton.class, "mtButton");
    }
    
    public void addNames()
    {
    	ModLoader.addName(BlockMTButton, "Multi-Textured Button");
	    ModLoader.addName(new ItemStack(BlockMTButton, 1, 0), "Iron Button");
	    ModLoader.addName(new ItemStack(BlockMTButton, 1, 1), "Gold Button");
	    ModLoader.addName(new ItemStack(BlockMTButton, 1, 2), "Diamond Button");
    }
	
	public void addRecipes()
	{
	    ModLoader.addRecipe(new ItemStack(BlockMTButton, 1, 0), new Object[]
	            {
	        "X","Y", Character.valueOf('X'), Item.ingotIron, Character.valueOf('Y'), Block.button
	     });
	    ModLoader.addRecipe(new ItemStack(BlockMTButton, 1, 1), new Object[]
	            {
	        "X","Y", Character.valueOf('X'), Item.ingotGold, Character.valueOf('Y'), Block.button
	     });
	    ModLoader.addRecipe(new ItemStack(BlockMTButton, 1, 2), new Object[]
	            {
	        "X","Y", Character.valueOf('X'), Item.diamond, Character.valueOf('Y'), Block.button
	     });
	    FurnaceRecipes.smelting().addSmelting(BlockMTButtonID, 0, new ItemStack(Item.ingotIron, 1));
	    FurnaceRecipes.smelting().addSmelting(BlockMTButtonID, 1, new ItemStack(Item.ingotGold, 1));
	    FurnaceRecipes.smelting().addSmelting(BlockMTButtonID, 2, new ItemStack(Item.diamond, 1));
	}
	
    public int configurationProperties()
    {
        configuration.load();
        BlockMTButtonID = Integer.parseInt(configuration.getOrCreateBlockIdProperty("BlockMTButtonID", 214).value);
        configuration.save();
        return BlockMTButtonID;
    }
}
