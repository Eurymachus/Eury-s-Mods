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

public class MultiTexturedPPlates implements IMod
{
	private static String getMCPath() { return Minecraft.getMinecraftDir().getPath(); }
	public static File configFile = new File(getMCPath(), "config/MultiTexturedPPlates.cfg");
	public static Configuration configuration = new Configuration(configFile);
	public static int mtPPlateBlockID, mtPPlateItemID;
	public static Block mtPPlate;
	
    public static ICore MTPCore;
	private static boolean initialized = false;
	
	public void initialize()
	{
		if (initialized) return;
		initialized = true;
		MTPCore = new ClientCore(new ClientProxy(), new PacketHandles());
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
    
/*    public void handleTileEntityPacket(int x, int y, int z, int l, int ai[], float af[], String as[])
	{
        World world = ModLoader.getMinecraftInstance().theWorld;
        if (world.blockExists(x, y, z))
        {
            TileEntity tileentity = world.getBlockTileEntity(x, y, z);
            if ((tileentity != null) && (tileentity instanceof TileEntityMTPPlate))
            {
            	TileEntityMTPPlate mtstileentitypplate = (TileEntityMTPPlate)tileentity;
            	mtstileentitypplate.setMetaValue(ai[3]);
            	mtstileentitypplate.setTriggerType(ai[4]);
                mtstileentitypplate.onInventoryChanged();
            }
        }
    }*/
    
    public int configurationProperties()
    {
        configuration.load();
        mtPPlateBlockID = Integer.parseInt(configuration.getOrCreateBlockIdProperty("mtPPlateID", 194).value);
        configuration.save();
        return mtPPlateBlockID;
    }
}
