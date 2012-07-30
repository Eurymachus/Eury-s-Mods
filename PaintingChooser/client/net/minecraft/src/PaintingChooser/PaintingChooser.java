package net.minecraft.src.PaintingChooser;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
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

	public static Entity getEntityByID(int var0) {
		if (var0 == ModLoader.getMinecraftInstance().thePlayer.entityId) {
			return ModLoader.getMinecraftInstance().thePlayer;
		} else {
			for (int var1 = 0; var1 < ModLoader.getMinecraftInstance().theWorld.loadedEntityList
					.size(); ++var1) {
				Entity var2 = (Entity) ModLoader.getMinecraftInstance().theWorld.loadedEntityList
						.get(var1);

				if (var2 == null) {
					return null;
				}

				if (var2.entityId == var0) {
					return var2;
				}
			}

			return null;
		}
	}
	
	public static void openGui(World world, EntityPlayer entityplayer, EntityPaintings entitypaintings, ArrayList artList) {
		if (!world.isRemote) {
			ModLoader.getMinecraftInstance().displayGuiScreen(new GuiPainting(entitypaintings, artList));
		}
	}

}
