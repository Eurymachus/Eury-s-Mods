package net.minecraft.src.MultiTexturedDoors;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;
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
}
