package net.minecraft.src.MultiTexturedPPlates;

import net.minecraft.src.ModLoader;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.ServerCore;
import net.minecraft.src.EurysMods.ServerProxy;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.MultiTexturedPPlates.network.PacketHandles;

public class MultiTexturedPPlates {
	public static String minecraftDir = EurysCore.getMinecraftDir();
	public static ICore Core;
	private static boolean initialized = false;

	public static void initialize() {
		if (initialized)
			return;
		initialized = true;
		Core = new ServerCore(new ServerProxy(), new PacketHandles());
		Core.setModName("MultiTexturedPPlates");
		Core.setModChannel("MTP");
		load();
	}

	public static void load() {
		EurysCore.console(Core.getModName(), "Registering items...");
		ModLoader.registerTileEntity(TileEntityMTPPlate.class, "mtPPlate");
		MTPCore.addItems();
		EurysCore.console(Core.getModName(), "Naming items...");
		MTPCore.addNames();
		EurysCore.console(Core.getModName(), "Registering recipes...");
		MTPCore.addRecipes();
	}
}
