package net.minecraft.src.MultiTexturedLevers;

import java.io.File;

import net.minecraft.src.Block;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MultiTexturedLevers.network.NetworkConnection;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;

public class MTLCore {
	public static String version = "v1.4";
	static File configFile = new File(MultiTexturedLevers.minecraftDir,
			"config/MultiTexturedLevers.cfg");
	public static Configuration configuration = new Configuration(configFile);
	public static int mtLeverBlockID, mtLeverItemID;
	public static Block mtLever;
	public static Item mtLeverItem;
	public static ItemStack ironLever, goldLever, diamondLever;

	public static void initialize() {
		MultiTexturedLevers.initialize();
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
	}

	public static void addItems() {
		mtLeverBlockID = configurationProperties();
		mtLever = (new BlockMTLever(mtLeverBlockID, TileEntityMTLever.class,
				0.5F, Block.soundStoneFootstep, true, true))
				.setBlockName("mtLever");
		ModLoader.registerBlock(mtLever);
		mtLeverItem = (new ItemMTLever(mtLeverItemID - 256))
				.setItemName("mtItemLever");
		ironLever = new ItemStack(mtLeverItem, 1, 0);
		goldLever = new ItemStack(mtLeverItem, 1, 1);
		diamondLever = new ItemStack(mtLeverItem, 1, 2);
	}

	public static void addNames() {
		ModLoader.addName(mtLever, "MultiTextured Lever");
		ModLoader.addName(ironLever, "Iron Lever");
		ModLoader.addName(goldLever, "Gold Lever");
		ModLoader.addName(diamondLever, "Diamond Lever");
		// ModLoader.addName(new ItemStack(mtLeverItem, 1, 2), "Stone Lever");
	}

	public static void addRecipes() {
		ModLoader.addRecipe(ironLever,
				new Object[] { "X", "Y", Character.valueOf('X'), Item.stick,
						Character.valueOf('Y'), Item.ingotIron });
		ModLoader.addRecipe(goldLever,
				new Object[] { "X", "Y", Character.valueOf('X'), Item.stick,
						Character.valueOf('Y'), Item.ingotGold });
		ModLoader.addRecipe(diamondLever,
				new Object[] { "X", "Y", Character.valueOf('X'), Item.stick,
						Character.valueOf('Y'), Item.diamond });
		/*
		 * ModLoader.addRecipe(new ItemStack(mtLeverItem, 1, 3), new Object[] {
		 * "X","Y", Character.valueOf('X'), Item.stick, Character.valueOf('X'),
		 * Block.stone });
		 */
		FurnaceRecipes.smelting().addSmelting(mtLeverItemID, 0,
				new ItemStack(Item.ingotIron, 1));
		FurnaceRecipes.smelting().addSmelting(mtLeverItemID, 1,
				new ItemStack(Item.ingotGold, 1));
		FurnaceRecipes.smelting().addSmelting(mtLeverItemID, 2,
				new ItemStack(Item.diamond, 1));
	}

	public static int configurationProperties() {
		configuration.load();
		mtLeverBlockID = Integer.parseInt(configuration
				.getOrCreateBlockIdProperty("mtLeverID", 195).value);
		mtLeverItemID = Integer.parseInt(configuration.getOrCreateIntProperty(
				"mtLeverItemID", Configuration.CATEGORY_ITEM, 7003).value);
		configuration.save();
		return mtLeverBlockID;
	}
}
