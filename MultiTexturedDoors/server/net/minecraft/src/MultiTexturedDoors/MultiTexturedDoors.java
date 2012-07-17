package net.minecraft.src.MultiTexturedDoors;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_MultiTexturedDoors;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.ServerCore;
import net.minecraft.src.EurysMods.ServerProxy;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.MultiTexturedDoors.network.PacketHandles;

public class MultiTexturedDoors {
	public static String minecraftDir = EurysCore.getMinecraftDir();
	public static ICore Core;
	private static boolean initialized = false;
	public static int mtDoorBlockRenderID;

	public static void initialize() {
		if (initialized)
			return;
		initialized = true;
		Core = new ServerCore(new ServerProxy(), new PacketHandles());
		Core.setModName("MultiTexturedDoors");
		Core.setModChannel("MTD");
		load();
	}

	public static void load() {
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

	public static int getDamageValue(IBlockAccess par1iBlockAccess, int x,
			int y, int z) {
		return 0;
	}

	public static EntityPlayer getPlayer() {
		return null;
	}

	public static int getMouseOver() {
		return 0;
	}

	public static int getBelowPlayer(EntityPlayer entityplayer) {
		return 0;
	}

	public static int getAtPlayer(EntityPlayer entityplayer) {
		return 0;
	}

	public static void addMessage(String message) {
		ModLoader.getLogger().warning(message);
	}
}
