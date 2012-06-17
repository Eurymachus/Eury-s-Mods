package net.minecraft.src.MultiTexturedLevers;

import net.minecraft.src.ModLoader;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.ServerCore;
import net.minecraft.src.EurysMods.ServerProxy;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.MultiTexturedLevers.network.PacketHandles;

public class MultiTexturedLevers {
	public static String minecraftDir = EurysCore.getMinecraftDir();
	public static ICore Core;
	private static boolean initialized = false;

	public static void initialize() {
		if (initialized)
			return;
		initialized = true;
		Core = new ServerCore(new ServerProxy(), new PacketHandles());
		Core.setModName("MultiTexturedLevers");
		Core.setModChannel("MTL");
		load();
	}

	public static void load() {
		EurysCore.console(Core.getModName(), "Registering items...");
		ModLoader.registerTileEntity(TileEntityMTLever.class, "mtLever");
		MTLCore.addItems();
		EurysCore.console(Core.getModName(), "Naming items...");
		MTLCore.addNames();
		EurysCore.console(Core.getModName(), "Registering recipes...");
		MTLCore.addRecipes();
	}
}
