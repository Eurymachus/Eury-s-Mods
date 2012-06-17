package net.minecraft.src.MultiTexturedButtons;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;
import net.minecraft.src.EurysMods.ClientCore;
import net.minecraft.src.EurysMods.ClientProxy;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.MultiTexturedButtons.network.PacketHandles;

public class MultiTexturedButtons {
	public static String minecraftDir = Minecraft.getMinecraftDir().toString();
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
}
