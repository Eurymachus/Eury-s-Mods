package net.minecraft.src.MultiTexturedButtons;

import java.io.File;

import net.minecraft.src.Block;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MultiTexturedButtons.network.NetworkConnection;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;

public class MTBCore {
	public static String version = "v1.4";
	public static File configFile = new File(MultiTexturedButtons.minecraftDir,
			"config/MultiTexturedButtons.cfg");
	public static Configuration configuration = new Configuration(configFile);
	public static int BlockMTButtonID;
	public static Block BlockMTButton;
	public static ItemStack ironButton, goldButton, diamondButton;

	public static void initialize() {
		MultiTexturedButtons.initialize();
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
	}

	public static void addItems() {
		BlockMTButtonID = configurationProperties();
		BlockMTButton = new BlockMTButton(BlockMTButtonID,
				TileEntityMTButton.class, 0.5F, Block.soundStoneFootstep, true,
				true).setBlockName("mtButton");
		ModLoader.registerBlock(BlockMTButton, ItemMTButtons.class);
		ironButton = new ItemStack(BlockMTButton, 1, 0);
		goldButton = new ItemStack(BlockMTButton, 1, 1);
		diamondButton = new ItemStack(BlockMTButton, 1, 2);
	}

	public static void addNames() {
		ModLoader.addName(BlockMTButton, "Multi-Textured Button");
		ModLoader.addName(ironButton, "Iron Button");
		ModLoader.addName(goldButton, "Gold Button");
		ModLoader.addName(diamondButton, "Diamond Button");
	}

	public static void addRecipes() {
		ModLoader.addRecipe(ironButton,
				new Object[] { "X", "Y", Character.valueOf('X'),
						Item.ingotIron, Character.valueOf('Y'), Block.button });
		ModLoader.addRecipe(goldButton,
				new Object[] { "X", "Y", Character.valueOf('X'),
						Item.ingotGold, Character.valueOf('Y'), Block.button });
		ModLoader.addRecipe(diamondButton,
				new Object[] { "X", "Y", Character.valueOf('X'), Item.diamond,
						Character.valueOf('Y'), Block.button });
		FurnaceRecipes.smelting().addSmelting(BlockMTButtonID, 0,
				new ItemStack(Item.ingotIron, 1));
		FurnaceRecipes.smelting().addSmelting(BlockMTButtonID, 1,
				new ItemStack(Item.ingotGold, 1));
		FurnaceRecipes.smelting().addSmelting(BlockMTButtonID, 2,
				new ItemStack(Item.diamond, 1));
	}

	public static int configurationProperties() {
		configuration.load();
		BlockMTButtonID = Integer.parseInt(configuration
				.getOrCreateBlockIdProperty("BlockMTButtonID", 214).value);
		configuration.save();
		return BlockMTButtonID;
	}
}
