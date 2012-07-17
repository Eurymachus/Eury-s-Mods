package net.minecraft.src.MultiTexturedSigns;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySpecialRenderer;
import net.minecraft.src.EurysMods.ClientCore;
import net.minecraft.src.EurysMods.ClientProxy;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.MultiTexturedSigns.network.PacketHandles;
import net.minecraft.src.forge.MinecraftForgeClient;

public class MultiTexturedSigns {
	public static String minecraftDir = Minecraft.getMinecraftDir().toString();
	private static Minecraft mc = ModLoader.getMinecraftInstance();
	public static TileEntitySpecialRenderer mtsTileEntitySignRenderer = new TileEntityMTSignRenderer();
	public static ICore MTS;
	private static boolean initialized = false;

	public static String ironSign = "item/mtsIronSign.png";
	public static String goldSign = "item/mtsGoldSign.png";
	public static String diamondSign = "item/mtsDiamondSign.png";

	public static String getSignTexture(int textureData) {
		switch (textureData) {
		case 0:
			return MTS.getModDir() + ironSign;
		case 1:
			return MTS.getModDir() + goldSign;
		case 2:
			return MTS.getModDir() + diamondSign;
		default:
			return MTS.getModDir() + ironSign;
		}
	}

	public static void displaymtsGuiEditSign(EntityPlayer entityplayer,
			TileEntityMTSign tileentitymtsign) {
		if (!ModLoader.getMinecraftInstance().theWorld.isRemote)
			ModLoader
					.openGUI(entityplayer, new GuiEditMTSign(tileentitymtsign));
	}

	public static void initialize() {
		if (initialized)
			return;
		initialized = true;
		MTS = new ClientCore(new ClientProxy(), new PacketHandles());
		MTS.setModName("MultiTexturedSigns");
		MTS.setModChannel("MTS");
		load();
	}

	public static void load() {
		MinecraftForgeClient.preloadTexture(MTS.getBlockSheet());
		MinecraftForgeClient.preloadTexture(MTS.getItemSheet());
		MinecraftForgeClient.preloadTexture(getSignTexture(0));
		MinecraftForgeClient.preloadTexture(getSignTexture(1));
		MinecraftForgeClient.preloadTexture(getSignTexture(2));
		EurysCore.console(MTS.getModName(), "Registering items...");
		MTSCore.addItems();
		ModLoader.registerTileEntity(TileEntityMTSign.class, "mtSign",
				mtsTileEntitySignRenderer);
		EurysCore.console(MTS.getModName(), "Naming items...");
		MTSCore.addNames();
		EurysCore.console(MTS.getModName(), "Registering recipes...");
		MTSCore.addRecipes();
	}
	
	public static int getDamageValue(IBlockAccess blockAccess, int x, int y,
			int z) {
		TileEntity tileentity = blockAccess.getBlockTileEntity(x, y, z);
		if (tileentity != null && tileentity instanceof TileEntityMTSign) {
			TileEntityMTSign tileentitymtsign = (TileEntityMTSign) tileentity;
			return tileentitymtsign.getMetaValue();
		}
		return 0;
	}

	public static int getMouseOver() {
		if (mc.objectMouseOver != null) {
			int xPosition = mc.objectMouseOver.blockX;
			int yPosition = mc.objectMouseOver.blockY;
			int zPosition = mc.objectMouseOver.blockZ;
			return getDamageValue(mc.theWorld, xPosition, yPosition, zPosition);
		}
		return 0;
	}

	public static int getBelowPlayer(EntityPlayer player) {
		int playerX = (int) player.posX;
		int playerY = (int) player.posY;
		int playerZ = (int) player.posZ;
		return getDamageValue(mc.theWorld, playerX, playerY - 1, playerZ);
	}

	public static int getAtPlayer(EntityPlayer player) {
		int playerX = (int) player.posX;
		int playerY = (int) player.posY;
		int playerZ = (int) player.posZ;
		return getDamageValue(mc.theWorld, playerX, playerY, playerZ);
	}
	
	public static int getBlockTextureFromMetadata(int i) {
		int texture = -1;
		EntityPlayer player = mc.thePlayer;
		if (player.onGround) {
			texture = getMouseOver();
		}
		if (texture == -1 && player.isAirBorne) {
			texture = getMouseOver();
		}
		if (texture == -1 && player.isAirBorne) {
			texture = getBelowPlayer(player);
		}
		if (texture == -1 && player.isAirBorne) {
			texture = getAtPlayer(player);
		}
		if (texture == -1) {
			texture = 0;
		}
		return texture;
	}
}
