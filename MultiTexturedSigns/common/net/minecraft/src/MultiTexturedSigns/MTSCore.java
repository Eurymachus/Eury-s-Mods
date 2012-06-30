package net.minecraft.src.MultiTexturedSigns;

import java.io.File;

import net.minecraft.src.Block;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MultiTexturedSigns.network.NetworkConnection;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;

public class MTSCore {
	public static String version = "v3.1";
	public static File configFile = new File(MultiTexturedSigns.minecraftDir,
			"config/MultiTexturedSigns.cfg");
	public static Configuration configuration = new Configuration(configFile);
	public static int mtSignPostBlockID, mtSignWallBlockID, mtSignPartsItemID,
			mtSignItemID, mtSignToolItemID;
	public static Block mtSignPost, mtSignWall;
	public static Item mtsItemSignParts, mtsItemSigns, mtsItemSignTool;
	public static ItemStack ironCladPlating, ironCladPole, goldPlating,
			goldenPole, diamondPlating, diamondPole;
	public static ItemStack ironCladSign, goldPlatedSign, diamondLatheredSign;

	public static void initialize() {
		MultiTexturedSigns.initialize();
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
	}

	public static void addItems() {
		mtSignPostBlockID = configurationProperties();
		mtSignPost = (new BlockMTSign(mtSignPostBlockID,
				TileEntityMTSign.class, true, 1F, 2F, true, true))
				.setBlockName("mtSignPost");
		mtSignWall = (new BlockMTSign(mtSignWallBlockID,
				TileEntityMTSign.class, false, 1F, 2F, true, true))
				.setBlockName("mtSignWall");
		ModLoader.registerBlock(mtSignPost);
		ModLoader.registerBlock(mtSignWall);
		mtsItemSignParts = (new ItemMTSignParts(mtSignPartsItemID - 256))
				.setItemName("mtsItemSignParts");
		mtsItemSigns = (new ItemMTSigns(mtSignItemID - 256))
				.setItemName("mtsItemSigns");
		mtsItemSignTool = (new ItemMTSignTool(mtSignToolItemID - 256))
				.setItemName("mtsItemSignTool");
		ironCladPlating = new ItemStack(mtsItemSignParts, 1, 0);
		ironCladPole = new ItemStack(mtsItemSignParts, 1, 1);
		goldPlating = new ItemStack(mtsItemSignParts, 1, 2);
		goldenPole = new ItemStack(mtsItemSignParts, 1, 3);
		diamondPlating = new ItemStack(mtsItemSignParts, 1, 4);
		diamondPole = new ItemStack(mtsItemSignParts, 1, 5);
		ironCladSign = new ItemStack(mtsItemSigns, 1, 0);
		goldPlatedSign = new ItemStack(mtsItemSigns, 1, 1);
		diamondLatheredSign = new ItemStack(mtsItemSigns, 1, 2);
	}

	public static void addNames() {
		ModLoader.addName(mtSignPost, "Sign-Post");
		ModLoader.addName(mtSignWall, "Wall-Sign");
		ModLoader.addName(ironCladPlating, "Iron-Clad Plating");
		ModLoader.addName(ironCladPole, "Iron-Clad Pole");
		ModLoader.addName(goldPlating, "Gold Plating");
		ModLoader.addName(goldenPole, "Golden Pole");
		ModLoader.addName(diamondPlating, "Diamond-Studded Plating");
		ModLoader.addName(diamondPole, "Diamond-Encrusted Pole");
		ModLoader.addName(ironCladSign, "Iron-Clad Sign");
		ModLoader.addName(goldPlatedSign, "Gold-Plated Sign");
		ModLoader.addName(diamondLatheredSign, "Diamond-Lathered Sign");
		ModLoader.addName(mtsItemSignTool, "Sign Wand");
	}

