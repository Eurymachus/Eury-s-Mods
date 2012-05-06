package MultiTexturedPPlates;

import MultiTexturedPPlates.network.NetworkConnection;
import forge.Configuration;
import forge.MinecraftForge;
import java.io.File;
import net.minecraft.server.Block;
import net.minecraft.server.FurnaceRecipes;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ModLoader;

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
        mtPPlate = (new BlockMTPPlate(mtPPlateBlockID, TileEntityMTPPlate.class, 0.5F, Block.h, true, true)).a("mtPPlate");
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
        ModLoader.addRecipe(new ItemStack(mtPPlate, 1, 0), new Object[] {"X", "Y", 'X', Item.IRON_INGOT, 'Y', Block.WOOD_PLATE});
        ModLoader.addRecipe(new ItemStack(mtPPlate, 1, 1), new Object[] {"X", "Y", 'X', Item.GOLD_INGOT, 'Y', Block.STONE_PLATE});
        ModLoader.addRecipe(new ItemStack(mtPPlate, 1, 2), new Object[] {"X", "Y", 'X', Item.DIAMOND, 'Y', Block.STONE_PLATE});
        FurnaceRecipes.getInstance().addSmelting(mtPPlateBlockID, 0, new ItemStack(Item.IRON_INGOT, 1));
        FurnaceRecipes.getInstance().addSmelting(mtPPlateBlockID, 1, new ItemStack(Item.GOLD_INGOT, 1));
        FurnaceRecipes.getInstance().addSmelting(mtPPlateBlockID, 2, new ItemStack(Item.DIAMOND, 1));
    }

    public static int configurationProperties()
    {
        configuration.load();
        mtPPlateBlockID = Integer.parseInt(configuration.getOrCreateBlockIdProperty("mtPPlateID", 194).value);
        configuration.save();
        return mtPPlateBlockID;
    }
}
