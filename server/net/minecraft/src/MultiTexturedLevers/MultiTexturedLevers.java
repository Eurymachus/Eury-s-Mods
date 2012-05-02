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

public class MultiTexturedLevers implements IMod
{
	static File configFile = new File(EurysCore.getMinecraftDir(), "config/MultiTexturedLevers.cfg");
	public static Configuration configuration = new Configuration(configFile);
	public static int mtLeverBlockID, mtLeverItemID;
    public static Block mtLever;
    public static Item mtLeverItem;
    public static ICore MTLCore;
	private static boolean initialized = false;
	
	public void initialize()
	{
		if (initialized) return;
		initialized = true;
		MTLCore = new ServerCore(new ServerProxy(), new PacketHandles());
		MTLCore.setModName("MultiTexturedLevers");
		MTLCore.setModChannel("MTL");
    	load();
	}
    
    public void load()
    {
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
