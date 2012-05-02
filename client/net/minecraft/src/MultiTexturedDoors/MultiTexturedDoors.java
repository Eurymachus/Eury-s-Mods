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

public class MultiTexturedDoors implements IMod
{
	public static String minecraftDir = Minecraft.getMinecraftDir().toString();
	public static File configFile = new File(minecraftDir, "config/MultiTexturedDoors.cfg");
	public static Configuration configuration = new Configuration(configFile);
	public static int mtDoorBlockID, mtDoorItemID, mtDoorBlockRenderID;
	public static Block mtDoor;
    public static Item mtDoorItem;
    public static ICore MTDCore;
	private static boolean initialized = false;

    public void initialize()
    {
		if (initialized) return;
		initialized = true;
    	MTDCore = new ClientCore(new ClientProxy(), new PacketHandles());
    	MTDCore.setModName("MultiTexturedDoors");
    	MTDCore.setModChannel("MTD");
    	load();
    }
    
    public void load()
    {
    	MinecraftForgeClient.preloadTexture(MTDCore.getBlockSheet());
    	MinecraftForge.registerConnectionHandler(new NetworkConnection());
		mtDoorBlockID = configurationProperties();
    	EurysCore.console(MTDCore.getModName(), "Registering Items...");
    	addItems();
    	EurysCore.console(MTDCore.getModName(), "Naming Items...");
    	addNames();
    	EurysCore.console(MTDCore.getModName(), "Registering Recipes...");
    	addRecipes();
    }
    
    public void addItems()
    {
		mtDoor = (new BlockMTDoor(mtDoorBlockID, TileEntityMTDoor.class, 0.5F, Block.soundStoneFootstep, true, true)).setBlockName("mtDoor");
	    mtDoorItem = (new ItemMTDoor(mtDoorItemID - 256)).setItemName("mtItemDoor");
        ModLoader.registerBlock(mtDoor);
        ModLoader.registerTileEntity(TileEntityMTDoor.class, "mtDoor");
        //mtDoorBlockRenderID = ModLoader.getUniqueBlockModelID(mod_MultiTexturedDoors.instance, true);
    }
    
    public void addNames()
    {
    	ModLoader.addName(mtDoor, "MultiTextured Door");
	    ModLoader.addName(new ItemStack(mtDoorItem, 1, 0), "Stone Door");
	    ModLoader.addName(new ItemStack(mtDoorItem, 1, 1), "Gold Door");
	    ModLoader.addName(new ItemStack(mtDoorItem, 1, 2), "Secret Bookcase Door");
    }
    
    public void addRecipes()
    {
        ModLoader.addRecipe(new ItemStack(mtDoorItem, 1, 0), new Object[]
                {
            "X","Y", Character.valueOf('X'), Block.dirt//Item.ingotIron, Character.valueOf('Y'), Block.pressurePlatePlanks
         });
        ModLoader.addRecipe(new ItemStack(mtDoorItem, 1, 1), new Object[]
                {
            "XX","Y", Character.valueOf('X'), Block.dirt//Item.ingotGold, Character.valueOf('Y'), Block.pressurePlateStone
         });
        ModLoader.addRecipe(new ItemStack(mtDoorItem, 1, 2), new Object[]
                {
            "X","Y", Character.valueOf('X'), Item.diamond, Character.valueOf('Y'), Block.pressurePlateStone
         });
        FurnaceRecipes.smelting().addSmelting(mtDoorBlockID, 0, new ItemStack(Item.ingotIron, 1));
        FurnaceRecipes.smelting().addSmelting(mtDoorBlockID, 1, new ItemStack(Item.ingotGold, 1));
        FurnaceRecipes.smelting().addSmelting(mtDoorBlockID, 2, new ItemStack(Item.diamond, 1));
    }
	
    public int configurationProperties()
    {
        configuration.load();
        mtDoorBlockID = Integer.parseInt(configuration.getOrCreateBlockIdProperty("mtDoorID", 196).value);
        mtDoorItemID = Integer.parseInt(configuration.getOrCreateIntProperty("mtDoorItemID", Configuration.CATEGORY_ITEM, 7004).value);
        configuration.save();
        return mtDoorBlockID;
    }
}
