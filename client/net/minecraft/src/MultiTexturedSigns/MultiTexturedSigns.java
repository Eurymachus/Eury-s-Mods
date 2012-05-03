package net.minecraft.src.MultiTexturedSigns;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntitySpecialRenderer;
import net.minecraft.src.mod_MultiTexturedSigns;
import net.minecraft.src.EurysMods.ClientProxy;
import net.minecraft.src.EurysMods.ClientCore;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.EurysMods.core.IMod;
import net.minecraft.src.MultiTexturedSigns.network.NetworkConnection;
import net.minecraft.src.MultiTexturedSigns.network.PacketHandles;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.MinecraftForgeClient;

public class MultiTexturedSigns implements IMod
{
	public static String minecraftDir = Minecraft.getMinecraftDir().toString();
	public static File configFile = new File(minecraftDir, "config/MultiTexturedSigns.cfg");
	public static Configuration configuration = new Configuration(configFile);
	public static int mtSignPostBlockID, mtSignWallBlockID, mtSignPartsItemID, mtSignItemID, mtSignToolItemID;
    public static Block mtSignPost, mtSignWall;
    public static Item mtsItemSignParts, mtsItemSigns, mtsItemSignTool;
    public static ItemStack ironCladPlating, ironCladPole, goldPlating, goldenPole, diamondPlating, diamondPole;
    public static ItemStack ironCladSign, goldPlatedSign, diamondLatheredSign;
    public static ICore MTSCore;
	private static boolean initialized = false;
	public static String ironSign = "item/mtsIronSign.png";
	public static String goldSign = "item/mtsGoldSign.png";
	public static String diamondSign = "item/mtsDiamondSign.png";
	public static TileEntitySpecialRenderer mtsTileEntitySignRenderer = new TileEntityMTSignRenderer();
	
	public static String getSignTexture(int textureData)
	{
        String texture = "";
        switch(textureData)
        {
        case 0:
        	texture = MTSCore.getModDir() + ironSign;
        	break;
        case 1:
        	texture = MTSCore.getModDir() + goldSign;
        	break;
        case 2:
        	texture = MTSCore.getModDir() + diamondSign;
        	break;
        default: texture = MTSCore.getModDir() + ironSign;
        }
        return texture;
	}

	public static void displaymtsGuiEditSign(EntityPlayer entityplayer, TileEntityMTSign tileentitymtsign)
    {
        ModLoader.openGUI(entityplayer, new GuiEditMTSign(tileentitymtsign));
    }
    
	public void initialize()
	{
		if (initialized) return;
		initialized = true;
    	MTSCore = new ClientCore(new ClientProxy(), new PacketHandles());
    	MTSCore.setModName("MultiTexturedSigns");
    	MTSCore.setModChannel("MTS");
    	load();
	}
    
    public void load()
    {
	    MinecraftForgeClient.preloadTexture(MTSCore.getBlockSheet());
	    MinecraftForgeClient.preloadTexture(MTSCore.getItemSheet());
    	MinecraftForgeClient.preloadTexture(getSignTexture(0));
    	MinecraftForgeClient.preloadTexture(getSignTexture(1));
    	MinecraftForgeClient.preloadTexture(getSignTexture(2));
    	MinecraftForge.registerConnectionHandler(new NetworkConnection());
		mtSignPostBlockID = configurationProperties();
    	EurysCore.console(MTSCore.getModName(), "Registering items...");
    	addItems();
		EurysCore.console(MTSCore.getModName(), "Naming items...");
    	addNames();
    	EurysCore.console(MTSCore.getModName(), "Registering recipes...");
    	addRecipes();
    }
	
	public void addItems()
	{
	    mtSignPost = (new BlockMTSign(mtSignPostBlockID, TileEntityMTSign.class, true, 1F, 2F, true, true)).setBlockName("mtSignPost");
	    mtSignWall = (new BlockMTSign(mtSignWallBlockID, TileEntityMTSign.class, false, 1F, 2F, true, true)).setBlockName("mtSignWall");
	    mtsItemSignParts = (new ItemMTSignParts(mtSignPartsItemID - 256)).setItemName("mtsItemSignParts");
	    mtsItemSigns = (new ItemMTSigns(mtSignItemID - 256)).setItemName("mtsItemSigns");
	    mtsItemSignTool = (new ItemMTSignTool(mtSignToolItemID - 256)).setItemName("mtsItemSignTool");
        ironCladPlating = new ItemStack(mtsItemSignParts, 1, 0);
        ironCladPole = new ItemStack(mtsItemSignParts, 1, 1);
        goldPlating = new ItemStack(mtsItemSignParts, 1, 2);
        goldenPole = new ItemStack(mtsItemSignParts, 1, 3);
        diamondPlating = new ItemStack(mtsItemSignParts, 1, 4);
        diamondPole = new ItemStack(mtsItemSignParts, 1, 5);
        ironCladSign = new ItemStack(mtsItemSigns, 1, 0);
        goldPlatedSign = new ItemStack(mtsItemSigns, 1, 1);
        diamondLatheredSign = new ItemStack(mtsItemSigns, 1, 2);
	    ModLoader.registerBlock(mtSignPost);
	    ModLoader.registerBlock(mtSignWall);
		ModLoader.registerTileEntity(TileEntityMTSign.class, "mtSign", mtsTileEntitySignRenderer);
	}

