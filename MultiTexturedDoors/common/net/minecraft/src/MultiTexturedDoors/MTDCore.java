package net.minecraft.src.MultiTexturedDoors;

import java.io.File;

import net.minecraft.src.Block;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MultiTexturedDoors.network.NetworkConnection;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;

public class MTDCore {
	public static String version = "v1.2";
	public static File configFile = new File(MultiTexturedDoors.minecraftDir,
			"config/MultiTexturedDoors.cfg");
	public static Configuration configuration = new Configuration(configFile);
	public static int mtDoorBlockID, mtDoorItemID;
	public static Block mtDoor;
	public static Item mtDoorItem;
	public static ItemStack stoneDoor, goldDoor, diamondDoor;

	public static void initialize() {
		MultiTexturedDoors.initialize();
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
	}

	public static void addItems() {
		mtDoorBlockID = configurationProperties();
		mtDoor = (new BlockMTDoor(mtDoorBlockID, TileEntityMTDoor.class, 0.5F,
				Block.soundStoneFootstep, true, true, "mtDoor"));
		ModLoader.registerBlock(mtDoor);
		mtDoorItem = (new ItemMTDoor(mtDoorItemID - 256))
				.setItemName("mtItemDoor");
		stoneDoor = new ItemStack(mtDoorItem, 1, 0);
		goldDoor = new ItemStack(mtDoorItem, 1, 1);
		diamondDoor = new ItemStack(mtDoorItem, 1, 2);
	}

	public static void addNames() {
		ModLoader.addName(mtDoor, "MultiTextured Door");
		ModLoader.addName(stoneDoor, "Stone Door");
		ModLoader.addName(goldDoor, "Gold Door");
		ModLoader.addName(diamondDoor, "Diamond Door");
	}

	public static void addRecipes() {
		ModLoader.addRecipe(stoneDoor, new Object[] { "XX", "XX", "XX",
				Character.valueOf('X'), Block.stone });
		ModLoader.addRecipe(goldDoor,
				new Object[] { "X", "Y", "X", Character.valueOf('X'),
						Item.ingotGold, Character.valueOf('Y'), stoneDoor });
		ModLoader.addRecipe(diamondDoor, new Object[] { "X", "Y", "X",
				Character.valueOf('X'), Item.diamond, Character.valueOf('Y'),
				goldDoor });
		FurnaceRecipes.smelting().addSmelting(mtDoorItemID, 0,
				new ItemStack(Item.ingotIron, 1));
		FurnaceRecipes.smelting().addSmelting(mtDoorItemID, 1,
				new ItemStack(Item.ingotGold, 1));
		FurnaceRecipes.smelting().addSmelting(mtDoorItemID, 2,
				new ItemStack(Item.diamond, 1));
	}

	public static int configurationProperties() {
		configuration.load();
		mtDoorBlockID = Integer.parseInt(configuration
				.getOrCreateBlockIdProperty("mtDoorID", 196).value);
		mtDoorItemID = Integer.parseInt(configuration.getOrCreateIntProperty(
				"mtDoorItemID", Configuration.CATEGORY_ITEM, 7004).value);
		configuration.save();
		return mtDoorBlockID;
	}
}
