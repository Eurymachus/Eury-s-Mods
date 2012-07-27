package net.minecraft.src.EurysMods;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;
import net.minecraft.src.EurysMods.core.ICore;

public class EurysCore {
	public static String version = "v1.2";
	public static ICore EurysCore;
	private static boolean initialized = false;

	public static String getMinecraftDir() {
		return Minecraft.getMinecraftDir().toString();
	}

	public static void console(String modName, String s, int type) {
		switch (type) {
		case 0:
			ModLoader.getLogger().fine("[" + modName + "] " + s);
			break;
		case 1:
			ModLoader.getLogger().warning("[" + modName + "] " + s);
			break;
		case 2:
			ModLoader.getLogger().severe("[" + modName + "] " + s);
			break;
		default:
			ModLoader.getLogger().fine("[" + modName + "] " + s);
			break;
		}
	}

	public static void console(String modName, String s) {
		console(modName, s, 0);
	}

	public static void load() {
	}

	public static void initialize() {
		if (initialized)
			return;
		initialized = true;
		EurysCore = new ClientCore(new ClientProxy());
		EurysCore.setModName("EurysMods");
	}
}
