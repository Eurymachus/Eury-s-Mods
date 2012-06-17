package net.minecraft.src.MultiTexturedLevers;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_MultiTexturedLevers;
import net.minecraft.src.EurysMods.ClientCore;
import net.minecraft.src.EurysMods.ClientProxy;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.MultiTexturedLevers.network.PacketHandles;
import net.minecraft.src.forge.MinecraftForgeClient;

public class MultiTexturedLevers {
	public static String minecraftDir = Minecraft.getMinecraftDir().toString();
	public static int mtLeverBlockRenderID;
	public static ICore Core;
	private static boolean initialized = false;

	public static void initialize() {
		if (initialized)
			return;
		initialized = true;
		Core = new ClientCore(new ClientProxy(), new PacketHandles());
		Core.setModName("MultiTexturedLevers");
		Core.setModChannel("MTL");
		load();
	}

	public static void load() {
		MinecraftForgeClient.preloadTexture(Core.getItemSheet());
		mtLeverBlockRenderID = ModLoader.getUniqueBlockModelID(
				mod_MultiTexturedLevers.instance, true);
		EurysCore.console(Core.getModName(), "Registering items...");
		ModLoader.registerTileEntity(TileEntityMTLever.class, "mtLever");
		MTLCore.addItems();
		EurysCore.console(Core.getModName(), "Naming items...");
		MTLCore.addNames();
		EurysCore.console(Core.getModName(), "Registering recipes...");
		MTLCore.addRecipes();
	}
}
