package MultiTexturedButtons;

import MultiTexturedButtons.network.NetworkConnection;
import forge.Configuration;
import forge.MinecraftForge;
import java.io.File;
import net.minecraft.server.Block;
import net.minecraft.server.FurnaceRecipes;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ModLoader;

public class MTBCore
{
    public static String version = "v1.3";
    public static File configFile = new File(MultiTexturedButtons.minecraftDir, "config/MultiTexturedButtons.cfg");
    public static Configuration configuration = new Configuration(configFile);
    public static int BlockMTButtonID;
    public static Block BlockMTButton;
    public static ItemStack ironButton;
    public static ItemStack goldButton;
    public static ItemStack diamondButton;

    public static void initialize()
    {
        MultiTexturedButtons.initialize();
        MinecraftForge.registerConnectionHandler(new NetworkConnection());
    }

    public static void addItems()
    {
        BlockMTButtonID = configurationProperties();
        BlockMTButton = (new BlockMTButton(BlockMTButtonID, TileEntityMTButton.class, 0.5F, Block.h, true, true)).a("mtButton");
        ModLoader.registerBlock(BlockMTButton, ItemMTButtons.class);
        ironButton = new ItemStack(BlockMTButton, 1, 0);
        goldButton = new ItemStack(BlockMTButton, 1, 1);
        diamondButton = new ItemStack(BlockMTButton, 1, 2);
    }

    public static void addNames()
    {
        ModLoader.addName(BlockMTButton, "Multi-Textured Button");
        ModLoader.addName(ironButton, "Iron Button");
        ModLoader.addName(goldButton, "Gold Button");
        ModLoader.addName(diamondButton, "Diamond Button");
    }

    public static void addRecipes()
    {
        ModLoader.addRecipe(ironButton, new Object[] {"X", "Y", 'X', Item.IRON_INGOT, 'Y', Block.STONE_BUTTON});
        ModLoader.addRecipe(goldButton, new Object[] {"X", "Y", 'X', Item.GOLD_INGOT, 'Y', Block.STONE_BUTTON});
        ModLoader.addRecipe(diamondButton, new Object[] {"X", "Y", 'X', Item.DIAMOND, 'Y', Block.STONE_BUTTON});
        FurnaceRecipes.getInstance().addSmelting(BlockMTButtonID, 0, new ItemStack(Item.IRON_INGOT, 1));
        FurnaceRecipes.getInstance().addSmelting(BlockMTButtonID, 1, new ItemStack(Item.GOLD_INGOT, 1));
        FurnaceRecipes.getInstance().addSmelting(BlockMTButtonID, 2, new ItemStack(Item.DIAMOND, 1));
    }

    public static int configurationProperties()
    {
        configuration.load();
        BlockMTButtonID = Integer.parseInt(configuration.getOrCreateBlockIdProperty("BlockMTButtonID", 214).value);
        configuration.save();
        return BlockMTButtonID;
    }
}
