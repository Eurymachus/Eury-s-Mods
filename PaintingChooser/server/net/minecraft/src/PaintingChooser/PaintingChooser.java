package net.minecraft.src.PaintingChooser;

import java.util.ArrayList;

import net.minecraft.src.Entity;
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

	public static Entity getEntityByID(World world, int entityId) {
		for (int i = 0; i < world.loadedEntityList
				.size(); ++i) {
			Entity entity = (Entity) world.loadedEntityList
					.get(i);

			if (entity == null) {
				return null;
			}

			if (entity.entityId == entityId) {
				return entity;
			}
		}
		return null;
	}

	public static void openGui(World world, EntityPlayer entityplayer, EntityPaintings entitypaintings, ArrayList artList) {
		if (!world.isRemote) {
			PacketPaintingGui guiPacket = new PacketPaintingGui(entitypaintings, artList);
			PChooser.getProxy().sendPacket(entityplayer, guiPacket.getPacket());
		}
	}
}
