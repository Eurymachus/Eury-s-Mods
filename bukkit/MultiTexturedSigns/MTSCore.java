package MultiTexturedSigns;

import MultiTexturedSigns.network.NetworkConnection;
import forge.Configuration;
import forge.MinecraftForge;
import java.io.File;
import net.minecraft.server.Block;
import net.minecraft.server.FurnaceRecipes;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ModLoader;

public class MTSCore
{
    public static String version = "v3.0";
    public static File configFile = new File(MultiTexturedSigns.minecraftDir, "config/MultiTexturedSigns.cfg");
    public static Configuration configuration = new Configuration(configFile);
    public static int mtSignPostBlockID;
    public static int mtSignWallBlockID;
    public static int mtSignPartsItemID;
    public static int mtSignItemID;
    public static int mtSignToolItemID;
    public static Block mtSignPost;
    public static Block mtSignWall;
    public static Item mtsItemSignParts;
    public static Item mtsItemSigns;
    public static Item mtsItemSignTool;
    public static ItemStack ironCladPlating;
    public static ItemStack ironCladPole;
    public static ItemStack goldPlating;
    public static ItemStack goldenPole;
    public static ItemStack diamondPlating;
    public static ItemStack diamondPole;
    public static ItemStack ironCladSign;
    public static ItemStack goldPlatedSign;
    public static ItemStack diamondLatheredSign;

    public static void initialize()
    {
        MultiTexturedSigns.initialize();
        MinecraftForge.registerConnectionHandler(new NetworkConnection());
    }

    public static void addItems()
    {
        mtSignPostBlockID = configurationProperties();
        mtSignPost = (new BlockMTSign(mtSignPostBlockID, TileEntityMTSign.class, true, 1.0F, 2.0F, true, true)).a("mtSignPost");
        mtSignWall = (new BlockMTSign(mtSignWallBlockID, TileEntityMTSign.class, false, 1.0F, 2.0F, true, true)).a("mtSignWall");
        ModLoader.registerBlock(mtSignPost);
        ModLoader.registerBlock(mtSignWall);
        mtsItemSignParts = (new ItemMTSignParts(mtSignPartsItemID - 256)).a("mtsItemSignParts");
        mtsItemSigns = (new ItemMTSigns(mtSignItemID - 256)).a("mtsItemSigns");
        mtsItemSignTool = (new ItemMTSignTool(mtSignToolItemID - 256)).a("mtsItemSignTool");
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

    public static void addNames()
    {
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

    public static void addRecipes()
    {
        ModLoader.addRecipe(ironCladPlating.a(2), new Object[] {"XX", "XX", 'X', Item.IRON_INGOT});
        ModLoader.addRecipe(goldPlating.a(2), new Object[] {"XX", "XX", 'X', Item.GOLD_INGOT});
        ModLoader.addRecipe(diamondPlating.a(2), new Object[] {"XX", "XX", 'X', Item.DIAMOND});
        ModLoader.addRecipe(ironCladPole.a(8), new Object[] {"X", "Y", 'X', Item.IRON_INGOT, 'Y', Item.STICK});
        ModLoader.addRecipe(goldenPole.a(8), new Object[] {"X", "Y", 'X', Item.GOLD_INGOT, 'Y', Item.STICK});
        ModLoader.addRecipe(diamondPole.a(8), new Object[] {"X", "Y", 'X', Item.DIAMOND, 'Y', Item.STICK});
        ModLoader.addRecipe(ironCladSign, new Object[] {"X", "Y", 'X', ironCladPlating, 'Y', ironCladPole});
        ModLoader.addRecipe(goldPlatedSign, new Object[] {"X", "Y", 'X', goldPlating, 'Y', goldenPole});
        ModLoader.addRecipe(diamondLatheredSign, new Object[] {"X", "Y", 'X', diamondPlating, 'Y', diamondPole});
        ModLoader.addRecipe(ironCladSign, new Object[] {"X", "Y", "X", 'X', Item.IRON_INGOT, 'Y', Item.SIGN});
        ModLoader.addRecipe(goldPlatedSign, new Object[] {"X", "Y", "X", 'X', Item.GOLD_INGOT, 'Y', Item.SIGN});
        ModLoader.addRecipe(diamondLatheredSign, new Object[] {"X", "Y", "X", 'X', Item.DIAMOND, 'Y', Item.SIGN});
        FurnaceRecipes.getInstance().addSmelting(mtSignPartsItemID, 0, new ItemStack(Item.IRON_INGOT, 2));
        FurnaceRecipes.getInstance().addSmelting(mtSignPartsItemID, 2, new ItemStack(Item.GOLD_INGOT, 2));
        FurnaceRecipes.getInstance().addSmelting(mtSignPartsItemID, 4, new ItemStack(Item.DIAMOND, 2));
        ModLoader.addRecipe(new ItemStack(mtsItemSignTool, 1), new Object[] {"OXO", "ISI", "OXO", 'X', Item.IRON_INGOT, 'I', new ItemStack(Item.INK_SACK, 1, 0), 'S', Item.STICK});
    }

    public static int configurationProperties()
    {
        configuration.load();
        mtSignPostBlockID = Integer.parseInt(configuration.getOrCreateBlockIdProperty("mtSignPost", 213).value);
        mtSignWallBlockID = Integer.parseInt(configuration.getOrCreateBlockIdProperty("mtSignWall", 212).value);
        mtSignPartsItemID = Integer.parseInt(configuration.getOrCreateIntProperty("mtSignParts", "item", 7000).value);
        mtSignItemID = Integer.parseInt(configuration.getOrCreateIntProperty("mtSign", "item", 7001).value);
        mtSignToolItemID = Integer.parseInt(configuration.getOrCreateIntProperty("mtSignTool", "item", 7002).value);
        configuration.save();
        return mtSignPostBlockID;
    }
}
