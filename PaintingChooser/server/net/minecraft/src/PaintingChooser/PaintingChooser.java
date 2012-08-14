package net.minecraft.src.PaintingChooser;

import java.util.ArrayList;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraft.src.mod_PaintingChooser;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.ServerCore;
import net.minecraft.src.EurysMods.ServerProxy;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.PaintingChooser.network.PacketHandles;
import net.minecraft.src.PaintingChooser.network.PacketPaintingGui;
import net.minecraft.src.PaintingChooser.network.PacketUpdatePainting;

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
		for (int i = 0; i < world.loadedEntityList.size(); ++i) {
			Entity entity = (Entity) world.loadedEntityList.get(i);

			if (entity == null) {
				return null;
			}

			if (entity.entityId == entityId) {
				return entity;
			}
		}
		return null;
	}

	public static void openGui(World world, EntityPlayer entityplayer,
			EntityPainting entitypaintings, ArrayList artList) {
		if (!world.isRemote) {
			PacketPaintingGui guiPacket = new PacketPaintingGui(0,
					entitypaintings, artList);
			PChooser.getProxy().sendPacket(entityplayer, guiPacket.getPacket());
		}
	}

	public static void notifyPlayers(EntityPainting entitypainting) {
		if (entitypainting != null && entitypainting.art != null) {
			PacketUpdatePainting paintingPacket = new PacketUpdatePainting(
					entitypainting, "UPDATEPAINTING");
			paintingPacket.setArtTitle(entitypainting.art.title);
			PaintingChooser.PChooser.getProxy().sendPacketToAll(
					paintingPacket.getPacket(), entitypainting.xPosition,
					entitypainting.yPosition, entitypainting.zPosition, 16,
					mod_PaintingChooser.instance);
		}
	}
}
