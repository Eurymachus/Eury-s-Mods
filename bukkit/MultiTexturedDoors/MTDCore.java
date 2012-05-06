package MultiTexturedDoors;

import MultiTexturedDoors.network.NetworkConnection;
import forge.Configuration;
import forge.MinecraftForge;
import java.io.File;
import net.minecraft.server.Block;
import net.minecraft.server.FurnaceRecipes;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ModLoader;

public class MTDCore
{
    public static String version = "v1.0";
    public static File configFile = new File(MultiTexturedDoors.minecraftDir, "config/MultiTexturedDoors.cfg");
    public static Configuration configuration = new Configuration(configFile);
    public static int mtDoorBlockID;
    public static int mtDoorItemID;
    public static Block mtDoor;
    public static Item mtDoorItem;
    public static ItemStack stoneDoor;
    public static ItemStack goldDoor;
    public static ItemStack diamondDoor;

    public static void initialize()
    {
        MultiTexturedDoors.initialize();
        MinecraftForge.registerConnectionHandler(new NetworkConnection());
    }

    public static void addItems()
    {
        mtDoorBlockID = configurationProperties();
        mtDoor = (new BlockMTDoor(mtDoorBlockID, TileEntityMTDoor.class, 0.5F, Block.h, true, true)).a("mtDoor");
        ModLoader.registerBlock(mtDoor);
        mtDoorItem = (new ItemMTDoor(mtDoorItemID - 256)).a("mtItemDoor");
        stoneDoor = new ItemStack(mtDoorItem, 1, 0);
        goldDoor = new ItemStack(mtDoorItem, 1, 1);
        diamondDoor = new ItemStack(mtDoorItem, 1, 2);
    }

    public static void addNames()
    {
        ModLoader.addName(mtDoor, "MultiTextured Door");
        ModLoader.addName(stoneDoor, "Stone Door");
        ModLoader.addName(goldDoor, "Gold Door");
        ModLoader.addName(diamondDoor, "Secret Bookcase Door");
    }

    public static void addRecipes()
    {
        ModLoader.addRecipe(stoneDoor, new Object[] {"X", "Y", 'X', Block.DIRT});
        ModLoader.addRecipe(goldDoor, new Object[] {"XX", "Y", 'X', Block.DIRT});
        ModLoader.addRecipe(diamondDoor, new Object[] {"XXX", "Y", 'X', Block.DIRT});
        FurnaceRecipes.getInstance().addSmelting(mtDoorBlockID, 0, new ItemStack(Item.IRON_INGOT, 1));
        FurnaceRecipes.getInstance().addSmelting(mtDoorBlockID, 1, new ItemStack(Item.GOLD_INGOT, 1));
        FurnaceRecipes.getInstance().addSmelting(mtDoorBlockID, 2, new ItemStack(Item.DIAMOND, 1));
    }

    public static int configurationProperties()
    {
        configuration.load();
        mtDoorBlockID = Integer.parseInt(configuration.getOrCreateBlockIdProperty("mtDoorID", 196).value);
        mtDoorItemID = Integer.parseInt(configuration.getOrCreateIntProperty("mtDoorItemID", "item", 7004).value);
        configuration.save();
        return mtDoorBlockID;
    }
}
