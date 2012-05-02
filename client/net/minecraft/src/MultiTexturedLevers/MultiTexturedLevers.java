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

public class MultiTexturedLevers implements IMod
{
	public static String minecraftDir = Minecraft.getMinecraftDir().toString();
	public static File configFile = new File(minecraftDir, "config/MultiTexturedLevers.cfg");
	public static Configuration configuration = new Configuration(configFile);
	public static int mtLeverBlockID, mtLeverItemID, mtLeverBlockRenderID;
    public static Block mtLever;
    public static Item mtLeverItem;
    public static ICore MTLCore;
	private static boolean initialized = false;
	
	public void initialize()
	{
		if (initialized) return;
		initialized = true;
		MTLCore = new ClientCore(new ClientProxy(), new PacketHandles());
		MTLCore.setModName("MultiTexturedLevers");
		MTLCore.setModChannel("MTL");
    	load();
	}
    
    public void load()
    {
    	MinecraftForgeClient.preloadTexture(MTLCore.getItemSheet());
    	MinecraftForge.registerConnectionHandler(new NetworkConnection());
		mtLeverBlockID = configurationProperties();
    	EurysCore.console(MTLCore.getModName(), "Registering items...");
    	addItems();
	    EurysCore.console(MTLCore.getModName(), "Naming items...");
    	addNames();
    	EurysCore.console(MTLCore.getModName(), "Registering recipes...");
    	addRecipes();
    }
    
    public void addItems()
    {
		mtLever = (new BlockMTLever(mtLeverBlockID, TileEntityMTLever.class, 0.5F, Block.soundStoneFootstep, true, true)).setBlockName("mtLever");
		mtLeverItem = (new ItemMTLever(mtLeverItemID - 256)).setItemName("mtItemLever");
	    ModLoader.registerBlock(mtLever);
	    ModLoader.registerTileEntity(TileEntityMTLever.class, "mtLever");
	    mtLeverBlockRenderID = ModLoader.getUniqueBlockModelID(mod_MultiTexturedLevers.instance, true);
    }
    
    public void addNames()
    {
    	ModLoader.addName(mtLever, "MultiTextured Lever");
	    ModLoader.addName(new ItemStack(mtLeverItem, 1, 0), "Iron Lever");
	    ModLoader.addName(new ItemStack(mtLeverItem, 1, 1), "Gold Lever");
	    ModLoader.addName(new ItemStack(mtLeverItem, 1, 2), "Diamond Lever");
	    //ModLoader.addName(new ItemStack(mtLeverItem, 1, 2), "Stone Lever");
    }
	
	public void addRecipes()
	{
	    ModLoader.addRecipe(new ItemStack(mtLeverItem, 1, 0), new Object[]
	            {
	        "X","Y", Character.valueOf('X'), Item.stick, Character.valueOf('Y'),Item.ingotIron
	     });
	    ModLoader.addRecipe(new ItemStack(mtLeverItem, 1, 1), new Object[]
	            {
	        "X","Y", Character.valueOf('X'), Item.stick, Character.valueOf('Y'), Item.ingotGold
	     });
	    ModLoader.addRecipe(new ItemStack(mtLeverItem, 1, 2), new Object[]
	            {
	        "X","Y", Character.valueOf('X'), Item.stick, Character.valueOf('Y'), Item.diamond
	     });
/*	    ModLoader.addRecipe(new ItemStack(mtLeverItem, 1, 3), new Object[]
	            {
	        "X","Y", Character.valueOf('X'), Item.stick, Character.valueOf('X'), Block.stone
	     });*/
	    FurnaceRecipes.smelting().addSmelting(mtLeverItemID, 0, new ItemStack(Item.ingotIron, 1));
	    FurnaceRecipes.smelting().addSmelting(mtLeverItemID, 1, new ItemStack(Item.ingotGold, 1));
	    FurnaceRecipes.smelting().addSmelting(mtLeverItemID, 2, new ItemStack(Item.diamond, 1));
	}
 
    public int configurationProperties()
    {
        configuration.load();
        mtLeverBlockID = Integer.parseInt(configuration.getOrCreateBlockIdProperty("mtLeverID", 215).value);
        mtLeverItemID = Integer.parseInt(configuration.getOrCreateIntProperty("mtLeverItemID", Configuration.CATEGORY_ITEM, 7003).value);
        configuration.save();
        return mtLeverBlockID;
    }
}
