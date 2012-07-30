package net.minecraft.src.PaintingChooser;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.ClientCore;
import net.minecraft.src.EurysMods.ClientProxy;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.PaintingChooser.network.PacketHandles;

public class PaintingChooser {

	public static String minecraftDir = Minecraft.getMinecraftDir().toString();
	public static ICore PChooser;
	private static boolean initialized = false;

	public static void initialize() {
		if (!initialized) {
			initialized = true;
			PChooser = new ClientCore(new ClientProxy(), new PacketHandles());
			PChooser.setModName("PaintingChooser");
			PChooser.setModChannel("PChooser");
			load();
		}
	}

	private static void load() {
		EurysCore.console(PChooser.getModName(), "Registering items...");
		PChooserCore.addItems();
		EurysCore.console(PChooser.getModName(), "Naming items...");
		PChooserCore.addNames();
		EurysCore.console(PChooser.getModName(), "Registering recipes...");
		PChooserCore.addRecipes();
	}

	public static void openGui(World world, ArrayList numberOfPaintings, EntityPaintings entityPaintings) {
		if (!world.isRemote) {
			ModLoader.getMinecraftInstance().displayGuiScreen(new GuiPainting(numberOfPaintings, entityPaintings));
		}
	}

}
