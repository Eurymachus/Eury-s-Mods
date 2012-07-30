package net.minecraft.src.PaintingChooser;

import java.util.ArrayList;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.*;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.PaintingChooser.network.PacketHandles;
import net.minecraft.src.PaintingChooser.network.PacketPaintingGui;

public class PaintingChooser {

	public static String minecraftDir = EurysCore.getMinecraftDir();
	public static ICore PChooser;
	private static boolean initialized = false;

	public static void initialize() {
		if (!initialized) {
			initialized = true;
			PChooser = new ServerCore(new ServerProxy(), new PacketHandles());
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

	public static void openGui(World world, EntityPlayer entityplayer, ArrayList numberOfPaintings, EntityPaintings entityPaintings) {
		if (!world.isRemote) {
			PacketPaintingGui guiPacket = new PacketPaintingGui(entityPaintings);
			PChooser.getProxy().sendPacket(entityplayer, guiPacket.getPacket());
		}
	}

}