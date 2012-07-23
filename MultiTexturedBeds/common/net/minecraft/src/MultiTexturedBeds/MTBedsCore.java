package net.minecraft.src.MultiTexturedBeds;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ShapedRecipes;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.MultiTexturedBeds.network.NetworkConnection;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;

public class MTBedsCore {
	public static String version = "v1.0";
	public static File configFile = new File(MultiTexturedBeds.minecraftDir,
			"config/MultiTexturedBeds.cfg");
	public static Configuration configuration = new Configuration(configFile);
	public static int mtBlockBedID, mtItemBedID;
	public static Block mtBlockBed;
	public static Item mtItemBed;
	public static ItemStack blackBed, redBed, greenBed, brownBed, blueBed,
			purpleBed, cyanBed, silverBed, grayBed, pinkBed, limeBed,
			yellowBed, lightBlueBed, magentaBed, orangeBed, whiteBed;

	public static void initialize() {
		MultiTexturedBeds.initialize();
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
	}

	public static void addItems() {
		mtBlockBedID = configurationProperties();
		ModLoader.registerBlock(mtBlockBed);
		mtItemBed = (new ItemMTBed(mtItemBedID - 256)).setItemName("mtItemBed");
		blackBed = new ItemStack(mtItemBed, 1, 0);
		redBed = new ItemStack(mtItemBed, 1, 1);
		greenBed = new ItemStack(mtItemBed, 1, 2);
		brownBed = new ItemStack(mtItemBed, 1, 3);
		blueBed = new ItemStack(mtItemBed, 1, 4);
		purpleBed = new ItemStack(mtItemBed, 1, 5);
		cyanBed = new ItemStack(mtItemBed, 1, 6);
		silverBed = new ItemStack(mtItemBed, 1, 7);
		grayBed = new ItemStack(mtItemBed, 1, 8);
		pinkBed = new ItemStack(mtItemBed, 1, 9);
		limeBed = new ItemStack(mtItemBed, 1, 10);
		yellowBed = new ItemStack(mtItemBed, 1, 11);
		lightBlueBed = new ItemStack(mtItemBed, 1, 12);
		magentaBed = new ItemStack(mtItemBed, 1, 13);
		orangeBed = new ItemStack(mtItemBed, 1, 14);
		whiteBed = new ItemStack(mtItemBed, 1, 15);
		ModLoader.registerTileEntity(TileEntityMTBed.class, "mtBed");
		mtBlockBed = (new BlockMTBed(mtBlockBedID, TileEntityMTBed.class, 0.2F,
				true, true)).setBlockName("mtBed");
	}

	public static void addNames() {
		ModLoader.addName(mtBlockBed, "Multi-Textured Bed");
		ModLoader.addName(mtItemBed, "Item Multi-Textured Bed");

		ModLoader.addName(blackBed, "Black Bed");
		ModLoader.addName(redBed, "Red Bed");
		ModLoader.addName(greenBed, "Green Bed");
		ModLoader.addName(brownBed, "Brown Bed");
		ModLoader.addName(blueBed, "Blue Bed");
		ModLoader.addName(purpleBed, "Purple Bed");
		ModLoader.addName(cyanBed, "Cyan Bed");
		ModLoader.addName(silverBed, "Light Gray Bed");
		ModLoader.addName(grayBed, "Gray Bed");
		ModLoader.addName(pinkBed, "Pink Bed");
		ModLoader.addName(limeBed, "Lime Bed");
		ModLoader.addName(yellowBed, "Yellow Bed");
		ModLoader.addName(lightBlueBed, "Light Blue Bed");
		ModLoader.addName(magentaBed, "Magenta Bed");
		ModLoader.addName(orangeBed, "Orange Bed");
		ModLoader.addName(whiteBed, "White Bed");
	}

	private static void removeRecipe(int outputID) {
		try {
			ArrayList recipelist = ((ArrayList) CraftingManager.getInstance()
					.getRecipeList());
			for (int i = 0; i < recipelist.size(); i++) {
				if (recipelist.get(i) != null
						&& recipelist.get(i) instanceof ShapedRecipes
						&& ((ShapedRecipes) recipelist.get(i)).recipeOutputItemID == outputID)
					recipelist.remove(i);
			}
		} catch (Exception e) {
			EurysCore.console(MultiTexturedBeds.MTBed.getModName(), "Could not access or remove from Minecraft Recipe List!", 1);
		}
	}

	public static void addRecipes() {
		removeRecipe(Item.bed.shiftedIndex);
		ModLoader.addRecipe(blackBed, new Object[] {"###", "XXX", '#', new ItemStack(Block.cloth, 1, 15), 'X', Block.planks});
		ModLoader.addRecipe(redBed, new Object[] {"###", "XXX", '#', new ItemStack(Block.cloth, 1, 14), 'X', Block.planks});
		ModLoader.addRecipe(greenBed, new Object[] {"###", "XXX", '#', new ItemStack(Block.cloth, 1, 13), 'X', Block.planks});
		ModLoader.addRecipe(brownBed, new Object[] {"###", "XXX", '#', new ItemStack(Block.cloth, 1, 12), 'X', Block.planks});
		ModLoader.addRecipe(blueBed, new Object[] {"###", "XXX", '#', new ItemStack(Block.cloth, 1, 11), 'X', Block.planks});
		ModLoader.addRecipe(purpleBed, new Object[] {"###", "XXX", '#', new ItemStack(Block.cloth, 1, 10), 'X', Block.planks});
		ModLoader.addRecipe(cyanBed, new Object[] {"###", "XXX", '#', new ItemStack(Block.cloth, 1, 9), 'X', Block.planks});
		ModLoader.addRecipe(silverBed, new Object[] {"###", "XXX", '#', new ItemStack(Block.cloth, 1, 8), 'X', Block.planks});
		ModLoader.addRecipe(grayBed, new Object[] {"###", "XXX", '#', new ItemStack(Block.cloth, 1, 7), 'X', Block.planks});
		ModLoader.addRecipe(pinkBed, new Object[] {"###", "XXX", '#', new ItemStack(Block.cloth, 1, 6), 'X', Block.planks});
		ModLoader.addRecipe(limeBed, new Object[] {"###", "XXX", '#', new ItemStack(Block.cloth, 1, 5), 'X', Block.planks});
		ModLoader.addRecipe(yellowBed, new Object[] {"###", "XXX", '#', new ItemStack(Block.cloth, 1, 4), 'X', Block.planks});
		ModLoader.addRecipe(lightBlueBed, new Object[] {"###", "XXX", '#', new ItemStack(Block.cloth, 1, 3), 'X', Block.planks});
		ModLoader.addRecipe(magentaBed, new Object[] {"###", "XXX", '#', new ItemStack(Block.cloth, 1, 2), 'X', Block.planks});
		ModLoader.addRecipe(orangeBed, new Object[] {"###", "XXX", '#', new ItemStack(Block.cloth, 1, 1), 'X', Block.planks});
		ModLoader.addRecipe(whiteBed, new Object[] {"###", "XXX", '#', new ItemStack(Block.cloth, 1, 0), 'X', Block.planks});
	}

	public static int configurationProperties() {
		configuration.load();
		mtBlockBedID = Integer.parseInt(configuration
				.getOrCreateBlockIdProperty("mtBlockBed", 199).value);
		mtItemBedID = Integer.parseInt(configuration
				.getOrCreateBlockIdProperty("mtBlockBed", 7005).value);
		configuration.save();
		return mtBlockBedID;
	}
}
