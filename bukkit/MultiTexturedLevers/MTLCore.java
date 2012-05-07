package MultiTexturedLevers;

import MultiTexturedLevers.network.NetworkConnection;
import forge.Configuration;
import forge.MinecraftForge;
import java.io.File;
import net.minecraft.server.Block;
import net.minecraft.server.FurnaceRecipes;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ModLoader;

public class MTLCore
{
    public static String version = "v1.3";
    static File configFile = new File(MultiTexturedLevers.minecraftDir, "config/MultiTexturedLevers.cfg");
    public static Configuration configuration = new Configuration(configFile);
    public static int mtLeverBlockID;
    public static int mtLeverItemID;
    public static Block mtLever;
    public static Item mtLeverItem;
    public static ItemStack ironLever;
    public static ItemStack goldLever;
    public static ItemStack diamondLever;

    public static void initialize()
    {
        MultiTexturedLevers.initialize();
        MinecraftForge.registerConnectionHandler(new NetworkConnection());
    }

    public static void addItems()
    {
        mtLeverBlockID = configurationProperties();
        mtLever = (new BlockMTLever(mtLeverBlockID, TileEntityMTLever.class, 0.5F, Block.h, true, true)).a("mtLever");
        ModLoader.registerBlock(mtLever);
        mtLeverItem = (new ItemMTLever(mtLeverItemID - 256)).a("mtItemLever");
        ironLever = new ItemStack(mtLeverItem, 1, 0);
        goldLever = new ItemStack(mtLeverItem, 1, 1);
        diamondLever = new ItemStack(mtLeverItem, 1, 2);
    }

    public static void addNames()
    {
        ModLoader.addName(mtLever, "MultiTextured Lever");
        ModLoader.addName(ironLever, "Iron Lever");
        ModLoader.addName(goldLever, "Gold Lever");
        ModLoader.addName(diamondLever, "Diamond Lever");
    }

    public static void addRecipes()
    {
        ModLoader.addRecipe(ironLever, new Object[] {"X", "Y", 'X', Item.STICK, 'Y', Item.IRON_INGOT});
        ModLoader.addRecipe(goldLever, new Object[] {"X", "Y", 'X', Item.STICK, 'Y', Item.GOLD_INGOT});
        ModLoader.addRecipe(diamondLever, new Object[] {"X", "Y", 'X', Item.STICK, 'Y', Item.DIAMOND});
        FurnaceRecipes.getInstance().addSmelting(mtLeverItemID, 0, new ItemStack(Item.IRON_INGOT, 1));
        FurnaceRecipes.getInstance().addSmelting(mtLeverItemID, 1, new ItemStack(Item.GOLD_INGOT, 1));
        FurnaceRecipes.getInstance().addSmelting(mtLeverItemID, 2, new ItemStack(Item.DIAMOND, 1));
    }

    public static int configurationProperties()
    {
        configuration.load();
        mtLeverBlockID = Integer.parseInt(configuration.getOrCreateBlockIdProperty("mtLeverID", 195).value);
        mtLeverItemID = Integer.parseInt(configuration.getOrCreateIntProperty("mtLeverItemID", "item", 7003).value);
        configuration.save();
        return mtLeverBlockID;
    }
}
