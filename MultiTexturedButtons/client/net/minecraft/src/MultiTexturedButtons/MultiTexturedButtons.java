package net.minecraft.src.MultiTexturedButtons;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.EurysMods.ClientCore;
import net.minecraft.src.EurysMods.ClientProxy;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.MultiTexturedButtons.network.PacketHandles;

public class MultiTexturedButtons {
	public static String minecraftDir = Minecraft.getMinecraftDir().toString();
	private static Minecraft mc = ModLoader.getMinecraftInstance();
	public static ICore Core;

	private static boolean initialized = false;

	public static void initialize() {
		if (initialized)
			return;
		initialized = true;
		Core = new ClientCore(new ClientProxy(), new PacketHandles());
		Core.setModName("MultiTexturedButtons");
		Core.setModChannel("MTB");
		load();
	}

	public static void load() {
		EurysCore.console(Core.getModName(), "Registering items...");
		ModLoader.registerTileEntity(TileEntityMTButton.class, "mtButton");
		MTBCore.addItems();
		EurysCore.console(Core.getModName(), "Naming items...");
		MTBCore.addNames();
		EurysCore.console(Core.getModName(), "Registering recipes...");
		MTBCore.addRecipes();
	}

	public static int getDamageValue(IBlockAccess blockAccess, int x, int y,
			int z) {
		TileEntity tileentity = blockAccess.getBlockTileEntity(x, y, z);
		if (tileentity != null && tileentity instanceof TileEntityMTButton) {
			TileEntityMTButton tileentitymtbutton = (TileEntityMTButton) tileentity;
			return tileentitymtbutton.getTextureValue();
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

	public static int getTextureFromMetaData(int i) {
		int itemDamage = -1;
		switch (i) {
		case 0:
			itemDamage = 22;
			break;
		case 1:
			itemDamage = 23;
			break;
		case 2:
			itemDamage = 24;
			break;
		}
		if (itemDamage == -1) {
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
			switch (texture) {
			case 0:
				itemDamage = 22;
				break;
			case 1:
				itemDamage = 23;
				break;
			case 2:
				itemDamage = 24;
				break;
			}
		}
		if (itemDamage == -1)
			itemDamage = 22;
		return itemDamage;
	}
}
