package net.minecraft.src.MultiTexturedBeds;

import java.io.File;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MultiTexturedBeds.network.NetworkConnection;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;

public class MTBedsCore{
	public static String version = "v1.0";
	public static File configFile = new File(MultiTexturedBeds.minecraftDir, "config/MultiTexturedBeds.cfg");
	public static Configuration configuration = new Configuration(configFile);
	public static int mtBlockBedID, mtItemBedID;
	public static Block mtBlockBed;
	public static Item mtItemBed;
	public static ItemStack blueBed, blackBed, pinkBed, greenBed, yellowBed, purpleBed, orangeBed, greyBed;
	
	public static void initialize() {
		MultiTexturedBeds.initialize();
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
	}

	public static void addItems() {
		mtBlockBedID = configurationProperties();
		ModLoader.registerTileEntity(TileEntityMTBed.class, "mtBed");
		mtBlockBed = (new BlockMTBed(mtBlockBedID, TileEntityMTBed.class, 1.0F, true, true)).setBlockName("mtBed");
		ModLoader.registerBlock(mtBlockBed);
		mtItemBed = (new ItemMTBed(mtItemBedID - 256)).setItemName("mtItemBed");
		blueBed = new ItemStack(mtItemBed, 1, 0);
		blackBed = new ItemStack(mtItemBed, 1, 1);
		pinkBed = new ItemStack(mtItemBed, 1, 2);
		greenBed = new ItemStack(mtItemBed, 1, 3);
		yellowBed = new ItemStack(mtItemBed, 1, 4);
		purpleBed = new ItemStack(mtItemBed, 1, 5);
		orangeBed = new ItemStack(mtItemBed, 1, 6);
		greyBed = new ItemStack(mtItemBed, 1, 7);
	}

	public static void addNames() {
		ModLoader.addName(mtBlockBed, "Multi-Textured Bed");
		ModLoader.addName(mtItemBed, "Multi-Textured BedItem");
		ModLoader.addName(blueBed, "Blue Bed");
		ModLoader.addName(blackBed, "Black Bed");
		ModLoader.addName(pinkBed, "Pink Bed");
		ModLoader.addName(greenBed, "Green Bed");
		ModLoader.addName(yellowBed, "Yellow Bed");
		ModLoader.addName(purpleBed, "Purple Bed");
		ModLoader.addName(orangeBed, "Orange Bed");
		ModLoader.addName(greyBed, "Grey Bed");
	}

	public static void addRecipes() {
		ModLoader.addRecipe(blackBed,
				new Object[] { "XX", Character.valueOf('X'), Block.dirt});
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
