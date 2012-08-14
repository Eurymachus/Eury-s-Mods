package net.minecraft.src.Elevators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import net.minecraft.src.ChunkPosition;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.Elevators.network.PacketHandles;
import net.minecraft.src.Elevators.network.PacketUpdateElevators;
import net.minecraft.src.Elevators.network.PacketUpdateRiders;
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
		Core.setModName("Elevators");
		Core.setModChannel("ELEVATOR");
		load();
	}

	public static void load() {
		EurysCore.console(Core.getModName(), "Registering items...");
		ElevatorsCore.addItems();
		ElevatorsCore.additionalProps();
		EurysCore.console(Core.getModName(), "Naming items...");
		ElevatorsCore.addNames();
		EurysCore.console(Core.getModName(), "Registering recipes...");
		ElevatorsCore.addRecipes();
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

	public static void updateRiderPosition(int[] entityIds, float[] entityYCoords, Entity entityElevator) {
		PacketUpdateRiders ridersPacket = new PacketUpdateRiders(
				"UPDATE_RIDERS", entityIds, entityYCoords);
		ElevatorsCore.sendRiderUpdates(ridersPacket, (int)entityElevator.posX, (int)entityElevator.posY, (int)entityElevator.posZ);
	}

	public static void ejectRiders(EntityElevator entityElevator) {
		if (!entityElevator.mountedEntities.isEmpty()) {
			int i = 0;
			int[] entityIDs = new int[entityElevator.mountedEntities.size()];
			float[] entityYCoords = new float[entityElevator.mountedEntities.size()];

			for (Iterator entities = entityElevator.mountedEntities.iterator(); entities
					.hasNext(); ++i) {
				Entity entity = (Entity) entities.next();
				entity.setPosition(entity.posX, entityElevator.posY
						+ entity.height + entity.getYOffset(),
						entity.posZ);
				entity.ridingEntity = null;
				entityIDs[i] = entity.entityId;
				entityYCoords[i] = (float) entity.posY;
			}

			PacketUpdateRiders ridersPacket = new PacketUpdateRiders(
					"DISMOUNT_RIDERS", entityIDs, entityYCoords);
			ElevatorsCore.sendRiderUpdates(ridersPacket, (int)entityElevator.posX, (int)entityElevator.posY, (int)entityElevator.posZ);
		}
	}

	public static void updateAllConjoined(EntityElevator entityElevator) {
		Iterator elevators = entityElevator.conjoinedelevators.iterator();
		HashSet elevatorHash = new HashSet();
		elevatorHash.addAll(entityElevator.conjoinedelevators);

		int[] entityIDs = new int[elevatorHash.size()];
		float[] entityYCoords = new float[elevatorHash.size()];
		double[] entitysData = new double[elevatorHash.size()];
		int i = 0;

		for (elevators = elevatorHash.iterator(); elevators.hasNext();) {
			EntityElevator elevator = (EntityElevator) elevators.next();
			//if (!elevator.updateSent) {
				entityIDs[i] = elevator.entityId;
				entityYCoords[i] = (float) elevator.posY;
				entitysData[i] = elevator.metadata;
				elevator.updateSent = true;
				//i++;
			//}
			ElevatorsCore.say(elevator.entityId + ": " + elevator.metadata);
		}
/*		int[] entityIDlist = null;
		int[] tempIDList;
		float[] entityYList = null;
		float[] tempYList;
		double[] entityDataList = null;
		double[] tempDataList;
		int z = 0;
		for (int j = 0; j < entityIDs.length; j++) {
			if (entityIDs[j] != 0) {
				tempIDList = entityIDlist;
				tempYList = entityYList;
				tempDataList = entityDataList;
				entityIDlist = new int[z];
				entityYList = new float[z];
				entityDataList = new double[z];
				for (int k = 0; k < entityIDlist.length; k++) {
					if (k == z) {
						entityIDlist[k] = entityIDs[j];
						entityYList[k] = entityYCoords[j];
						entityDataList[k] = entitysData[j];
						z++;
					} else {
						if (tempIDList != null)
							entityIDlist[k] = tempIDList[k];
					}
				}
			}
		}
		entityIDs = entityIDlist;
		entityYCoords = entityYList;
		entityDataList = entitysData;*/

		PacketUpdateElevators elevatorsPacket = new PacketUpdateElevators(
				"UPDATE_ELEVATORS", entityIDs, entityYCoords, entitysData);
		ElevatorsCore.sendElevatorUpdates(elevatorsPacket, (int)entityElevator.posX, (int)entityElevator.posY, (int)entityElevator.posZ);
	}
}
