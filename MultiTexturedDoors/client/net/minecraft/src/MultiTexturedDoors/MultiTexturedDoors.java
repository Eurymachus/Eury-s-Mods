package net.minecraft.src.MultiTexturedDoors;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_MultiTexturedDoors;
import net.minecraft.src.EurysMods.ClientCore;
import net.minecraft.src.EurysMods.ClientProxy;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.MultiTexturedDoors.network.PacketHandles;
import net.minecraft.src.forge.MinecraftForgeClient;

public class MultiTexturedDoors {
	public static String minecraftDir = Minecraft.getMinecraftDir().toString();
	public static int mtDoorBlockRenderID;
	public static ICore Core;
	private static boolean initialized = false;
	private static Minecraft mc = ModLoader.getMinecraftInstance();

	public static void initialize() {
		if (initialized)
			return;
		initialized = true;
		Core = new ClientCore(new ClientProxy(), new PacketHandles());
		Core.setModName("MultiTexturedDoors");
		Core.setModChannel("MTD");
		load();
	}

	public static void load() {
		MinecraftForgeClient.preloadTexture(Core.getBlockSheet());
		mtDoorBlockRenderID = ModLoader.getUniqueBlockModelID(
				mod_MultiTexturedDoors.instance, true);
		EurysCore.console(Core.getModName(), "Registering Items...");
		ModLoader.registerTileEntity(TileEntityMTDoor.class, "mtDoor");
		MTDCore.addItems();
		EurysCore.console(Core.getModName(), "Naming Items...");
		MTDCore.addNames();
		EurysCore.console(Core.getModName(), "Registering Recipes...");
		MTDCore.addRecipes();
	}

	public static int getDamageValue(IBlockAccess world, int x, int y, int z) {
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);
		if (tileentity != null) {
			if (tileentity instanceof TileEntityMTDoor) {
				TileEntityMTDoor tileentitymtdoor = (TileEntityMTDoor) tileentity;
				return tileentitymtdoor.getTextureValue();
			}
		}
		return 1000;
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

	public static EntityPlayer getPlayer() {
		return mc.thePlayer;
	}

	public static void addMessage(String message) {
		mc.thePlayer.addChatMessage(message);
	}
}
