package net.minecraft.src.Elevators;

import net.minecraft.src.ChunkPosition;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.Elevators.network.PacketHandles;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.ServerCore;
import net.minecraft.src.EurysMods.ServerProxy;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.forge.DimensionManager;

public class Elevators {
	public static String minecraftDir = "";// EurysCore.getMinecraftDir();
	public static ICore Core;
	private static boolean initialized = false;

	public static void initialize() {
		if (initialized)
			return;
		initialized = true;
		Core = new ServerCore(new ServerProxy(), new PacketHandles());
		Core.setModName("DynamicElevators");
		Core.setModChannel("ELEVATOR");
		load();
	}

	public static void load() {
		EurysCore.console(Core.getModName(), "Registering items...");
		ElevatorsCore.addItems();
		EurysCore.console(Core.getModName(), "Naming items...");
		// ElevatorsCore.addNames();
		EurysCore.console(Core.getModName(), "Registering recipes...");
		// ElevatorsCore.addRecipes();
	}

	public static void openGUI(EntityPlayer entityplayer,
			BlockElevator elevator, ChunkPosition chunkPos,
			TileEntityElevator tileentityelevator) {
		for (int i = 0; i < BlockElevator.conjoinedElevators.size(); ++i) {
			if (ElevatorsCore.elevatorRequests
					.containsValue(BlockElevator.conjoinedElevators.get(i))) {
				ElevatorsCore
						.say("Attempted to open an elevator GUI that was already open. Request ignored.",
								true);
			}
		}

		String[] floorMap = tileentityelevator.convertFloorMapToArray();
		boolean[] properties = tileentityelevator
				.convertBooleanPropertiesToArray();
		ElevatorsCore.requestGUIMapping(chunkPos, entityplayer, floorMap,
				properties);
	}

	public static void sendPacketToAll(PacketUpdate packet, int x, int y, int z) {
		if (packet != null) {
			World[] worlds = DimensionManager.getWorlds();
			for (int i = 0; i < worlds.length; i++) {
				for (int j = 0; j < worlds[i].playerEntities.size(); j++) {
					EntityPlayerMP entityplayermp = (EntityPlayerMP) worlds[i].playerEntities
							.get(j);
					double absX = Math.abs(entityplayermp.posX-x);
					double absY = Math.abs(entityplayermp.posY-y);
					double absZ = Math.abs(entityplayermp.posZ-z);
					if (absX <= 16	&& absY <= 16 && absZ <= 16) {
						entityplayermp.playerNetServerHandler.netManager
								.addToSendQueue(packet.getPacket());
					}
				}
			}
		} else {
			ModLoader.getLogger().warning("Null Packet");
		}
	}
}
