package net.minecraft.src.MultiTexturedBeds;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_MultiTexturedBeds;
import net.minecraft.src.EurysMods.ClientCore;
import net.minecraft.src.EurysMods.ClientProxy;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.MultiTexturedBeds.network.PacketHandles;
import net.minecraft.src.forge.MinecraftForgeClient;

public class MultiTexturedBeds {
	public static String minecraftDir = Minecraft.getMinecraftDir().toString();
	private static Minecraft mc = ModLoader.getMinecraftInstance();
	public static int mtBedRenderID;
	public static ICore MTBed;
	private static boolean initialized = false;

	public static void initialize() {
		if (!initialized) {
			initialized = true;
			MTBed = new ClientCore(new ClientProxy(), new PacketHandles());
			MTBed.setModName("MultiTexturedBeds");
			MTBed.setModChannel("MTBed");
			load();
		}
	}

	public static void load() {
		MinecraftForgeClient.preloadTexture(MTBed.getBlockSheet());
		mtBedRenderID = ModLoader.getUniqueBlockModelID(
				mod_MultiTexturedBeds.instance, true);
		EurysCore.console(MTBed.getModName(), "Registering items...");
		MTBedsCore.addItems();
		EurysCore.console(MTBed.getModName(), "Naming items...");
		MTBedsCore.addNames();
		EurysCore.console(MTBed.getModName(), "Registering recipes...");
		MTBedsCore.addRecipes();
	}

	public static int getDamageValue(IBlockAccess blockAccess, int x, int y,
			int z) {
		TileEntity tileentity = blockAccess.getBlockTileEntity(x, y, z);
		if (tileentity != null && tileentity instanceof TileEntityMTBed) {
			return ((TileEntityMTBed) tileentity).getTextureValue();
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

	public static int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		int index = -1;
		EntityPlayer entityplayer = mc.thePlayer;
		if (entityplayer.onGround) {
			index = getMouseOver();
		}
		if (index == -1 && entityplayer.isAirBorne) {
			index = getMouseOver();
		}
		if (index == -1 && entityplayer.isAirBorne) {
			index = getBelowPlayer(entityplayer);
		}
		if (index == -1 && entityplayer.isAirBorne) {
			index = getAtPlayer(entityplayer);
		}
		if (index == -1) {
			index = 0;
		}
		return index;
	}

	public static boolean isBlockFootOfBed(World world, int x, int y, int z) {
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);
		if (tileentity != null && tileentity instanceof TileEntityMTBed) {
			if (((TileEntityMTBed) tileentity).getBedPiece() == 1)
				return true;
		}
		return false;
	}
}
