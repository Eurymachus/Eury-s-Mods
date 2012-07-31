package net.minecraft.src.PaintingChooser;

import java.io.File;
import java.util.ArrayList;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Entity;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ShapedRecipes;
import net.minecraft.src.mod_PaintingChooser;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.PaintingChooser.network.NetworkConnection;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;

public class PChooserCore {

	public static String version = "1.0";
	public static File configFile = new File(PaintingChooser.minecraftDir,
			"config/PaintingChooser.cfg");
	public static Configuration configuration = new Configuration(configFile);
	public static int entityPaintingsID, itemPaintingsID;
	public static Entity entityPaintings;
	public static Item itemPaintings;

	public static void initialize() {
		PaintingChooser.initialize();
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
	}
	
	public static void addItems() {
		entityPaintingsID = configurationProperties();
		itemPaintings = (new ItemPaintings(itemPaintingsID - 256)).setItemName("itemPaintings");
		//MinecraftForge.registerEntity(EntityPaintings.class, mod_PaintingChooser.instance, entityPaintingsID, 16, 16, false);
	}

	public static void addNames() {
		ModLoader.addName(itemPaintings, "Painting");
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
			EurysCore.console(PaintingChooser.PChooser.getModName(), "Could not access or remove from Minecraft Recipe List!", 1);
		}
	}

	public static void addRecipes() {
		removeRecipe(Item.painting.shiftedIndex);
        ModLoader.addRecipe(new ItemStack(itemPaintings, 1), new Object[] {"###", "#X#", "###", '#', Item.stick, 'X', Block.cloth});
	}
	
	public static int configurationProperties() {
		configuration.load();
		entityPaintingsID = Integer.parseInt(configuration
				.getOrCreateIntProperty("entityPaintingsID", Configuration.CATEGORY_GENERAL, ModLoader.getUniqueEntityId()).value);
		itemPaintingsID = Integer.parseInt(configuration
				.getOrCreateIntProperty("itemPaintingsID", Configuration.CATEGORY_ITEM, 7006).value);
		configuration.save();
		return entityPaintingsID;
	}
}