	public void addNames()
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
	
	public void addRecipes()
	{
	    // Plating
	    ModLoader.addRecipe(ironCladPlating.splitStack(2), new Object[]
	            {
	        "XX","XX", Character.valueOf('X'), Item.ingotIron
	     });
	    ModLoader.addRecipe(goldPlating.splitStack(2), new Object[]
	            {
	        "XX","XX", Character.valueOf('X'), Item.ingotGold
	     });
	    ModLoader.addRecipe(diamondPlating.splitStack(2), new Object[]
	            {
	        "XX","XX", Character.valueOf('X'), Item.diamond
	     });
	    // Poles
	    ModLoader.addRecipe(ironCladPole.splitStack(8), new Object[]
	            {
	        "X","Y", Character.valueOf('X'), Item.ingotIron, Character.valueOf('Y'), Item.stick
	     });
	    ModLoader.addRecipe(goldenPole.splitStack(8), new Object[]
	            {
	        "X","Y", Character.valueOf('X'), Item.ingotGold, Character.valueOf('Y'), Item.stick
	     });
	    ModLoader.addRecipe(diamondPole.splitStack(8), new Object[]
	            {
	        "X","Y", Character.valueOf('X'), Item.diamond, Character.valueOf('Y'), Item.stick
	     });
	    //Signs
	    ModLoader.addRecipe(ironCladSign, new Object[]
	            {
	        "X","Y", Character.valueOf('X'), ironCladPlating, Character.valueOf('Y'), ironCladPole
	     });
	    ModLoader.addRecipe(goldPlatedSign, new Object[]
	            {
	        "X","Y", Character.valueOf('X'), goldPlating, Character.valueOf('Y'), goldenPole
	     });
	    ModLoader.addRecipe(diamondLatheredSign, new Object[]
	            {
	        "X","Y", Character.valueOf('X'), diamondPlating, Character.valueOf('Y'), diamondPole
	     });
	    ModLoader.addRecipe(ironCladSign, new Object[]
	            {
	        "X","Y","X", Character.valueOf('X'), Item.ingotIron, Character.valueOf('Y'), Item.sign
	     });
	    ModLoader.addRecipe(goldPlatedSign, new Object[]
	            {
	        "X","Y","X", Character.valueOf('X'), Item.ingotGold, Character.valueOf('Y'), Item.sign
	     });
	    ModLoader.addRecipe(diamondLatheredSign, new Object[]
	            {
	        "X","Y","X", Character.valueOf('X'), Item.diamond, Character.valueOf('Y'), Item.sign
	     });
	    FurnaceRecipes.smelting().addSmelting(mtSignPartsItemID, 0, (new ItemStack(Item.ingotIron, 2)));
	    FurnaceRecipes.smelting().addSmelting(mtSignPartsItemID, 2, (new ItemStack(Item.ingotGold, 2)));
	    FurnaceRecipes.smelting().addSmelting(mtSignPartsItemID, 4, (new ItemStack(Item.diamond, 2)));
	    ModLoader.addRecipe(new ItemStack(mtsItemSignTool, 1), new Object[]
	            {
	        "OXO","ISI","OXO", Character.valueOf('X'), Item.ingotIron, Character.valueOf('I'), new ItemStack(Item.dyePowder, 1, 0), Character.valueOf('S'), Item.stick
	     });
	}
    
    public int configurationProperties()
    {
        configuration.load();
        mtSignPostBlockID = Integer.parseInt(configuration.getOrCreateBlockIdProperty("mtSignPost", 213).value);
        mtSignWallBlockID = Integer.parseInt(configuration.getOrCreateBlockIdProperty("mtSignWall", 212).value);
        mtSignPartsItemID = Integer.parseInt(configuration.getOrCreateIntProperty("mtSignParts", Configuration.CATEGORY_ITEM, 7000).value);
        mtSignItemID = Integer.parseInt(configuration.getOrCreateIntProperty("mtSign", Configuration.CATEGORY_ITEM, 7001).value);
        mtSignToolItemID = Integer.parseInt(configuration.getOrCreateIntProperty("mtSignTool", Configuration.CATEGORY_ITEM, 7002).value);
        configuration.save();
        return mtSignPostBlockID;
    }
}
