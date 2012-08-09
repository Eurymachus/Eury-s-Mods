package net.minecraft.src.Elevators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_EurysElevators;
import net.minecraft.src.Elevators.network.PacketHandles;
import net.minecraft.src.EurysMods.ClientCore;
import net.minecraft.src.EurysMods.ClientProxy;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.EurysMods.network.PacketUpdate;

public class Elevators {
	public static String minecraftDir = Minecraft.getMinecraftDir().toString()
			+ "/";
	private static Minecraft mc = ModLoader.getMinecraftInstance();
	public static ICore Core;
	private static boolean initialized = false;
	public static GuiScreen screen = null;

	public static void initialize() {
		if (initialized)
			return;
		initialized = true;
		Core = new ClientCore(new ClientProxy(), new PacketHandles());
		Core.setModName("Elevators");
		Core.setModChannel("ELEVATOR");
		load();
	}

	public static void load() {
		EurysCore.console(Core.getModName(), "Registering items...");
		ElevatorsCore.addItems();
		// ModLoaderMp.registerNetClientHandlerEntity(EntityElevator.class,
		// elevator_entityID);
		ModLoader.setInGUIHook(mod_EurysElevators.instance, true, true);
		ModLoader.setInGameHook(mod_EurysElevators.instance, true, true);
		EurysCore.console(Core.getModName(), "Naming items...");
		// ElevatorsCore.addNames();
		EurysCore.console(Core.getModName(), "Registering recipes...");
		// ElevatorsCore.addRecipes();
	}

	public static void openGUI(EntityPlayer entityplayer,
			BlockElevator elevator, ChunkPosition chunkpos,
			TileEntityElevator tileentityelevator) {
		String[] floors = tileentityelevator.convertFloorMapToArray();
		boolean[] properties = tileentityelevator
				.convertBooleanPropertiesToArray();
		ElevatorsCore.say("Getting properties: " + properties[0] + ", "
				+ properties[1] + ", " + properties[2]);
		ModLoader.openGUI(entityplayer,
				new GuiElevator(-1, tileentityelevator.numFloors(),
						tileentityelevator.curFloor(), floors, properties,
						chunkpos));
	}

	public static EntityPlayer getPlayer() {
		return mc.thePlayer;
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

	public static void sendPacketToAll(PacketUpdate packet, int x, int y, int z) {
	}
}
