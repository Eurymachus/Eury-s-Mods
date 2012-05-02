package net.minecraft.src.MultiTexturedPPlates;

import java.io.File;
import java.net.URISyntaxException;

import net.minecraft.src.Block;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Vec3D;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.ServerCore;
import net.minecraft.src.EurysMods.ServerProxy;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.EurysMods.core.IMod;
import net.minecraft.src.MultiTexturedPPlates.network.NetworkConnection;
import net.minecraft.src.MultiTexturedPPlates.network.PacketHandles;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;

public class MultiTexturedPPlates implements IMod
{
	public static File configFile = new File(EurysCore.getMinecraftDir(), "config/MultiTexturedPPlates.cfg");
	public static Configuration configuration = new Configuration(configFile);
	public static int mtPPlateBlockID;
	public static Block mtPPlate;
    public static ICore MTPCore;
	private static boolean initialized = false;
	
	public void initialize()
	{
		if (initialized) return;
		initialized = true;
		MTPCore = new ServerCore(new ServerProxy(), new PacketHandles());
		MTPCore.setModName("MultiTexturedPlates");
		MTPCore.setModChannel("MTP");
    	load();
	}
    
    public void load()
    {
    	MinecraftForge.registerConnectionHandler(new NetworkConnection());
    	mtPPlateBlockID = configurationProperties();
    	EurysCore.console(MTPCore.getModName(), "Registering items...");
    	addItems();
	    EurysCore.console(MTPCore.getModName(), "Naming items...");
    	addNames();
    	EurysCore.console(MTPCore.getModName(), "Registering recipes...");
    	addRecipes();
    }
    
    public void addItems()
    {
    	mtPPlate = (new BlockMTPPlate(mtPPlateBlockID, TileEntityMTPPlate.class, 0.5F, Block.soundStoneFootstep, true, true).setBlockName("mtPPlate"));
	    ModLoader.registerBlock(mtPPlate, ItemMTPPlate.class);
	    ModLoader.registerTileEntity(TileEntityMTPPlate.class, "mtPPlate");
    }
    
    public void addNames()
    {
	    ModLoader.addName(mtPPlate, "MultiTextured Pressure Plate");
	    ModLoader.addName(new ItemStack(mtPPlate, 1, 0), "Iron Pressure Plate");
	    ModLoader.addName(new ItemStack(mtPPlate, 1, 1), "Gold Pressure Plate");
	    ModLoader.addName(new ItemStack(mtPPlate, 1, 2), "Diamond Pressure Plate");
    }
	
	public void addRecipes()
	{
	    ModLoader.addRecipe(new ItemStack(mtPPlate, 1, 0), new Object[]
	            {
	        "X","Y", Character.valueOf('X'), Item.ingotIron, Character.valueOf('Y'), Block.pressurePlatePlanks
	     });
	    ModLoader.addRecipe(new ItemStack(mtPPlate, 1, 1), new Object[]
	            {
	        "X","Y", Character.valueOf('X'), Item.ingotGold, Character.valueOf('Y'), Block.pressurePlateStone
	     });
	    ModLoader.addRecipe(new ItemStack(mtPPlate, 1, 2), new Object[]
	            {
	        "X","Y", Character.valueOf('X'), Item.diamond, Character.valueOf('Y'), Block.pressurePlateStone
	     });
	    FurnaceRecipes.smelting().addSmelting(mtPPlateBlockID, 0, new ItemStack(Item.ingotIron, 1));
	    FurnaceRecipes.smelting().addSmelting(mtPPlateBlockID, 1, new ItemStack(Item.ingotGold, 1));
	    FurnaceRecipes.smelting().addSmelting(mtPPlateBlockID, 2, new ItemStack(Item.diamond, 1));
	}
    
    public int configurationProperties()
    {
        configuration.load();
        mtPPlateBlockID = Integer.parseInt(configuration.getOrCreateBlockIdProperty("mtPPlateID", 194).value);
        configuration.save();
        return mtPPlateBlockID;
    }
}