	public static void addRecipes() {
		// Plating
		ModLoader.addRecipe(ironCladPlating.splitStack(2), new Object[] { "XX",
				"XX", Character.valueOf('X'), Item.ingotIron });
		ModLoader.addRecipe(goldPlating.splitStack(2), new Object[] { "XX",
				"XX", Character.valueOf('X'), Item.ingotGold });
		ModLoader.addRecipe(diamondPlating.splitStack(2), new Object[] { "XX",
				"XX", Character.valueOf('X'), Item.diamond });
		// Poles
		ModLoader.addRecipe(ironCladPole.splitStack(8),
				new Object[] { "X", "Y", Character.valueOf('X'),
						Item.ingotIron, Character.valueOf('Y'), Item.stick });
		ModLoader.addRecipe(goldenPole.splitStack(8), new Object[] { "X", "Y",
				Character.valueOf('X'), Item.ingotGold, Character.valueOf('Y'),
				Item.stick });
		ModLoader.addRecipe(diamondPole.splitStack(8), new Object[] { "X", "Y",
				Character.valueOf('X'), Item.diamond, Character.valueOf('Y'),
				Item.stick });
		// Signs
		ModLoader
				.addRecipe(ironCladSign,
						new Object[] { "X", "Y", Character.valueOf('X'),
								ironCladPlating, Character.valueOf('Y'),
								ironCladPole });
		ModLoader.addRecipe(goldPlatedSign,
				new Object[] { "X", "Y", Character.valueOf('X'), goldPlating,
						Character.valueOf('Y'), goldenPole });
		ModLoader.addRecipe(diamondLatheredSign, new Object[] { "X", "Y",
				Character.valueOf('X'), diamondPlating, Character.valueOf('Y'),
				diamondPole });
		ModLoader.addRecipe(ironCladSign, new Object[] { "X", "Y", "X",
				Character.valueOf('X'), Item.ingotIron, Character.valueOf('Y'),
				Item.sign });
		ModLoader.addRecipe(goldPlatedSign, new Object[] { "X", "Y", "X",
				Character.valueOf('X'), Item.ingotGold, Character.valueOf('Y'),
				Item.sign });
		ModLoader.addRecipe(diamondLatheredSign, new Object[] { "X", "Y", "X",
				Character.valueOf('X'), Item.diamond, Character.valueOf('Y'),
				Item.sign });
		FurnaceRecipes.smelting().addSmelting(mtSignPartsItemID, 0,
				(new ItemStack(Item.ingotIron, 2)));
		FurnaceRecipes.smelting().addSmelting(mtSignPartsItemID, 2,
				(new ItemStack(Item.ingotGold, 2)));
		FurnaceRecipes.smelting().addSmelting(mtSignPartsItemID, 4,
				(new ItemStack(Item.diamond, 2)));
		ModLoader.addRecipe(new ItemStack(mtsItemSignTool, 1), new Object[] {
				"OXO", "ISI", "OXO", Character.valueOf('X'), Item.ingotIron,
				Character.valueOf('I'), new ItemStack(Item.dyePowder, 1, 0),
				Character.valueOf('S'), Item.stick });
	}

	public static int configurationProperties() {
		configuration.load();
		mtSignPostBlockID = Integer.parseInt(configuration
				.getOrCreateBlockIdProperty("mtSignPost", 213).value);
		mtSignWallBlockID = Integer.parseInt(configuration
				.getOrCreateBlockIdProperty("mtSignWall", 212).value);
		mtSignPartsItemID = Integer.parseInt(configuration
				.getOrCreateIntProperty("mtSignParts",
						Configuration.CATEGORY_ITEM, 7000).value);
		mtSignItemID = Integer.parseInt(configuration.getOrCreateIntProperty(
				"mtSign", Configuration.CATEGORY_ITEM, 7001).value);
		mtSignToolItemID = Integer.parseInt(configuration
				.getOrCreateIntProperty("mtSignTool",
						Configuration.CATEGORY_ITEM, 7002).value);
		configuration.save();
		return mtSignPostBlockID;
	}
}
