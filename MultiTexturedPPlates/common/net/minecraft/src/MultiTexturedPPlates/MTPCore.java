package net.minecraft.src.MultiTexturedPPlates;

import java.io.File;

import net.minecraft.src.Block;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MultiTexturedPPlates.network.NetworkConnection;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;

public class MTPCore
{
	public static String version = "v1.3";
	public static File configFile = new File(MultiTexturedPPlates.minecraftDir, "config/MultiTexturedPPlates.cfg");
	public static Configuration configuration = new Configuration(configFile);
	public static int mtPPlateBlockID;
	public static Block mtPPlate;

	public static void initialize()
	{
		MultiTexturedPPlates.initialize();
    	MinecraftForge.registerConnectionHandler(new NetworkConnection());
	}
    
    public static void addItems()
    {
    	mtPPlateBlockID = configurationProperties();
    	mtPPlate = (new BlockMTPPlate(mtPPlateBlockID, TileEntityMTPPlate.class, 0.5F, Block.soundStoneFootstep, true, true).setBlockName("mtPPlate"));
	    ModLoader.registerBlock(mtPPlate, ItemMTPPlate.class);
    }
    
    public static void addNames()
    {
	    ModLoader.addName(mtPPlate, "MultiTextured Pressure Plate");
	    ModLoader.addName(new ItemStack(mtPPlate, 1, 0), "Iron Pressure Plate");
	    ModLoader.addName(new ItemStack(mtPPlate, 1, 1), "Gold Pressure Plate");
	    ModLoader.addName(new ItemStack(mtPPlate, 1, 2), "Diamond Pressure Plate");
    }
	
	public static void addRecipes()
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
    
    public static int configurationProperties()
    {
        configuration.load();
        mtPPlateBlockID = Integer.parseInt(configuration.getOrCreateBlockIdProperty("mtPPlateID", 194).value);
        configuration.save();
        return mtPPlateBlockID;
    }
}
